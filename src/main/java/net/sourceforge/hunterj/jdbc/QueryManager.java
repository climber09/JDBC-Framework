package net.sourceforge.hunterj.jdbc;

import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * QueryManager is the central driver for a given set of queries. Each query is encapsulated
 * within a {@link JdbcQueryHandler}. There is a many-to-one relationship between {@link JdbcQueryHandler}
 * and QueryManager. There is a one-to-one relationship between QueryManager and the database
 * connection. QueryManager is responsible for opening and closing the JDBC resources, Connection, 
 * Statement, and ResultSet, after execution of each {@link JdbcQueryHandler#doQueryAndProcessResults()}.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public class QueryManager implements JdbcQueryManager
{
	private final Log logger = LogFactory.getLog(QueryManager.class);
	
	private JdbcResourceMonitor resourceMonitor;
	private ConnectionAdapter connectionAdapter;		

	
	/**
	 * If this constructor is used, then a {@link ConnectionAdapter} must be supplied 
	 * using the setter method.
	 * 
	 * @throws DaoConnectionException 
	 */
	public QueryManager() {
		this(null);
	}
	
	/**
	 * 
	 * @param connectionAdapter
	 */
	public QueryManager(ConnectionAdapter connectionAdapter) {
		this.setConnectionAdapter(connectionAdapter);
		this.setResourceMonitor( new DefaultResourceMonitor() );
	}
	

	public JdbcResourceMonitor getResourceMonitor() {
		return resourceMonitor;
	}

	public void setResourceMonitor(JdbcResourceMonitor resourceMonitor) {
		this.resourceMonitor = resourceMonitor;
	}
	
	/**
	 *  
	 * @see JdbcQueryManager#setConnectionAdapter(com.addr.hunterj.jdbc.ConnectionAdapter)
	 */
	public void setConnectionAdapter(ConnectionAdapter connectionAdapter)  
	{
		this.connectionAdapter = connectionAdapter;
	}

	/**
	 *  
	 * @see JdbcQueryManager#executeAndCloseResources(com.addr.hunterj.jdbc.JdbcQueryHandler)
	 */
	public void executeAndCloseResources(JdbcQueryHandler queryHandler) 
		throws SQLException
	{
		executeAndCloseResources( new JdbcQueryHandler[] { queryHandler } );
	}
	
	/**
	 * 
	 * @see JdbcQueryManager#executeAndCloseResources(com.addr.hunterj.jdbc.JdbcQueryHandler[])
	 */
	public void executeAndCloseResources(JdbcQueryHandler[] queryHandlers) 
		throws SQLException
	{
		Connection targetConnection	= this.connectionAdapter.getTargetConnection();
		logger.trace("Opened Connection: " + targetConnection);

		Connection proxyConnection = this.initConnectionProxy(targetConnection);
		
		try {
			for(int i = 0; i < queryHandlers.length; i++){
				boolean haltCondition = 
					queryHandlers.length == 1 ||
					queryHandlers[i].isHaltOnError();
				
				executeQueryHandler(queryHandlers[i], proxyConnection, haltCondition);	
			}
		} 
		catch (SQLException e) {
			throw e;
		}
		finally{
			JdbcUtil.close(proxyConnection);
			proxyConnection = null;
		}
	}
	
	/**
	 * Encapslates the Connection proxy creation implementation. This method can be 
	 * overriden to provide a different <code>InvocationHandler</code>.
	 * 
	 * @param targetConnection
	 * @return
	 */
	protected Connection initConnectionProxy(Connection targetConnection)
	{
		Connection proxyConnection = (Connection) Proxy.newProxyInstance(
				targetConnection.getClass().getClassLoader(),
				targetConnection.getClass().getInterfaces(),
				new ConnectionInvocationHandler(targetConnection));
		
		return proxyConnection;
	}
	
	/**
	 * Executes the supplied {@link JdbcQueryHandler}, closes the contained <code>ResultSet</code>
	 * and <code>Statement</code> instances, but does not close the <code>Connection</code>.
	 * 
	 * @param queryHandler
	 * @param proxyConnection
	 * @param haltOnError
	 * @throws SQLException
	 */
	protected void executeQueryHandler(JdbcQueryHandler queryHandler,
			Connection proxyConnection, boolean haltOnError)
		throws SQLException
	{
		queryHandler.setConnection(proxyConnection);
		queryHandler.setNestedExecutor(
			new NestedQueryExecutorImpl(proxyConnection, haltOnError));
		
		JdbcQueryContext queryContext = new QueryContext( this.getResourceMonitor() );
		((QueryContextAccessor) Proxy.getInvocationHandler(proxyConnection))
				.setQueryContext(queryContext);
		
		try {
			queryHandler.doQueryAndProcessResults();
		} 
		catch (SQLException sqlEx) {
			if(haltOnError) {
				throw sqlEx;
			}
			else {
				logger.error(
					"Caught SQLException while trying to execute JdbcQueryHandler: " + 
					queryHandler, sqlEx);
			}
		}
		// and since Proxies are being invoked:
		catch(UndeclaredThrowableException ex) { 
			if(haltOnError) {
				throw new QueryException(
					"Caught checked exception while trying to execute JdbcQueryHandler: " + 
					queryHandler, ex.getUndeclaredThrowable());
			}
			else {
				logger.error(
					"Caught checked exception while trying to execute JdbcQueryHandler: " + 
					queryHandler, ex.getUndeclaredThrowable());
			}
		}
		finally {
			JdbcUtil.close( queryContext.getResultSet() );
			JdbcUtil.close( queryContext.getStatement() );
		}
	}
	
	
	private class NestedQueryExecutorImpl implements NestedQueryExecutor
	{
		private Connection connection;
		private boolean haltOnError;
		
		public NestedQueryExecutorImpl(Connection connection, boolean haltOnError)
		{
			this.connection = connection;
			this.haltOnError = haltOnError;
		}

		public void execute(JdbcQueryHandler queryHandler)
				throws SQLException 
		{
			executeQueryHandler(queryHandler, connection, haltOnError);
		}
	}
	
}

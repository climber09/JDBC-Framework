package net.sourceforge.hunterj.jdbc;

import java.sql.SQLException;

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
public interface JdbcQueryManager 
{
	/**
	 * 
	 * @param connectionAdapter
	 * @throws SQLException
	 */
	public void setConnectionAdapter(ConnectionAdapter connectionAdapter);
	
	/**
	 * 
	 * @param resourceMonitor
	 */
	public void setResourceMonitor(JdbcResourceMonitor resourceMonitor);

	/**
	 * 
	 * @param queryHandler
	 * @throws SQLException
	 */
	public void executeAndCloseResources(JdbcQueryHandler queryHandler)
		throws SQLException;

	/**
	 * 
	 * @throws DaoException
	 */
	public void executeAndCloseResources(JdbcQueryHandler[] queryHandlers)
		throws SQLException;

}
package net.sourceforge.hunterj.jdbc;

import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class to provide static methods for closing JDBC resources.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public class JdbcUtil 
{
	private static final Log logger = LogFactory.getLog(JdbcUtil.class);

	
	/**
	 * 
	 * @param connection
	 * @return true if the Connection was closed successfully
	 */
	public static boolean close(java.sql.Connection connection)
	{
		boolean isClosed = false;	
		try {
			if(connection != null && !connection.isClosed()){
				connection.close();
				isClosed = true;
				if(logger.isTraceEnabled()) {
					logger.trace("Closed Connection: "+ connection );
				}
			}
		} 
		catch (SQLException e) {
			logger.debug("Could not close Connection : " + connection, e);
		}
		catch(Throwable t) {
			logger.debug("Unusual error prevented closure of Connection: " + connection, t);
		}
		return isClosed;
	}
	
	
	/**
	 * 
	 * @param statement
	 * @return true if the Statement was closed successfully.
	 */
	public static boolean close(java.sql.Statement statement)
	{
		boolean isClosed = false;	
		try {
			if(statement != null){
				statement.close();
				isClosed = true;
				if(logger.isTraceEnabled()) {
					logger.trace("Closed Statement: "+ statement);
				}
			}
		} 
		catch (SQLException e) {
			logger.debug("Could not close Statement: " + statement, e);
		}
		catch(Throwable t) {
			logger.debug("Unusual error prevented closure of Statement: " + statement, t);
		}
		return isClosed;
	}
	
	
	/**
	 * 
	 * @param resultSet
	 * @return true if the ResultSet was closed successfully.
	 */
	public static boolean close(java.sql.ResultSet resultSet)
	{
		boolean isClosed = false;	
		try {
			if(resultSet != null){
				resultSet.close();
				isClosed = true;
				if(logger.isTraceEnabled()) {
					logger.trace("Closed ResultSet: "+ resultSet);
				}
			}
		} 
		catch (SQLException e) {
			logger.debug("Could not close ResultSet: " + resultSet, e);
		}
		catch(Throwable t) {
			logger.debug("Unusual error prevented closure of ResultSet: " + resultSet, t);
		}
		return isClosed;
	}

}

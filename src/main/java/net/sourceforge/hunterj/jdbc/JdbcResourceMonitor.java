package net.sourceforge.hunterj.jdbc;


/**
 * <code>JdbcResourceMonitor</code> provides a mechanism by which the default behavior,
 * regarding the creation <code>java.sql.Statement</code> and <code>
 * java.sql.ResultSet</code> objects within a {@link JdbcQueryHandler}. The default behavior 
 * is to allow only one <code>Statement</code> and one <code>ResultSet</code> per 
 * {@link JdbcQueryHandler} so that these resources can be closed immediately after execution of
 * {@link JdbcQueryHandler#doQueryAndProcessResults()}. This behavior is enforced with the 
 * {@link DefaultResourceMonitor} implementation. Although it is not recommended, the default 
 * behavior can be overridden with a new <code>JdbcResourceMonitor</code> implementation provided to
 * {@link JdbcQueryManager#setResourceMonitor(JdbcResourceMonitor)}. 
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public interface JdbcResourceMonitor 
{	
	/**
	 * Called within the framework after a <code>java.sql.Statement<code> is created
	 * and before it is assigned to the {@link JdbcQueryContext}.
	 * 
	 * @param oldValue
	 * @param newValue
	 * 
	 * @throws QueryException to prevent an illegal operation, such as the creation of 
	 * multiple <code>Statement</code> instances within a {@link JdbcQueryHandler}.
	 */
	public void checkStatementChange(Object oldValue, Object newValue) 
		throws QueryException;

	/**
	 * Called within the framework after a <code>java.sql.ResultSet<code> is created
	 * and before it is assigned to the {@link JdbcQueryContext}.
	 * 
	 * @param oldValue
	 * @param newValue
	 * 
	 * @throws QueryException to prevent an illegal operation, such as the creation of 
	 * multiple <code>ResultSet</code> instances within a {@link JdbcQueryHandler}.
	 */
	public void checkResultSetChange(Object oldValue, Object newValue)
		throws QueryException;

}

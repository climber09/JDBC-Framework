package net.sourceforge.hunterj.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Except for the {@link ConnectionAdapter}, the <code>JdbcQueryHandler</code>
 * is the only interface that the user need implement to use this framework.
 * Each query is encapsulated within
 * {@link JdbcQueryHandler#doQueryAndProcessResults()} completely. <blockquote>
 * 
 * <pre>
 * Statement stmt = getConnection().createStatement();
 * ResultSet results = stmt.executeQuery(sqlQuery);
 * while(results.next(){
 *         ...
 * }
 * </pre>
 * 
 * </blockquote> No need for additional callbacks. No need to close any of the
 * JDBC resources. All that is taken care of. The recommended approach is to
 * extend {@link AbstractQueryHandler}.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public interface JdbcQueryHandler
//	extends NestedQueryExecutor
{
    /**
     * Implementing classes encapsulate all JDBC code within this one callback.
     * 
     * @throws SQLException caught internally by the framework and re-thrown by
     *                      {@link JdbcQueryManager#executeAndCloseResources(JdbcQueryHandler)}
     *                      or
     *                      {@link JdbcQueryManager#executeAndCloseResources(JdbcQueryHandler[])}.
     */
    public void doQueryAndProcessResults() throws SQLException;

    /**
     * This method is called by the framework internally.
     * 
     * @param connection
     */
    public void setConnection(Connection connection);

    /**
     * Implementing classes use this, and only this, method to retrieve a
     * <code>java.sql.Connection</code>.
     * 
     * @return a <code>java.sql.Connection</code> as a Dynamic Proxy.
     */
    public Connection getConnection();

    /**
     * Called by the framework to determine if execution of given set of
     * <code>JdbcQueryHandlers</code> should be halted in the event of an
     * exception thrown by {@link #doQueryAndProcessResults()}.
     * 
     * @return the value of the haltOnError switch
     */
    public boolean isHaltOnError();

    /**
     * The haltOnError switch is initialized to
     * <code>true</code> by default. It is used to determine 
     * if execution of a given set of <code>JdbcQueryHandler</code> instances will
     * be halted in the event of an exception thrown by
     * {@link #doQueryAndProcessResults()}. Users can set this switch to
     * <code>true</code> to prevent termination of all queries if this query throws
     * an exception.
     * 
     * @param haltOnError
     */
    public void setHaltOnError(boolean haltOnError);

    /**
     * Provides support for nested queries. Some queries require that an additional
     * <code>java.sql.Statement</code> be created and invoked within the scope of the
     * first query, i.e., before the outer <code>Statement</code> and
     * <code>ResultSet</code> have been closed. Resources within inner-most nested
     * <code>JdbcQueryHandler</code> instances will be closed before those within
     * the containing <code>JdbcQueryHandler</code>.
     * 
     * @param queryHandler
     * @throws SQLException
     */
    public void executeNested(JdbcQueryHandler queryHandler) throws SQLException;

    /**
     * This method is called internally by the framework.
     * 
     * @param nestedExecutor
     */
    public void setNestedExecutor(NestedQueryExecutor nestedExecutor);

}

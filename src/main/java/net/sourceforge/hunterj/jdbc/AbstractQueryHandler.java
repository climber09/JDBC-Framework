package net.sourceforge.hunterj.jdbc;

import java.sql.Connection;

/**
 * AbstractQueryHandler is the base {@link JdbcQueryHandler} class to be
 * extended for JDBC query encapsulation. All JDBC code is implemented within
 * {@link JdbcQueryHandler#doQueryAndProcessResults()}.
 * 
 * <blockquote>
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
 * JDBC resources. All that is taken care of.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public abstract class AbstractQueryHandler implements JdbcQueryHandler {
    private NestedQueryExecutor nestedExecutor;
    private Connection connection;
    private boolean haltOnError = true;

    public AbstractQueryHandler() {
    }

    public boolean isHaltOnError() {
        return haltOnError;
    }

    public void setHaltOnError(boolean haltOnError) {
        this.haltOnError = haltOnError;
    }

    /**
     * Not called by the user directly. This method is invoked by
     * {@link QueryManager}.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Called within user-defined {@link JdbcQueryHandler} classes to invoke methods
     * on a <code>java.sql.Connection</code>.
     * 
     * @return a Connection Proxy to the target Connection prescribed by the
     *         user-defined {@link ConnectionAdapter}.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * This method is called by the framework internally.
     * 
     * @param nestedExecutor
     */
    public void setNestedExecutor(NestedQueryExecutor nestedExecutor) {
        this.nestedExecutor = nestedExecutor;
    }

    /**
     * Instructs the framework to execute the supplied {@link JdbcQueryHandler}.
     * 
     * @param queryHandler
     */
    public void executeNested(JdbcQueryHandler queryHandler) throws java.sql.SQLException {
        this.nestedExecutor.execute(queryHandler);
    }

}

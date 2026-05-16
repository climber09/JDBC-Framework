package net.sourceforge.hunterj.jdbc;

/**
 * As the default implementation of {@link JdbcResourceMonitor}, this class
 * provides the instructions necessary to enforce the default behavior regarding
 * <code>java.sql.Statement
 * </code> and <code>java.sql.ResultSet</code> creation within
 * {@link JdbcQueryHandler#doQueryAndProcessResults()}. It is important that
 * only one <code>
 * Statement</code> and one <code>ResultSet</code> instance reside within each
 * {@link JdbcQueryHandler}, so that each can be closed immediately after
 * execution of {@link JdbcQueryHandler#doQueryAndProcessResults()}.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public class DefaultResourceMonitor implements JdbcResourceMonitor {
    public DefaultResourceMonitor() {
    }

    /**
     * This method will throw a {@link QueryException} if the the
     * <code>newValue</code> is not the same instance as the <code>oldValue</code>,
     * as determined by the <code>Object.equals()</code> method.
     * 
     * @param oldValue
     * @param newValue
     * @param message  the message assigned to the {@link QueryException}
     */
    protected void checkChange(Object oldValue, Object newValue, String message) {
        if (oldValue != null && newValue != null && !newValue.equals(oldValue)) {
            throw new QueryException(message);
        }
    }

    /**
     * 
     */
    public void checkResultSetChange(Object oldValue, Object newValue) {
        checkChange(oldValue, newValue, "Multiple ResultSets not allowed.");
    }

    /**
     * 
     * @param oldValue
     * @param newValue
     */
    public void checkStatementChange(Object oldValue, Object newValue) {
        checkChange(oldValue, newValue, "Multiple Statements not allowed.");
    }

}

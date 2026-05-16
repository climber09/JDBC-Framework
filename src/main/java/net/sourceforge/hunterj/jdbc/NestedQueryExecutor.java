package net.sourceforge.hunterj.jdbc;

/**
 * <code>NestedQueryExecutor</code> provides a mechanism whereby
 * {@link JdbcQueryHandler} can be nested. This supports queries that must
 * contain multiple open <code>Statements</code>. This mechanism is necessary
 * since the default behavior allows only one <code>Statement</code> per query.
 * <code>
 * NestedQueryExecutor</code> is implemented within {@link QueryManager} and
 * supplied to each {@link JdbcQueryHandler}. The user does not invoke the
 * implemented object directly, but, instead, uses,
 * {@link JdbcQueryHandler#executeNested(JdbcQueryHandler)} to execute a nested
 * {@link JdbcQueryHandler}.
 * 
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public interface NestedQueryExecutor {
    public void execute(JdbcQueryHandler queryHandler) throws java.sql.SQLException;

}

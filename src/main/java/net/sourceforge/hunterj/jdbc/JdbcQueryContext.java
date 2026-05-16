package net.sourceforge.hunterj.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * <code>JdbcQueryContext</code> is responsible for maintaining a <code>
 * java.sql.Statement</code> and <code>java.sql.ResultSet</code> corresponding
 * to each {@link JdbcQueryHandler}. A new <code>JdbcQueryContext</code> is
 * created for each query.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public interface JdbcQueryContext {

    public abstract ResultSet getResultSet();

    public abstract Statement getStatement();

    public abstract void setResultSet(ResultSet resultSet);

    public abstract void setStatement(Statement statement);

}
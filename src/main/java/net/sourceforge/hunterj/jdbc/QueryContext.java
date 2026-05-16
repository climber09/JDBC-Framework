package net.sourceforge.hunterj.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public class QueryContext implements JdbcQueryContext
{
	private Statement statement;
	private ResultSet resultSet;
	private JdbcResourceMonitor resourceMonitor;
	
	public QueryContext(JdbcResourceMonitor resourceMonitor) 
	{
		this.resourceMonitor = resourceMonitor;
	}

	/**
	 * 
	 */
	public ResultSet getResultSet() {
		return resultSet;
	}

	/**
	 * 
	 */
	public Statement getStatement() {
		return statement;
	}

	/**
	 * Before assigning the <code>ResultSet</code> instance, this method invokes 
	 * {@link JdbcResourceMonitor#checkResultSetChange(Object, Object)}, which can 
	 * throw a {@link QueryException} to veto the change.
	 * 
	 * @param resultSet
	 */
	public void setResultSet(ResultSet resultSet)
	{	
		resourceMonitor.checkResultSetChange(this.resultSet, resultSet);	
		this.resultSet = resultSet;
	}

	/**
	 * Before assigning the <code>Statement</code> instance, this method invokes 
	 * {@link JdbcResourceMonitor#checkStatementChange(Object, Object)}, which can 
	 * throw a {@link QueryException} to veto the change.
	 * 
	 * @param statement
	 */
	public void setStatement(Statement statement) 
	{
		resourceMonitor.checkStatementChange(this.statement, statement);
		this.statement = statement;
	}

}

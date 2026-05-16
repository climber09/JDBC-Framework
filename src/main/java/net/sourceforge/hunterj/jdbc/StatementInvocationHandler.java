package net.sourceforge.hunterj.jdbc;

import java.lang.reflect.Method;
import java.sql.ResultSet;

/**
 * <code>StatementInvocationHandler</code> is provided to <code>java.lang.reflect.Proxy</code> 
 * to create a proxy to a <code>java.sql.Statement</code>. The {@link #invoke(Object, Method, Object[])}
 * method monitors calls to the target <code>Statement</code>. When a <code>java.sql.ResultSet
 * </code> is created, it is stored within the {@link JdbcQueryContext} associated with the 
 * current query.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public class StatementInvocationHandler extends QueryResourceInvocationHandler
{

	public StatementInvocationHandler(Object target) 
	{
		super(target);
	}
	
	public StatementInvocationHandler(Object target, JdbcQueryContext queryContext)
	{
		super(target, queryContext);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable
	{
		Object result = super.invoke(proxy, method, args);
		
		if (result instanceof java.sql.ResultSet) {			
			// Store the ResultSet so it can be closed later.
			this.getQueryContext().setResultSet((ResultSet)result);
		}
		return result;
	}
	
}

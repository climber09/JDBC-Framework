package net.sourceforge.hunterj.jdbc;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;

/**
 * <code>ConnectionInvocationHandler</code> is provided to
 * <code>java.lang.reflect.Proxy</code> to create a proxy to a
 * <code>java.sql.Connection</code>. The
 * {@link #invoke(Object, Method, Object[])} method monitors calls to the target
 * <code>Connection</code>. When a <code>java.sql.Statement</code>
 * is created, it is stored within the {@link JdbcQueryContext}
 * associated with the current query.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public class ConnectionInvocationHandler extends QueryResourceInvocationHandler {
    public ConnectionInvocationHandler(Object targetStatement) {
        super(targetStatement);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = super.invoke(proxy, method, args);

        if (result instanceof java.sql.Statement) {
            Statement proxyStatement = (Statement) Proxy.newProxyInstance(result.getClass().getClassLoader(),
                    result.getClass().getInterfaces(), new StatementInvocationHandler(result, this.getQueryContext()));

            // Store the Proxy Statement so it can be closed later.
            this.getQueryContext().setStatement(proxyStatement);
            return proxyStatement;
        }
        return result;
    }

}

package net.sourceforge.hunterj.jdbc;

import net.sourceforge.hunterj.GenericInvocationHandler;

/**
 * <code>QueryResourceInvocationHandler</code> serves as a base class for
 * <code>InvocationHandler
 * </code> implementations that need to access a {@link JdbcQueryContext}.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public class QueryResourceInvocationHandler extends GenericInvocationHandler implements QueryContextAccessor {
    private JdbcQueryContext queryContext;

    public QueryResourceInvocationHandler(Object target) {
        super(target);
    }

    public QueryResourceInvocationHandler(Object invocationTarget, JdbcQueryContext queryContext) {
        this.queryContext = queryContext;
        this.setInvocationTarget(invocationTarget);
    }

    public JdbcQueryContext getQueryContext() {
        return queryContext;
    }

    public void setQueryContext(JdbcQueryContext queryContext) {
        this.queryContext = queryContext;
    }

}

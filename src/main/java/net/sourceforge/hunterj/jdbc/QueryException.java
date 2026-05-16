package net.sourceforge.hunterj.jdbc;

/**
 * <code>QueryException</code> is the only exception type thrown by the framework.
 * Because <code>java.lang.reflect.Proxy</code> instances are invoked internally by the framework,
 * it is possibly that an <code>UndeclaredThrowableException</code> will be thrown by 
 * {@link JdbcQueryHandler#doQueryAndProcessResults()}. <code>QueryException</code> is used to wrap 
 * the cause Exception within <code>UndeclaredThrowableException</code>, and re-throw it so as to be 
 * accessible to the caller. Because <code>QueryException</code> is a <code>RuntimeException</code>, 
 * developers can choose when to catch it. Most of these exception types will be throw during 
 * development anyway. Also, the methods within {@link JdbcResourceMonitor} will throw exceptions
 * of this type. These exception instances are used to enforce the behavior implemented within the
 * framework's {@link JdbcResourceMonitor} implementation.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public class QueryException extends RuntimeException 
{
	public QueryException() {
	}

	public QueryException(String arg0) {
		super(arg0);
	}

	public QueryException(Throwable arg0) {
		super(arg0);
	}

	public QueryException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}

package net.sourceforge.hunterj;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public class GenericInvocationHandler implements InvocationHandler
{
	private Object invocationTarget;
	private Object invocationResult;
	
	/**
	 * 
	 */
	public GenericInvocationHandler()
	{}

	
	/**
	 * 
	 * @param target
	 */
	public GenericInvocationHandler(Object target) {
		this.invocationTarget = target;
	}
	
	/**
	 * 
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable
	{
		try {
			invocationResult = method.invoke(getInvocationTarget(), args);
		} 
		catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
		return invocationResult;
	}
	
	/**
	 * 
	 * @param result
	 */
	public void setInvocationResult(Object result){ 
		this.invocationResult = result; 
	}
	
	/**
	 * 
	 * @return the result of the method invoked by 
	 * 		{@link #invoke(Object, Method, Object[])}
	 */
	public Object getInvocationResult(){ 
		return invocationResult; 
	}
	
	/**
	 * 
	 * @return the target object invoked by this <code>InvocationHandler</code>
	 */
	public Object getInvocationTarget() {
		return invocationTarget;
	}

	/**
	 * 
	 * @param invocationTarget
	 */
	public void setInvocationTarget(Object invocationTarget) {
		this.invocationTarget = invocationTarget;
	}

}

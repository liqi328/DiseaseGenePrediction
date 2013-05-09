package com.liqi.proxy;

import java.lang.reflect.Method;

import com.liqi.util.ProgramRunTimeCalculator;
import com.liqi.util.WriterUtil;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProgramRuntimeProxy implements MethodInterceptor{
	private ProgramRunTimeCalculator prc;
	
	public ProgramRuntimeProxy(){
		this(null);
	}
	
	public ProgramRuntimeProxy(String taskName){
		this.prc = new ProgramRunTimeCalculator(taskName);
	}
	
	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2,
			MethodProxy arg3) throws Throwable {
		beforeInvoke(arg0.getClass().getSuperclass().getName()+ "." + arg1.getName() + "()");
		Object obj = arg3.invokeSuper(arg0, arg2);
		afterInvoke();
		return obj;
	}
	
	private void beforeInvoke(String taskName){
		prc.setTaskName(taskName);
		prc.start();
	}
	
	private void afterInvoke(){
		prc.stop();
		System.out.println(prc.report());
	}

}

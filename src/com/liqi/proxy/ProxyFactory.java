package com.liqi.proxy;


import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class ProxyFactory {
	
	public static Object getProgramRuntimeProxyInstance(Class<?> superClass, Class[] argsType, Object[] args){
		Enhancer hancer = new Enhancer();
		ProgramRuntimeProxy proxy = new ProgramRuntimeProxy();
		
		hancer.setSuperclass(superClass);
		//hancer.setCallback(proxy);
		hancer.setCallbacks(new Callback[]{ proxy, NoOp.INSTANCE });
		hancer.setCallbackFilter(new ProgramRuntimeProxyFilter());
		
		if(null == argsType || argsType.length == 0){
			return hancer.create();
		}
		
		return hancer.create(argsType, args);
	}

}

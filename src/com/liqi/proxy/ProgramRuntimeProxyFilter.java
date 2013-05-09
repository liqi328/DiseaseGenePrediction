package com.liqi.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

public class ProgramRuntimeProxyFilter implements CallbackFilter{
	public int accept(Method arg0){
		String methodName = arg0.getName();
		
		if(methodName.startsWith("read") || methodName.startsWith("print")
				|| methodName.startsWith("create")){
			return 0;
		}
		return 1;
	}
}

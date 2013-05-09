package com.liqi.util;

import java.util.Map;

public class MapToStringUtil {
	
	public static String mapToString(Map<?,?> map){
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<?, ?> entry : map.entrySet()){
			sb.append(entry.getValue().toString());
		}
		return sb.toString();
	}

}

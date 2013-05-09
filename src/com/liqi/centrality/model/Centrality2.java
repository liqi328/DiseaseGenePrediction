package com.liqi.centrality.model;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Centrality2{
	public static final String[] HEADER = {"HPRD_id", "relatedDiseaseCount", "BC", "CC", "DC", "EC", "IC", "SC", "SoECC", "BN", "MNC", "DMNC", "EPC"}; 
	public static final Map<String, Integer> centralityType = new HashMap<String, Integer>();
	
	static{
		initCentralityType();
	}
	
	private static void initCentralityType(){		
		for(int i=0; i< HEADER.length; ++i){
			centralityType.put(HEADER[i], i);
		}
	}
	
	public Centrality2(){
		centralities = new ArrayList<Object>();
		for(int i=0; i<= HEADER.length; ++i){
			centralities.add(0);
		}
	}
	
	public void setValue(String key, Object value){
		int index = centralityType.get(key);
		centralities.set(index, value);
	}
	
	public Object getValue(String key){
		int index = centralityType.get(key);
		return centralities.get(index);
	}

	private List<Object> centralities;
	
	
	/* 降序-排序 ,以内部类的方式实现Centrality的比较器*/
	public class CentralityComparator implements Comparator<Centrality2>{
		public CentralityComparator(String key) throws NullPointerException{
			/* 此处应该确保 key 合法*/
			this.sortKey = key;
		}

		@Override
		public int compare(Centrality2 cty, Centrality2 anthorCty){
			Object first = cty.getValue(sortKey);
			Object second = anthorCty.getValue(sortKey);
			
			int i = -1;
			if(first instanceof String){
				i = ((String)first).compareTo((String)second);
			}else{
				i = ((Double)first).compareTo((Double)second);
			}
			
			return i;
		}
		
		private String sortKey;
	}
}




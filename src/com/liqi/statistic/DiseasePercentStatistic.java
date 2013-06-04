package com.liqi.statistic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liqi.centrality.model.Centrality2;

/*
 * 统计top n中疾病的数目
 * */
public class DiseasePercentStatistic {
	Map<String, Centrality2> centralityMap = null;
	private Map<String, List<Integer>> resultMap;
	
	public DiseasePercentStatistic(Map<String, Centrality2> centralityMap){
		this.centralityMap = centralityMap;
		resultMap = new HashMap<String, List<Integer>>();
	}
	
	public Map<String, List<Integer>> getResultMap(){
		return this.resultMap;
	}
	
	public void run(){
		/* start statistic */
		String[] header = Centrality2.HEADER;
		for(int i = 2; i < header.length; ++i){
			resultMap.put(header[i], run_0(header[i], header));
		}
	}
	
	private List<Integer> run_0(String key, String[] header){
		List<Integer> aList = new ArrayList<Integer>();
		
		Centrality2[] ctyArray = centralityMap.values().toArray(new Centrality2[0]);
		Arrays.sort(ctyArray, new Centrality2.CentralityComparator(key));
		
		int count = 0;
		for(Centrality2 cty : ctyArray){
			if((Double)cty.getValue(header[1]) > 0){
				++count;
			}
			aList.add(count);
		}
		return aList;
	}
}

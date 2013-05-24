package com.liqi.statistic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.centrality.model.Centrality2;

public class CentralityAverageStatistic implements Statistic {
	private Map<String, Centrality2> centralityMap = null;
	
	private Map<String, Map<String, Double>> avgMap = null;
	
	private static final String[] keys = {
			"DiseaseGene",
			"NormalGene",
			"D/N"};
	
	public CentralityAverageStatistic(Map<String, Centrality2> centralityMap){
		this.centralityMap = centralityMap;  	
		initAvgMap();
	}
	
	private void initAvgMap(){
		avgMap = new HashMap<String, Map<String, Double>>();
		
		for(int i = 0; i < keys.length; ++i){
			avgMap.put(keys[i], new HashMap<String, Double>());
		}
		
		String[] header = Centrality2.HEADER;
		for(Map<String, Double> avg: avgMap.values()){
			for(int i = 2; i < header.length; ++i){
				avg.put(header[i], 0.0);
			}
		}
	}

	@Override
	public void run() {	
		String[] header = Centrality2.HEADER;
		
		String key = keys[0];
		int totalDiseaseGeneNum = 0;
		int totalNormalGeneNum = 0;
		Centrality2 centrality = null;
		Map<String, Double> avg = null;
		Iterator<Centrality2> itr = centralityMap.values().iterator();
		while(itr.hasNext()){
			centrality = itr.next();
			if(Integer.parseInt(centrality.getValue("ISDISEASE").toString()) > 0){
				++totalDiseaseGeneNum;
				key = keys[0];
			}else{
				++totalNormalGeneNum;
				key = keys[1];
			}
			avg = avgMap.get(key);
			for(int i = 2; i < header.length; ++i){
				avg.put(header[i], avg.get(header[i]) + 
						Double.parseDouble(centrality.getValue(header[i]).toString()));
			}
		}
		
		key = keys[0];
		avg = avgMap.get(key);
		for(int i = 2; i < header.length; ++i){
			avg.put(header[i], avg.get(header[i]) / totalDiseaseGeneNum);
		}
		
		key = keys[1];
		avg = avgMap.get(key);
		for(int i = 2; i < header.length; ++i){
			avg.put(header[i], avg.get(header[i]) / totalNormalGeneNum);
		}
		
		key = keys[2];
		avg = avgMap.get(key);
		for(int i = 2; i < header.length; ++i){
			avg.put(header[i], avgMap.get(keys[0]).get(header[i]) / avgMap.get(keys[1]).get(header[i]));
		}
	}

	@Override
	public String getStatisticResult() {
		StringBuffer sb = new StringBuffer();
		
		String[] header = Centrality2.HEADER;
		sb.append("\t");
		for(int i = 2; i < header.length; ++i){
			sb.append("\t").append(header[i]);
		}
		sb.append("\n");
		
		Map<String, Double> avg = null;
		Map.Entry<String, Map<String, Double>> entry = null;
		Iterator<Map.Entry<String, Map<String, Double>>> itr = avgMap.entrySet().iterator();
		while(itr.hasNext()){
			entry = itr.next();
			avg = entry.getValue();
			sb.append(entry.getKey()).append("\t");
			for(int i = 2; i < header.length; ++i){
				sb.append("\t").append(avg.get(header[i]));
			}
			sb.append("\n");
		}
			
		return sb.toString();
	}

}

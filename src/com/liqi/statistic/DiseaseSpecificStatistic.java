package com.liqi.statistic;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liqi.centrality.model.Centrality2;
import com.liqi.geneutil.GeneIdMappingUtil;
import com.liqi.model.Disease;
import com.liqi.model.Gene;
import com.liqi.reader.DiseaseGeneReader;


public class DiseaseSpecificStatistic {
	private Map<String, Centrality2> centralityMap = null;
	Map<String, Disease> diseaseMap = null;
	Map<String, Gene> omimIdIdexedGeneMap = null;
	
	private Map<String, Map<String, Double>> avgMap = null;
	
	
	public DiseaseSpecificStatistic(Map<String, Centrality2> centralityMap){
		this.centralityMap = centralityMap;
	}
	
	private void init(){
		String filename = "E:/2013疾病研究/实验数据/神经退行性疾病/disease_gene.txt";
		DiseaseGeneReader reader = new DiseaseGeneReader(filename);
		reader.read();
		diseaseMap = reader.getDiseaseMap();
		Iterator<Disease> itr = diseaseMap.values().iterator();
		omimIdIdexedGeneMap = new HashMap<String, Gene>();
		while(itr.hasNext()){
			Disease d = itr.next();
			Set<Gene> geneSet = d.getDiseaseGene();
			//System.out.println(d.getName()+ "\t" + geneSet.size());
			Iterator<Gene> geneItr = geneSet.iterator();
			while(geneItr.hasNext()){
				Gene gene = geneItr.next();
				omimIdIdexedGeneMap.put(gene.getOmimId(), gene);
			}
		}
		GeneIdMappingUtil.setOtherGeneId(omimIdIdexedGeneMap);
		
		initAvgMap();
	}
	
	private void initAvgMap(){
		avgMap = new HashMap<String, Map<String, Double>>();
			
		Disease disease = null;
		Iterator<String> itr = diseaseMap.keySet().iterator();
		while(itr.hasNext()){
			avgMap.put(itr.next(), new HashMap<String, Double>());
		}
		
		String[] header = Centrality2.HEADER;
		for(Map<String, Double> avg: avgMap.values()){
			for(int i = 2; i < header.length; ++i){
				avg.put(header[i], 0.0);
			}
		}
	}
	
	public void run(){
		init();
		
		String[] header = Centrality2.HEADER;
		
		int totalNum = 0;
		Centrality2 centrality = null;
		Map<String, Double> avg = null;
		Disease disease = null;
		Iterator<Entry<String, Disease>> itr = diseaseMap.entrySet().iterator();
		while(itr.hasNext()){
			Entry<String, Disease> entry = itr.next();
			disease = entry.getValue();
			Set<Gene> geneSet = disease.getDiseaseGene();
			Iterator<Gene> geneItr = geneSet.iterator();
			totalNum = 0;
			while(geneItr.hasNext()){
				Gene gene = geneItr.next();
				centrality = centralityMap.get(gene.getHprdId());
				if(centrality != null){
					++totalNum;
					avg = avgMap.get(entry.getKey());
					for(int i = 2; i < header.length; ++i){
						avg.put(header[i], avg.get(header[i]) + 
								Double.parseDouble(centrality.getValue(header[i]).toString()));
					}
				}
			}
			totalNum = totalNum == 0? 1: totalNum;
			avg = avgMap.get(entry.getKey());
			for(int i = 2; i < header.length; ++i){
				avg.put(header[i], avg.get(header[i]) / totalNum);
			}
		}
	}
	
	public Map<String, Map<String, Double>> getStatisticResultMap(){
		return avgMap;
	}
	
//	public String[] getKeys(){
//		List<String> list = new ArrayList();
//		Iterator<String> itr = diseaseMap.keySet().iterator();
//		while(itr.hasNext()){
//			avgMap.put(itr.next(), new HashMap<String, Double>());
//		}
//		return list.toArray(new String[0]);
//	}
	
	
	
}

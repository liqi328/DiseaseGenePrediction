package com.liqi.statistic;

import java.util.Iterator;
import java.util.Map;

import com.liqi.centrality.model.Centrality2;
import com.liqi.model.Gene;

@Deprecated
/*
 * 统计每个基因对应的疾病数量
 * 若数量 =0，则表示此基因为非致病基因
 * */
public class GeneDiseaseCountStatistic implements Statistic {
	Map<String, Centrality2> centralityMap = null;
	Map<String, Gene> diseaseGeneMap = null;
	
	public GeneDiseaseCountStatistic(Map<String, Centrality2> centralityMap,
			Map<String, Gene> diseaseGeneMap){
		this.centralityMap = centralityMap;
		this.diseaseGeneMap = diseaseGeneMap;
//		System.out.println(centralityMap.size());
//		System.out.println(diseaseGeneMap.size());
	}
	
	@Override
	public void run() {
		Centrality2 centrality = null;
		Iterator<Centrality2> itr = centralityMap.values().iterator();
		Gene gene = null;
		while(itr.hasNext()){
			centrality = itr.next();
			gene = diseaseGeneMap.get(String.valueOf(Integer.parseInt(centrality.getValue("NAME").toString())));
			if(null == gene){
				continue;
			}
			centrality.setValue("ISDISEASE", gene.getDiseaseSet().size());
			//System.out.println(centrality.getValue("ISDISEASE"));
		}
	}

	@Override
	public String getStatisticResult() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.liqi.statistic;

import java.util.Iterator;
import java.util.Map;

import com.liqi.centrality.model.Centrality2;
import com.liqi.model.Gene;

@Deprecated
/*
 * ͳ��ÿ�������Ӧ�ļ�������
 * ������ =0�����ʾ�˻���Ϊ���²�����
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

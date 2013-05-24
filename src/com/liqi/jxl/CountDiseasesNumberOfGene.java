package com.liqi.jxl;

import java.util.Iterator;
import java.util.Map;

import com.liqi.centrality.model.Centrality2;
import com.liqi.geneutil.GeneIdMappingUtil;
import com.liqi.model.Gene;
import com.liqi.reader.OmimGeneDiseaseAssociationReader;
import com.liqi.reader.ReaderFacade;
import com.liqi.statistic.GeneDiseaseCountStatistic;


/* ����ÿ�������Ӧ�ļ�������
 * ��������= 0�Ļ����ʾ����������;
 * ��������> 0�Ļ����ʾ���²�����
 * 
 * */
public class CountDiseasesNumberOfGene {
	private static Map<String, Gene> hprdIdIndexedDiseaseGeneMap = null;
	
	public static void count(Map<String, Centrality2> centralityMap){
		if(hprdIdIndexedDiseaseGeneMap == null){
			Map<String, Gene> omimIdIndexedDiseaseGeneMap = 
					ReaderFacade.getInstance().getOmimDiseaseGeneMap();
			
			hprdIdIndexedDiseaseGeneMap = GeneIdMappingUtil.transform2HprdIdIndexedGeneMap(omimIdIndexedDiseaseGeneMap);
			//System.out.println("2:" + hprdIdIndexedDiseaseGeneMap.size());
		}
		count_0(centralityMap);
	}
	
	private static void count_0(Map<String, Centrality2> centralityMap) {
		Centrality2 centrality = null;
		Iterator<Centrality2> itr = centralityMap.values().iterator();
		Gene gene = null;
		while(itr.hasNext()){
			centrality = itr.next();
			gene = hprdIdIndexedDiseaseGeneMap.get(String.valueOf(Integer.parseInt(centrality.getValue("NAME").toString())));
			if(null == gene){
				continue;
			}
			centrality.setValue("ISDISEASE", gene.getDiseaseSet().size());
		}
	}
}

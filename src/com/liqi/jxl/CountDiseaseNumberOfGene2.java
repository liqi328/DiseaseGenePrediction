package com.liqi.jxl;

import java.util.Iterator;
import java.util.Map;

import com.liqi.centrality.model.Centrality2;
import com.liqi.geneutil.GeneIdMappingUtil;
import com.liqi.model.Gene;
import com.liqi.reader.ReaderFacade;

public class CountDiseaseNumberOfGene2 {

	private static Map<String, Gene> symbolIndexedDiseaseGeneMap = null;
	
	public static void count(Map<String, Centrality2> centralityMap){
		if(symbolIndexedDiseaseGeneMap == null){
			Map<String, Gene> omimIdIndexedDiseaseGeneMap = 
					ReaderFacade.getInstance().getOmimDiseaseGeneMap();
			
			symbolIndexedDiseaseGeneMap = GeneIdMappingUtil.transform2SymbolIndexedGeneMap(omimIdIndexedDiseaseGeneMap);
		}
		count_0(centralityMap);
	}
	
	private static void count_0(Map<String, Centrality2> centralityMap) {
		Centrality2 centrality = null;
		Iterator<Centrality2> itr = centralityMap.values().iterator();
		Gene gene = null;
		while(itr.hasNext()){
			centrality = itr.next();
			gene = symbolIndexedDiseaseGeneMap.get(centrality.getValue("NAME").toString());
			if(null == gene){
				continue;
			}
			centrality.setValue("ISDISEASE", gene.getDiseaseSet().size());
		}
	}
}

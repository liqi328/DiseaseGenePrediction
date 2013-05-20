package com.liqi.networkcreator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Graph;
import com.liqi.model.HprdIdMapping;
import com.liqi.model.Tissue;
import com.liqi.model.TissueSpecificGeneExpression;

public class TissueSpecificPPINetworkCreator2 extends
		TissueSpecificPPINetworkCreator {

	private Map<String, TissueSpecificGeneExpression> tsGeneExpressionBySymbolMap;
	
	public TissueSpecificPPINetworkCreator2(Graph sourcePPI,
			Map<String, Tissue> tissueMap,
			Map<String, TissueSpecificGeneExpression> tsGeneExpMap,
			Map<String, HprdIdMapping> hprdIdmappingMap) {
		super(sourcePPI, tissueMap, tsGeneExpMap, hprdIdmappingMap);
		
		tsGeneExpressionBySymbolMap = new HashMap<String, TissueSpecificGeneExpression>();
		
		init(tsGeneExpMap);
	}
	
	private void init(Map<String, TissueSpecificGeneExpression> tsGeneExpMap){
		Iterator<TissueSpecificGeneExpression> itr = tsGeneExpMap.values().iterator();
		while(itr.hasNext()){
			TissueSpecificGeneExpression tsge = itr.next();
			tsGeneExpressionBySymbolMap.put(tsge.getIdentifier(), tsge);
		}
	}

	@Override
	protected TissueSpecificGeneExpression getTissueSpecificGeneExpression(String nodeName){
		TissueSpecificGeneExpression tsGeneExp = tsGeneExpressionBySymbolMap.get(nodeName);
		return tsGeneExp;
	}
}

package com.liqi.networkcreator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;
import com.liqi.model.HprdIdMapping;
import com.liqi.model.Tissue;
import com.liqi.model.TissueSpecificGeneExpression;


/* 
 * 组织特异性疾病网络构建器
 * */
public class TissueSpecificPPINetworkCreator implements NetworkCreator {
	private Graph sourcePPI;
	private Map<String, Tissue> tissueMap;
	private Map<String, TissueSpecificGeneExpression> tissueSpecificGeneExpressionMap;
	private Map<String, HprdIdMapping> hprdIdMappingMap;
	
	private Map<String, Graph> resultPPIMap;
	
	private final static int EXPRESSION_THRESHHOLD = 200; //基因表达的阈值
	
	/*
	 * @param sourcePPI 原始的PPI
	 * @param tissueMap 组织信息
	 * @param tissueSpecificGeneExpressionMap 组织特异性基因表达数据
	 * */
	public TissueSpecificPPINetworkCreator(Graph sourcePPI, Map<String, Tissue> tissueMap, 
			Map<String, TissueSpecificGeneExpression> tsGeneExpMap, 
			Map<String, HprdIdMapping> hmMap){
		this.sourcePPI = sourcePPI;
		this.tissueMap = tissueMap;
		this.tissueSpecificGeneExpressionMap = tsGeneExpMap;
		this.hprdIdMappingMap = hmMap;
		
		this.resultPPIMap = new HashMap<String, Graph>();
	}
	
	public Map<String, Graph> getResultPPIMap() {
		return this.resultPPIMap;
	}

	@Override
	public void create() {
		Iterator<Tissue> itr = tissueMap.values().iterator();
		Tissue t = null;
		Graph tsPPI = null;
		while(itr.hasNext()){
			t = itr.next();
			tsPPI = createOneTissueSpecificNetwork(t);
			resultPPIMap.put(t.getTissueName(), tsPPI);
		}
	}
	
	private Graph createOneTissueSpecificNetwork(Tissue t){
		Graph tsPPI = new Graph();
		
		addNodesToTissueSpecificPPI(tsPPI, t);
		addEdgesToTissueSpecificPPI(tsPPI);
		
		return tsPPI;
	}

	private void addNodesToTissueSpecificPPI(final Graph tsPPI, final Tissue t){
		Iterator<Node> nodeItr = sourcePPI.createNodesIterator();
		Node node = null;
		
		while(nodeItr.hasNext()){
			node = nodeItr.next();
			if(isNodeCanBeAdded(node, t)){
				tsPPI.addNode((Node) node.clone());
			}
		}		
	}
	
	private void addEdgesToTissueSpecificPPI(final Graph tsPPI){
		Iterator<Node> nodeItr = tsPPI.createNodesIterator();
		while(nodeItr.hasNext()){
			addNodeNeightbors(tsPPI, nodeItr.next());
		}
	}
	
	private void addNodeNeightbors(final Graph tsPPI, final Node node){
		Iterator<Edge> edgeItr = sourcePPI.createNeighborsIterator(node);
		Edge e = null;
		while(edgeItr.hasNext()){
			e = edgeItr.next();
			if(tsPPI.containsNode(e.getToNode())){
				tsPPI.addEdge(node, e.getToNode());
			}
		}
	}
	
	private boolean isNodeCanBeAdded(final Node node, final Tissue t){
		HprdIdMapping hm = hprdIdMappingMap.get(node.getName());
		if(hm == null){
			return false;
		}
		TissueSpecificGeneExpression tsGeneExp = tissueSpecificGeneExpressionMap.get(hm.getEntrezGeneId());
		if(tsGeneExp == null){
			return false;
		}
		double avg = tsGeneExp.getExpressionValue(t.getFirstSampleId());
		avg += tsGeneExp.getExpressionValue(t.getSecondSampleId());
		avg /= 2;
		if(avg >= EXPRESSION_THRESHHOLD){
			return true;
		}
		return false;
	}
	
}

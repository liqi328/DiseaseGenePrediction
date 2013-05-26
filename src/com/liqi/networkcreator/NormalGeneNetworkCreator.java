package com.liqi.networkcreator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;

/*
 * 构建正常基因子网，从原始的PPI网络中挖出正常基因子网。
 * 
 * 此类采用的算法是：正常基因子网 = 原始PPI - 疾病子网
 * */
public class NormalGeneNetworkCreator implements NetworkCreator {
	private Graph sourcePPI = null;
	private Graph diseaseSubPPI = null;
	
	private Graph resultPPI = null;
	Map<String, Node> nodesMap = null;
	
	public NormalGeneNetworkCreator(Graph sourcePPI, Graph diseaseSubPPI){
		this.sourcePPI = sourcePPI;
		this.diseaseSubPPI = diseaseSubPPI;
		resultPPI = new Graph();
		nodesMap = new HashMap<String, Node>();
	}
	
	public Graph getResultPPI(){
		return resultPPI;
	}
	
	@Override
	public void create() {
		createByNode();
		//createByEdge();
	}
	
//	private void createByEdge(){
//		Set<Edge> sourcePPIEdgeSet = sourcePPI.createEdgesSet();
//		Set<Edge> diseasePPIEdgeSet = diseaseSubPPI.createEdgesSet();
//		Collection<Edge> edges = CollectionUtils.subtract(sourcePPIEdgeSet, 
//				diseasePPIEdgeSet);
//		System.out.println("(A)原始PPI网络边数目：" + sourcePPIEdgeSet.size());
//		System.out.println("(B)疾病子网PPI网络边数目：" + diseasePPIEdgeSet.size());
//		System.out.println("A - B 的边数目：" + edges.size());
//		
//		for(Iterator<Edge> itr = edges.iterator(); itr.hasNext();){
//		}
//	}
	
	private void createByNode(){
		Set<Node> sourcePPINodeSet = sourcePPI.createNodesSet();
		Set<Node> diseasePPINodeSet = diseaseSubPPI.createNodesSet();
		Collection<Node> nodes = CollectionUtils.subtract(sourcePPINodeSet, diseasePPINodeSet);
		
		System.out.println("(A)原始PPI网络节点数目：" + sourcePPINodeSet.size());
		System.out.println("(B)疾病子网PPI网络节点数目：" + diseasePPINodeSet.size());
		System.out.println("A - B 的节点数目：" + nodes.size());
		
		Node node = null;
		for(Iterator<Node> itr = nodes.iterator(); itr.hasNext();){
			node = itr.next();
			nodesMap.put(node.getName(), node);
		}
		
		createSubPPI();
	}
	
	private void createSubPPI(){			
		Iterator<Node> itr = nodesMap.values().iterator();
		Node node = null;
		int count = 0;
		while(itr.hasNext()){
			node = itr.next();
			count += addNeighborEdge(node);
		}
		System.out.println("未加入子网的节点数目：" + count);
	}
	
	/* 若节点加入到子网中，返回0.
	 * 未加入子网中，返回1 */
	private int addNeighborEdge(Node fromNode){		
		Iterator<Edge> edgeItr = sourcePPI.createNeighborsIterator(fromNode);
		if(edgeItr == null){
			return 1;
		}
		
		Edge e = null;
		Node toNode = null;
		resultPPI.addNode(fromNode);
		
		while(edgeItr.hasNext()){
			e = edgeItr.next();
			if(e.isSelfLoop()){
				continue;
			}
			toNode = nodesMap.get(e.getToNode().getName());
			if(toNode == null)continue;
			resultPPI.addEdge(fromNode, toNode);
		}
		return 0;
	}

}

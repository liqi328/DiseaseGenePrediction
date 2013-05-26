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
 * ��������������������ԭʼ��PPI�������ڳ���������������
 * 
 * ������õ��㷨�ǣ������������� = ԭʼPPI - ��������
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
//		System.out.println("(A)ԭʼPPI�������Ŀ��" + sourcePPIEdgeSet.size());
//		System.out.println("(B)��������PPI�������Ŀ��" + diseasePPIEdgeSet.size());
//		System.out.println("A - B �ı���Ŀ��" + edges.size());
//		
//		for(Iterator<Edge> itr = edges.iterator(); itr.hasNext();){
//		}
//	}
	
	private void createByNode(){
		Set<Node> sourcePPINodeSet = sourcePPI.createNodesSet();
		Set<Node> diseasePPINodeSet = diseaseSubPPI.createNodesSet();
		Collection<Node> nodes = CollectionUtils.subtract(sourcePPINodeSet, diseasePPINodeSet);
		
		System.out.println("(A)ԭʼPPI����ڵ���Ŀ��" + sourcePPINodeSet.size());
		System.out.println("(B)��������PPI����ڵ���Ŀ��" + diseasePPINodeSet.size());
		System.out.println("A - B �Ľڵ���Ŀ��" + nodes.size());
		
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
		System.out.println("δ���������Ľڵ���Ŀ��" + count);
	}
	
	/* ���ڵ���뵽�����У�����0.
	 * δ���������У�����1 */
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

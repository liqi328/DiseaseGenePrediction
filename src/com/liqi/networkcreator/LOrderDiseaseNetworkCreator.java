package com.liqi.networkcreator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;
import com.liqi.model.Gene;

/*
 * ������֪���²����򣬴�PPI����ȡL-���²���������(LΪ�ھӵĲ���)
 * 
 * L-�׼������磬������
 * */
public class LOrderDiseaseNetworkCreator implements NetworkCreator {
	private Graph sourcePPI;
	private Map<String, Gene> diseaseGeneMap;
	
	private Graph resultSubPpi;
	
	private Map<String, Node> nodes;
	
	/* level ������, ����*/
	private int level = 0;
	
	public LOrderDiseaseNetworkCreator(Graph ppi, Map<String, Gene> diseaseGeneMap,
			Integer level){
		this.sourcePPI = ppi;
		this.diseaseGeneMap = diseaseGeneMap;
		this.level = level;
		
		resultSubPpi = new Graph();
		nodes = new HashMap<String, Node>();	
	}
	
	public Graph getResultPPI(){
		return this.resultSubPpi;
	}
	
	@Override
	public void create(){
		initNodes();
		
		for(int i= 1; i <= level; ++i){
			addNeighborsNode();
		}
		
		createSubPPI();		
	}

	
	/* ��ʼ�������²�������Ϊ�����еĽڵ�*/
	private void initNodes(){
		Iterator<Gene> itr = diseaseGeneMap.values().iterator();
		Node node = null;
		while(itr.hasNext()){
			node = new Node("" + Integer.parseInt(itr.next().getHprdId()));
			nodes.put(node.getName(), node);
		}
		//System.out.println("Init nodes size = " + nodes.size());
	}
	
	private void addNeighborsNode(){
		Iterator<Node> itr = nodes.values().iterator();
		Node node = null;
		Map<String, Node> neighborNode = new HashMap<String, Node>();
		while(itr.hasNext()){
			node = itr.next();
			Iterator<Edge> edgeItr = sourcePPI.createNeighborsIterator(node);
			if(edgeItr == null){
				/* delete this node*/
				continue;
			}
			Edge e = null;
			Node tmpNode = null;
			while(edgeItr.hasNext()){
				e = edgeItr.next();
				if(e.isSelfLoop()){
					continue;
				}
				if(nodes.containsKey((e.getToNode().getName()))){
					continue;
				}
				tmpNode = (Node) e.getToNode().clone();
				neighborNode.put(tmpNode.getName(), tmpNode);
			}
		}
		
		nodes.putAll(neighborNode);
	}
	
	private void createSubPPI(){			
		//System.out.println("After add neighbors, nodes size = " + nodes.size());
		
		Iterator<Node> itr = nodes.values().iterator();
		Node node = null;
		while(itr.hasNext()){
			node = itr.next();
			addNeighborEdge(node);
		}
	}
	
	private void addNeighborEdge(Node fromNode){		
		Iterator<Edge> edgeItr = sourcePPI.createNeighborsIterator(fromNode);
		if(edgeItr == null){
			return;
		}
		
		Edge e = null;
		Node toNode = null;
		resultSubPpi.addNode(fromNode);
		
		while(edgeItr.hasNext()){
			e = edgeItr.next();
			if(e.isSelfLoop()){
				continue;
			}
			toNode = nodes.get(e.getToNode().getName());
			if(toNode == null)continue;
			resultSubPpi.addEdge(fromNode, toNode);
		}
		
	}

}

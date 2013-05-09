package com.liqi.experiment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;
import com.liqi.model.Gene;
import com.liqi.util.WriterUtil;

/*
 * ������֪���²����򣬴�PPI����ȡL-���²���������(LΪ�ھӵĲ���)
 * 
 * L-�׼������磬������
 * */
public class LOrderDiseaseNetworkCreator implements NetworkCreator {
	private Buffer buffer;
	
	private Graph subPpi;
	
	private Map<String, Node> nodes;
	
	/* level ������*/
	int level = 0;
	
	public LOrderDiseaseNetworkCreator(Buffer buffer, Integer level){
		this.buffer = buffer;
		this.level = level;
		subPpi = new Graph();
		nodes = new HashMap<String, Node>();		
	}
	
	public Graph getResultPPI(){
		return this.subPpi;
	}
	
	@Override
	public void create(){
		initNodes();
		
		for(int i= 1; i <= level; ++i){
			addNeighborsNode();
		}
		
		createSubPPI();		
	}
	
	public void printSubNetwork(){
		String outFilename = buffer.getOutputDirectory() + "sub_disease_ppi_"+ level + ".txt";
		WriterUtil.write(outFilename, subPpi.toString());
	}
	
	/* ��ʼ�������²�������Ϊ�����еĽڵ�*/
	private void initNodes(){
		Map<String, Gene> hprdGene = buffer.getHprdGene();
		
		Iterator<Gene> itr = hprdGene.values().iterator();
		Node node = null;
		while(itr.hasNext()){
			node = new Node(itr.next().getHprdId());
			nodes.put(node.getName(), node);
		}
		//System.out.println("Init nodes size = " + nodes.size());
	}
	
	private void addNeighborsNode(){
		Graph ppi = buffer.getPpi();
		
		Iterator<Node> itr = nodes.values().iterator();
		Node node = null;
		Map<String, Node> neighborNode = new HashMap<String, Node>();
		while(itr.hasNext()){
			node = itr.next();
			Iterator<Edge> edgeItr = ppi.createNeighborsIterator(node);
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
		Graph ppi = buffer.getPpi();
		
		Iterator<Edge> edgeItr = ppi.createNeighborsIterator(fromNode);
		if(edgeItr == null){
			return;
		}
		
		Edge e = null;
		Node toNode = null;
		subPpi.addNode(fromNode);
		
		while(edgeItr.hasNext()){
			e = edgeItr.next();
			if(e.isSelfLoop()){
				continue;
			}
			toNode = nodes.get(e.getToNode().getName());
			if(toNode == null)continue;
			subPpi.addEdge(fromNode, toNode);
		}
		
	}

}

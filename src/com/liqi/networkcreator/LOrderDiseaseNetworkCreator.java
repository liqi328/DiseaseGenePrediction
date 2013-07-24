package com.liqi.networkcreator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.experiment.ExperimentDataBuffer;
import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;
import com.liqi.model.Gene;
import com.liqi.proxy.ProxyFactory;
import com.liqi.reader.GeneReader;
import com.liqi.reader.ReaderFacade;
import com.liqi.util.WriterUtil;

/**
 * ������֪���²����򣬴�PPI����ȡL-���²���������(LΪ�ھӵĲ���)
 * 
 * L-�׼������磬������
 * 
 * 
 * */
public class LOrderDiseaseNetworkCreator implements NetworkCreator {
	//ͼ�еĽڵ�����ΪHprdId
	protected Graph sourcePPI;
	
	/*String:
	 * Gene:����������HprdId */
	protected Map<String, Gene> diseaseGeneMap;
	
	private Graph resultSubPpi;
	
	protected Map<String, Node> nodes;
	
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
	
	protected Node createNode(Gene gene){
		Node node = new Node("" + Integer.parseInt(gene.getHprdId()));
		return node;
	}

	
	/* ��ʼ�������²�������Ϊ�����еĽڵ�*/
	protected void initNodes(){
		Iterator<Gene> itr = diseaseGeneMap.values().iterator();
		Node node = null;
		while(itr.hasNext()){
			//node = new Node("" + Integer.parseInt(itr.next().getHprdId()));
			node = createNode(itr.next());
			nodes.put(node.getName(), node);
		}
		System.out.println("Init nodes size = " + nodes.size());
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
		return 0;
	}
	
	public static void main(String[] args){
		String ppiFilename = "E:/2013�����о�/ʵ������/�������Լ���/HPRD_ppi.txt";
		String diseaseGeneFilename = "E:/MyCode/research/PrioritizingDiseaseCandidateGene/input/pd_input/pd_hprdid.txt";
		
		Graph ppi = ReaderFacade.getInstance().getPPI(ppiFilename);
		GeneReader reader = new GeneReader(diseaseGeneFilename);
		reader.read();
		Map<String, Gene> geneMap = reader.getGeneMap();
		
		for(int i=0; i<=1;++i){
			LOrderDiseaseNetworkCreator ldnCreator = (LOrderDiseaseNetworkCreator) ProxyFactory.getProgramRuntimeProxyInstance(LOrderDiseaseNetworkCreator.class,
					new Class[]{Graph.class, Map.class, Integer.class}, new Object[]{ppi, geneMap, i});
			ldnCreator.create();
			Graph subPPI = ldnCreator.getResultPPI();
			String outFilename = "E:/MyCode/research/PrioritizingDiseaseCandidateGene/input/pd_input/pd_"+ i + ".txt";
			WriterUtil.write(outFilename, subPPI.edgesToString());
		}	
	}

}

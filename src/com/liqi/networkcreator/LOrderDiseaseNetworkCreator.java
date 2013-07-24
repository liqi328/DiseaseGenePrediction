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
 * 根据已知的致病基因，从PPI中提取L-阶致病基因子网(L为邻居的层数)
 * 
 * L-阶疾病网络，构建器
 * 
 * 
 * */
public class LOrderDiseaseNetworkCreator implements NetworkCreator {
	//图中的节点名称为HprdId
	protected Graph sourcePPI;
	
	/*String:
	 * Gene:必须设置了HprdId */
	protected Map<String, Gene> diseaseGeneMap;
	
	private Graph resultSubPpi;
	
	protected Map<String, Node> nodes;
	
	/* level 阶子网, 阶数*/
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

	
	/* 初始化：将致病基因作为网络中的节点*/
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
		String ppiFilename = "E:/2013疾病研究/实验数据/神经退行性疾病/HPRD_ppi.txt";
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

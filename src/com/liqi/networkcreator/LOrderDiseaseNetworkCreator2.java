package com.liqi.networkcreator;

import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Graph;
import com.liqi.graph.Node;
import com.liqi.model.Gene;

public class LOrderDiseaseNetworkCreator2 extends LOrderDiseaseNetworkCreator {

	public LOrderDiseaseNetworkCreator2(Graph ppi,
			Map<String, Gene> diseaseGeneMap, Integer level) {
		super(ppi, diseaseGeneMap, level);
		// TODO Auto-generated constructor stub
	}
	
	protected Node createNode(Gene gene){
		String[] symbols = gene.getSymbols().split(",");
		Node node = null;
		int i = 0;
		Node tmp = null;
		for(String sym: symbols){
			node = new Node(sym.trim());
			if(sourcePPI.containsNode(node)){
				++i;
				tmp = node;
				//return node;
			}
		}
		if(i>1){
			System.out.println("" + i + "\t" + gene.getSymbols());
		}
		
		return tmp;
	}
	
	private void addNode(Gene gene){
		String[] symbols = gene.getSymbols().split(",");
		Node node = null;
		int i = 0;
		for(String sym: symbols){
			node = new Node(sym.trim());
			if(sourcePPI.containsNode(node)){
				++i;
				nodes.put(node.getName(), node);
			}
		}
		if(i>1){
			System.out.println("" + i + "\t" + gene.getSymbols());
		}
		
	}
	
	protected void initNodes(){
		Iterator<Gene> itr = diseaseGeneMap.values().iterator();
		Node node = null;
		Gene gene = null;
		while(itr.hasNext()){
			gene = itr.next();
//			node = createNode(gene);
//			if(node == null){
//				System.out.println("Not in the ppi: " + gene.getSymbols());
//				continue;
//			}
//			nodes.put(node.getName(), node);
			addNode(gene);
		}
		System.out.println("Init nodes size = " + nodes.size());
	}

}

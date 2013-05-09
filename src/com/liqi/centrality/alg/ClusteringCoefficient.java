package com.liqi.centrality.alg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;

public class ClusteringCoefficient {
	private Map<String, Double> resultMap;
	private Graph graph;
	
	public ClusteringCoefficient(){
		resultMap = new HashMap<String, Double>();
	}
	
	public void run(Graph graph){
		if(null == graph)return;
		
		this.graph = graph;
		Iterator<Node> nodeItr = graph.createNodesIterator();
		while(nodeItr.hasNext()){
			calculateOneNode(nodeItr.next());
		}
	}
	
	private void calculateOneNode(Node node){
		Iterator<Edge> itr = this.graph.createNeighborsIterator(node);
		List<Node> neightbors = new ArrayList<Node>();
		Edge edge = null;
		while(itr.hasNext()){
			edge = itr.next();
			if(edge.isSelfLoop())continue;
			neightbors.add(edge.getToNode());
		}
		int neighborNum = neightbors.size();
		
		int count = 0;
		for(int i = 0; i< neighborNum - 1; ++i){
			for(int j=i+1; j< neighborNum; ++j){
				if(graph.containsEdge(neightbors.get(i), neightbors.get(j))){
					++count;
				}
			}
		}
		double result = 0D;
		if(neighborNum > 1){
			result = (2.0D * count) / (neighborNum * (neighborNum - 1)) ;
		}
		resultMap.put(node.getName(), result);
	}
	
	public String getReuslt(){
		StringBuffer sb = new StringBuffer();
		Map.Entry<String, Double> entry = null;
		Iterator<Map.Entry<String, Double>> itr = resultMap.entrySet().iterator();
		while(itr.hasNext()){
			entry = itr.next();
			sb.append(entry.getKey()).append("\t");
			sb.append(entry.getValue()).append("\n");
		}
		
		return sb.toString();
	}

}

package com.liqi.centrality.alg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;

public class DC {
	private Graph graph;
	private Map<String, Double> resultMap;
	
	public DC(){
		
		resultMap = new HashMap<String, Double>();
	}
	
	public void run(Graph graph){
		if(graph == null)return;
		this.graph = graph;
		
		Iterator<Node> nodeItr = graph.createNodesIterator();
		while(nodeItr.hasNext()){
			calculateOneNode(nodeItr.next());
		}
	}

	private void calculateOneNode(Node node) {
		Iterator<Edge> itr = this.graph.createNeighborsIterator(node);
		int count = 0;
		Edge edge = null;
		while(itr.hasNext()){
			edge = itr.next();
			if(edge.isSelfLoop())continue;
			++count;
		}
		
		resultMap.put(node.getName(), new Double(count));
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

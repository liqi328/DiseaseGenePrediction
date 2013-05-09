package com.liqi.graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liqi.exception.NodeAlreadyExistException;
import com.liqi.exception.NodeNonExistException;


public class Graph{
	private Map<Node, List<Edge>> vertices;
	private int nodeNumber = 0;
	private int edgeNumber = -1;
	
	public Graph(){
		vertices = new HashMap<Node, List<Edge>>();
	}
	
	private void incrementNodeNumber(){
		++nodeNumber;
	}
		
	private void incrementEdgeNumber(){
		++edgeNumber;
	}
	
	public boolean addNode(Node newNode){
		if(this.containsNode(newNode)){
			//System.out.println("add duplicate node.");
			//throw new NodeAlreadyExistException("node already exist.");
			return false;
		}
		vertices.put(newNode, new ArrayList<Edge>());
		return true;
	}
	
	public boolean removeNode(Node rNode) throws NodeNonExistException{
		if(!this.containsNode(rNode)){
			//System.out.println("Remove node.");
			throw new NodeNonExistException("Remove: node not exist.");
		}
		
		List<Edge> neighbors = vertices.get(rNode);
		for(Edge e : neighbors){
			this.removeEdge(e);
		}
		
		vertices.remove(rNode);
		
		return true;
	}
	
	public boolean addEdge(Node fromNode, Node toNode){
		return addEdge(new Edge(fromNode, toNode));
	}
	
	public boolean addEdge(Edge newEdge){
		if(null == newEdge){
			return false;
		}
		
		Node fromNode = newEdge.getFromNode();
		Node toNode =   newEdge.getToNode();
		
		this.addNode(fromNode);
		this.addNode(toNode);
		
		if(vertices.get(fromNode).contains(newEdge) || vertices.get(toNode).contains(newEdge)){
			//System.out.println("Duplicate Edge.");
			//System.out.println(newEdge.toString());
			return false;
		}
		
		vertices.get(fromNode).add(newEdge);
		Edge edge2 = new Edge(toNode, fromNode);
		vertices.get(toNode).add(edge2);
		
		return true;
	}
	
	public boolean removeEdge(Edge rEdge){
		if(null == rEdge){
			return false;
		}
		
		Node fromNode = rEdge.getFromNode();
		Node toNode   = rEdge.getToNode();
		
		if(!this.containsNode(fromNode)
				|| !this.containsNode(toNode)){
			return false;
		}
		
		if(vertices.get(fromNode).contains(rEdge) && vertices.get(toNode).contains(rEdge)){
			vertices.get(fromNode).remove(rEdge);
			vertices.get(toNode).remove(rEdge);
			return true;
		}
		return false;
	}
	
	public Iterator<Edge> createNeighborsIterator(Node node){
		if(!this.containsNode(node)){
			return null;
		}
		return vertices.get(node).iterator();
	}
	
	public Iterator<Node> createNodesIterator(){
		return vertices.keySet().iterator();
	}
	
	public int getNodesNumber(){
		return vertices.keySet().size();
	}
	
	public int getNeighborsNumber(Node node){
		if(!this.containsNode(node)){
			return 0;
		}
		return vertices.get(node).size();
	}
	
	public int getEdgesNumber(){
//		Map.Entry<Node, List<Edge>> entry = null;
//		Iterator<Map.Entry<Node, List<Edge>>> itr = vertices.entrySet().iterator();
//		
//		int count = 0;
//		while(itr.hasNext()){
//			entry = itr.next();
//			count += entry.getValue().size();
//		}
//		return count/2;
		if(this.edgeNumber != -1){
			return this.edgeNumber;
		}
		Set<Edge> edgeSet = new HashSet<Edge>(this.getNodesNumber() * 2);
		Iterator<Map.Entry<Node, List<Edge>>> itr = vertices.entrySet().iterator();
		Map.Entry<Node, List<Edge>> entry = null;
		while(itr.hasNext()){
			entry = itr.next();
			edgeSet.addAll(entry.getValue());		
		}
		this.edgeNumber = edgeSet.size();
		return this.edgeNumber;
	}
	
	public boolean containsNode(Node node){
		return vertices.keySet().contains(node);
	}
	
	public boolean containsEdge(Node from, Node to){
		if(!this.containsNode(from) || !this.containsNode(to))return false;
		Edge e = new Edge(from, to);
		if(vertices.get(from).contains(e) || vertices.get(to).contains(e)){
			return true;
		}
		return false;
	}
	
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append("Graph: nodes[").append(this.getNodesNumber()).append("], edges[");
		int edgeNum = this.getEdgesNumber();
		//int edgeNum = this.getNodesNumber() * 2;
		buf.append(edgeNum).append("]\n");
		
		buf.append("-----------------Node List------------------>>>\n");		
		Set<Edge> edgeSet = new HashSet<Edge>(edgeNum);
		Iterator<Map.Entry<Node, List<Edge>>> itr = vertices.entrySet().iterator();
		Map.Entry<Node, List<Edge>> entry = null;
		while(itr.hasNext()){
			entry = itr.next();
			buf.append(entry.getKey().toString()).append("\n");
			edgeSet.addAll(entry.getValue());		
		}
		buf.append("<<<-----------------Node List------------------\n");
		
		buf.append("-----------------Edge List------------------>>>\n");
		Iterator<Edge> edgeItr = edgeSet.iterator();
		int count = 0;
		while(edgeItr.hasNext()){
			buf.append(edgeItr.next().toString()).append("\n");
			++count;
		}
		buf.append("edge num = ").append(count).append("\n");
		buf.append("<<<-----------------Edge List------------------\n");
		
		return buf.toString();
	}
}


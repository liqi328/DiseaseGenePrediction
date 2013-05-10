package com.liqi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;

public class PPIReader extends AbstractModelReader {
	private Graph ppi;
	public PPIReader(String filename) {
		super(filename);
		ppi = new Graph();
	}
	
	public Graph getPPI(){
		return this.ppi;
	}
	

	@Override
	public void read() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
			
			String line = null;
			while((line = in.readLine()) != null){
				addOneEdge(line);
			}			
			in.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addOneEdge(String line){
		String[] cols = line.split("\t");
		Node fromNode = new Node(cols[0]);
		Node toNode   = new Node(cols[1]);
		Edge edge = new Edge(fromNode, toNode);
//		if(edge.isSelfLoop()){
//			return;
//		}
		
		ppi.addEdge(edge);		
	} 

}

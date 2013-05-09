package com.liqi.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;

public class PPIView {
	
	public static Graph readPPI(String filename){
		File file = new File(filename);
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			
			Graph graph = new Graph();
			String line = null;
			while((line = in.readLine()) != null){
				processOneLine(line, graph);
			}
			
			in.close();
			
			return graph;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static void processOneLine(String line, Graph graph){
		String[] cols = line.split("\t");
		Node fromNode = new Node(cols[0]);
		Node toNode   = new Node(cols[1]);
		Edge edge = new Edge(fromNode, toNode);
//		if(edge.isSelfLoop()){
//			return;
//		}
		
//		graph.addNode(fromNode);
//		graph.addNode(toNode);
		graph.addEdge(edge);		
	} 
	
	public static void writePPI(String filename, Graph ppi){
		File file = new File(filename);
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(ppi.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

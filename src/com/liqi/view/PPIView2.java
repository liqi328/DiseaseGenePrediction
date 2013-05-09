package com.liqi.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import com.liqi.graph.Edge;
import com.liqi.graph.Node;

public class PPIView2 {
	
	public static Graph<Node, DefaultEdge> readPPI(String filename){
		File file = new File(filename);
		Graph<Node, DefaultEdge> ppi = null;
		try{
			BufferedReader in = new BufferedReader(new FileReader(file));
			ppi = new SimpleGraph<Node, DefaultEdge>(DefaultEdge.class);
			String line = null;
			while((line = in.readLine()) != null){
				processOneLine(line, ppi);
			}
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ppi;
	}
	
	public static void writePPI(String filename, Graph<Node, DefaultEdge> ppi){
		File file = new File(filename);
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(ppi.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void processOneLine(String line, Graph<Node, DefaultEdge> ppi){
		String[] cols = line.split("\t");
		if(cols[0].equals(cols[1]))return;
		Node from = new Node(cols[0]);
		Node to = new Node(cols[1]);
		ppi.addVertex(from);
		ppi.addVertex(to);
		ppi.addEdge(from, to);
	}
}

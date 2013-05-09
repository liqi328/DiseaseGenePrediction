package com.liqi.centrality.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.liqi.centrality.model.Centrality;

public class CentralityView {
	public CentralityView(){
		centralityList = new ArrayList<Centrality>();
	}
	
	public void read(String filename) throws IOException{
		File inFile = new File(filename); 
		BufferedReader in = new BufferedReader(new FileReader(inFile));
		String line = null;
		line = in.readLine();
		processFirstLine(line);
		
		while((line = in.readLine()) != null){
			processOneLine(line);
		}
		in.close();
	}
	
	private void processFirstLine(String line){
		String[] tmpHeader = line.split("\t");
		this.header = new String[tmpHeader.length];
		int i = 0;
		for(String col: tmpHeader){
			this.header[i++] = new String(col); 
			System.out.print(col + "\t");
		}
		System.out.println();
		
	}
	
	private void processOneLine(String line){
		String[] cols = line.split("\t");
		Centrality cty = new Centrality();
		cty.setHprdId(cols[0]);
		cty.setNumberOfDisease(Integer.parseInt(cols[1]));
		
		for(int i=2; i < cols.length; ++i){
		}
		
	}

	private List<Centrality> centralityList;
	private String[] header;
}

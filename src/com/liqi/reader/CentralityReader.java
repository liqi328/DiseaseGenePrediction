package com.liqi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.liqi.centrality.model.Centrality2;

public class CentralityReader extends AbstractModelReader{

	private Map<String, Centrality2> centralityMap;
	
	public CentralityReader(String filename){
		super(filename);
		centralityMap = new HashMap<String, Centrality2>();
	}
	
	public Map<String, Centrality2> getCentralityMap(){
		return centralityMap;
	}
	
	public void read(){
		File inFile = new File(filename); 
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(inFile));
			String line = null;
			line = in.readLine();
			String[] header = line.split("\t");;
			
			while((line = in.readLine()) != null){
				processOneLine(line, header);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void processOneLine(String line, String[] header){
		String[] cols = line.split("\t");
		Centrality2 cty = new Centrality2();
		for(int i = 0; i < header.length; ++i){
			cty.setValue(header[i], cols[i]);
		}
		centralityMap.put((String)cty.getValue("NAME"), cty);
	}
}

package com.liqi.jxl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jxl.JXLException;

import com.liqi.centrality.model.Centrality2;
import com.liqi.util.FileUtil;

public class DiseasePercent2Excel {
	public void read(String dirName) throws IOException, JXLException{
		File excelFile = new File(dirName+".xls");
			
		Centrality2Excel excelWriter = new Centrality2Excel(excelFile);

		String sheetName = null;
		Map<String, Centrality2> centralityMap = null;
			
		File [] files = FileUtil.getFileList(dirName);
		for(File file: files){
			sheetName = file.getName();
			centralityMap = readOneCentralityMap(file);
		}
		
	}
	
	private Map<String, Centrality2> readOneCentralityMap(File file){
		Map<String, Centrality2> centralityMap = new HashMap<String, Centrality2>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			Centrality2 centrality = null;
			String line = in.readLine();
			String[] header = line.split("\t");
			System.out.println(header.length);
			String[] values = null;
			while((line = in.readLine()) != null){
				values = line.split("\t");
				centrality = new Centrality2();
				centralityMap.put(values[0], centrality);
				for(int i = 0; i < header.length; ++i){
					centrality.setValue(header[i], values[i]);
				}
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return centralityMap;
	}
}

package com.liqi.jxl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.liqi.centrality.model.Centrality2;
import com.liqi.util.WriterUtil;

public class Centrality2Txt {
	public static void write(String outTxtname, 
			Map<String, Centrality2> centralityMap) 
					throws IOException{
		
		File outTxtFile = new File(outTxtname);
		
		write(outTxtFile, centralityMap);
	}
	
	public static void write(File outTxtFile,
			Map<String, Centrality2> centralityMap) 
					throws IOException{
		System.out.println("Write to txt: " + outTxtFile.getAbsolutePath());
		
		StringBuffer sb = new StringBuffer();
		String[] header = Centrality2.HEADER;
		
		for(int i=0; i < header.length; ++i){
			sb.append(header[i]).append("\t");
        }
		sb.append("\n");
		for(Centrality2 centrality: centralityMap.values()){
        	for(int i=0; i < header.length; ++i){
        		sb.append(centrality.getValue(header[i])).append("\t");
            }
        	sb.append("\n");
        }
		
		WriterUtil.write(outTxtFile.getAbsolutePath(), sb.toString());
	}
}

package com.liqi.jxl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jxl.JXLException;

import com.liqi.centrality.model.Centrality2;
import com.liqi.model.Gene;
import com.liqi.model.HprdIdMapping;
import com.liqi.reader.OmimGeneDiseaseAssociationReader;
import com.liqi.reader.ReaderFacade;
import com.liqi.statistic.GeneDiseaseCountStatistic;
import com.liqi.util.FileUtil;

public class CentralityResult2Excel {
	
	public void read(String dirName) throws IOException, JXLException{
		File excelFile = new File(dirName+".xls");
			
		File txtOutDir = new File(dirName+"_txt");
		FileUtil.makeDir(txtOutDir);
		File[] fileDirs = FileUtil.getDirectoryList(dirName);
		
		Centrality2Excel excelWriter = new Centrality2Excel(excelFile);
		
		String sheetName = null;
		for(File dir: fileDirs){
			sheetName = dir.getName();
			File [] files = FileUtil.getFileList(dir.getAbsolutePath());
			
			if(files.length < 10){
				System.out.println("Directory: " + dir);
				System.out.println("Skip this directory, Result is not completed, " +
						 "it must 10 files in this directory, but there are " +
						files.length + " files in it, please checked it. ");
				
				continue;
			}
			
			Map<String, Centrality2> centralityMap = readOneCentralityMap(files);
			CountDiseasesNumberOfGene.count(centralityMap);
			
			Centrality2Txt.write(txtOutDir.getAbsolutePath()+"/"+dir.getName(), centralityMap);
			excelWriter.write(sheetName, centralityMap);
			
			centralityMap = null;
		}
		
		excelWriter.close();
	}
	
	private Map<String, Centrality2> readOneCentralityMap(File[] files) throws IOException{
		Map<String, Centrality2> centralityMap = new HashMap<String, Centrality2>();
		
		for(File file: files){
			BufferedReader in = new BufferedReader(new FileReader(file));
			String field = file.getName().substring((file.getName().lastIndexOf('.')) + 1);
			if(field.equalsIgnoreCase("bn") || field.equalsIgnoreCase("dmnc")){
				in.readLine();
			}
			Centrality2 centrality = null;
			String line = null;
			String[] values = null;
			int i = 0;
			while((line = in.readLine()) != null){
				values = line.split("\t");
				i = values.length - 2;
				centrality = centralityMap.get(values[i]);
				if(centrality == null){
					centrality = new Centrality2();
					centrality.setValue("NAME", values[i]);
					centralityMap.put(values[i], centrality);
				}
				++i;
				centrality.setValue(field, values[i]);
			}
			in.close();
		}
		return centralityMap;
	}
	
	
	public static void main(String[] args){
		String[] dirs = {
				//"E:/2013�����о�/ʵ������/�������Լ���/result/DTSPPIN_1_result",
				"E:/2013�����о�/ʵ������/�������Լ���/result/DTSPPIN_2_result",
				};
		try{
			for(String dir : dirs){
				CentralityResult2Excel reader = new CentralityResult2Excel();
				reader.read(dir);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}

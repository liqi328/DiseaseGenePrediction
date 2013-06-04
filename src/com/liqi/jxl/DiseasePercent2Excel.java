package com.liqi.jxl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.JXLException;

import com.liqi.centrality.model.Centrality2;
import com.liqi.statistic.DiseasePercentStatistic;
import com.liqi.util.FileUtil;
import com.liqi.util.WriterUtil;

public class DiseasePercent2Excel {
	public void read(String dirName) throws IOException, JXLException{
		File excelFile = new File(dirName+".xls");
			
		Percent2Excel excelWriter = new Percent2Excel(excelFile);

		String sheetName = null;
		Map<String, Centrality2> centralityMap = null;
		
		Map<String, List<Integer>> rocMap = null;
			
		File [] files = FileUtil.getFileList(dirName);
		for(File file: files){
			sheetName = file.getName();
			centralityMap = readOneCentralityMap(file);
			rocMap = getRocMap(centralityMap);
			excelWriter.write(sheetName, rocMap);
		}
		excelWriter.close();
	}
	
	private Map<String, List<Integer>> getRocMap(Map<String, Centrality2> centralityMap){
		DiseasePercentStatistic sta = new DiseasePercentStatistic(centralityMap);
		sta.run();
		return sta.getResultMap();
	}
	
	private Map<String, Centrality2> readOneCentralityMap(File file){
		Map<String, Centrality2> centralityMap = new HashMap<String, Centrality2>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			Centrality2 centrality = null;
			String line = in.readLine();
			String[] header = line.split("\t");
			String[] values = null;
			while((line = in.readLine()) != null){
				values = line.split("\t");
				centrality = new Centrality2();
				centralityMap.put(values[0], centrality);
				centrality.setValue(header[0], values[0]);
				for(int i = 1; i < header.length; ++i){
					centrality.setValue(header[i], Double.valueOf(values[i]));
				}
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return centralityMap;
	}
	
	public void test_write() throws IOException{
		String filename = "E:/2013疾病研究/实验数据/神经退行性疾病/result2/TSPPIN_result_txt/721-B-lymphoblasts.txt";
		Map<String, Centrality2> centralityMap = readOneCentralityMap(new File(filename));
		String[] header = Centrality2.HEADER;
		Centrality2[] ctyArray = centralityMap.values().toArray(new Centrality2[0]);
		Arrays.sort(ctyArray, new Centrality2.CentralityComparator(header[2]));
		write(ctyArray);
	}
	
	public static void write(Centrality2[] ctyArray) 
					throws IOException{
		File outFile = new File("E:/2013疾病研究/实验数据/神经退行性疾病/result2/1.txt");
		System.out.println("Write to txt: " + outFile.getAbsolutePath());
		
		StringBuffer sb = new StringBuffer();
		String[] header = Centrality2.HEADER;
		
		for(int i=0; i < header.length; ++i){
			sb.append(header[i]).append("\t");
        }
		sb.append("\n");
		for(Centrality2 centrality: ctyArray){
        	for(int i=0; i < header.length; ++i){
        		sb.append(centrality.getValue(header[i])).append("\t");
            }
        	sb.append("\n");
        }
		
		WriterUtil.write(outFile.getAbsolutePath(), sb.toString());
	}
}

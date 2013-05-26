package com.liqi.jxl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import jxl.JXLException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.liqi.centrality.model.Centrality2;
import com.liqi.statistic.CentralityAverageStatistic;

public class Centrality2Excel {
	private WritableWorkbook wwb;
	private File excelFile;
	
	private boolean opened = false;
	
	public Centrality2Excel(String inputExcelFilename) throws IOException{
		this(new File(inputExcelFilename));
	}
	
	public Centrality2Excel(File inputExcelFile) throws IOException{
		excelFile = inputExcelFile;
	}
	
	public void open() throws IOException{
		if(!excelFile.exists()){
			System.out.println("create new excel file.");
			excelFile.createNewFile();
		}
		wwb = Workbook.createWorkbook(excelFile);
		
		opened = true;
	}
	
	public void close() throws IOException, JXLException{
		opened = false;
		wwb.write();
		wwb.close();
	};
		
	public void write(String newSheetname,
			Map<String, Centrality2> centralityMap) 
					throws IOException, JXLException{
		if(!opened){
			open();
		}
		
		System.out.println("Create sheet: " + newSheetname);
        
        //创建Excel工作表 指定名称和位置
        int num = wwb.getNumberOfSheets();
        System.out.println("number of sheet: " + num);
        WritableSheet ws = wwb.createSheet(newSheetname, num);

        //**************往工作表中添加数据*****************

        Label label = null;
        String[] header = Centrality2.HEADER;
        int row = 0;
        for(int i=0; i < header.length; ++i){
        	label = new Label(i,row,header[i]);
        	ws.addCell(label);
        }
        
        for(Centrality2 centrality: centralityMap.values()){
        	++row;
        	for(int i=0; i < header.length; ++i){
            	label = new Label(i,row,centrality.getValue(header[i]).toString());
            	ws.addCell(label);
            }
        }
	}
	
	public void writeCentralityAverage(
			Map<String, Map<String, Map<String, Double>>> allAvgMap) throws IOException, JXLException, WriteException{
		if(!opened){
			open();
		}
		String newSheetname = "average";
		System.out.println("Create sheet: " + newSheetname);
		
		int num = wwb.getNumberOfSheets();
        System.out.println("number of sheet: " + num);
        WritableSheet ws = wwb.createSheet(newSheetname, num);
		
		
		String[] header = Centrality2.HEADER;
		Label label = null;
		
		String[] keys = CentralityAverageStatistic.keys;
		Iterator<Map.Entry<String, Map<String, Map<String, Double>>>> itr = null;
		Map.Entry<String, Map<String, Map<String, Double>>> entry = null;
		Map<String, Map<String, Double>> avgMap = null;
		int col = 0;
		int row = 0;
		for(int i=2; i < header.length; ++i){
			col = 0;
			label = new Label(col, row, header[i]);
			ws.addCell(label);
			
			for(int r = 0; r < keys.length; ++r){
				label = new Label(col, row + r + 1, keys[r]);
				ws.addCell(label);
			}
			col = 1;
			
			itr = allAvgMap.entrySet().iterator();
			while(itr.hasNext()){
				entry = itr.next();
				label = new Label(col, row, entry.getKey());
				ws.addCell(label);
				
				avgMap = entry.getValue();
				for(int r = 0; r < keys.length; ++r){
					label = new Label(col, row + r + 1, String.valueOf(avgMap.get(keys[r]).get(header[i])));
					ws.addCell(label);
				}
				++col;
			}
			row += 6;
		}

	}
}

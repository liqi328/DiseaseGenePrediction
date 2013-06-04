package com.liqi.jxl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.JXLException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Percent2Excel {
	private WritableWorkbook wwb;
	private File excelFile;
	
	private boolean opened = false;
	
	public Percent2Excel(String inputExcelFilename) throws IOException{
		this(new File(inputExcelFilename));
	}
	
	public Percent2Excel(File inputExcelFile) throws IOException{
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
			Map<String, List<Integer>> rocMap) 
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
        String[] header = rocMap.keySet().toArray(new String[0]);
        int row = 0;
        label = new Label(0, row, "X");
        ws.addCell(label);
        for(int i = 0; i < header.length; ++i){
        	label = new Label(i + 1,row,header[i]);
        	ws.addCell(label);
        }
        
    	for(int r = 0; r < rocMap.get(header[0]).size(); ++r){
    		++row;
    		label = new Label(0, row, "" + (r + 1));
            ws.addCell(label);
	    	for(int i=0; i < header.length; ++i){
	        	label = new Label(i + 1, row, rocMap.get(header[i]).get(r).toString());
	        	ws.addCell(label);
	        }
    	}

	}
}

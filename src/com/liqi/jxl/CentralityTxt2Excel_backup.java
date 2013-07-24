package com.liqi.jxl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.liqi.centrality.model.Centrality2;
import com.liqi.model.Gene;
import com.liqi.model.HprdIdMapping;
import com.liqi.reader.OmimGeneDiseaseAssociationReader;
import com.liqi.reader.ReaderFacade;
import com.liqi.statistic.CentralityAverageStatistic;
import com.liqi.statistic.GeneDiseaseCountStatistic;
import com.liqi.util.FileUtil;
import com.liqi.util.WriterUtil;


/*
 * 已废弃，被CentralityResult2Excel.java代替
 * */
@Deprecated
public class CentralityTxt2Excel_backup {
	private Map<String, Gene> hprdDiseaseGeneMap = null;
	
	public void read(String dirName) throws IOException, RowsExceededException, WriteException{
		File excelFile = new File(dirName+".xls");
		//if(!excelFile.exists()){
			excelFile.createNewFile();
		//}
		
		OutputStream os = new FileOutputStream(excelFile);
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		
		File txtOutDir = new File(dirName+"_txt");
		FileUtil.makeDir(txtOutDir);
		File[] fileDirs = FileUtil.getDirectoryList(dirName);
		
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
//			for(File file: files){
//				//System.out.println("------" + file.getName());
//			}
			Map<String, Centrality2> centralityMap = readOneCentralityMap(files);
			setGeneDiseaseCount(centralityMap);
			
			write2txt(txtOutDir.getAbsolutePath()+"/"+dir.getName(), centralityMap);
			
			write2excel(wwb, sheetName, centralityMap);
			
			centralityMap = null;
		}
		
		wwb.write();
		wwb.close();
		os.close();
	}
	
	
	private void setGeneDiseaseCount(Map<String, Centrality2> centralityMap){
		
		if(hprdDiseaseGeneMap == null){
			OmimGeneDiseaseAssociationReader reader = new OmimGeneDiseaseAssociationReader(
					"E:/2013疾病研究/疾病数据/OMIM/morbidmap");
			reader.read();
			Map<String, Gene> diseaseGeneMap = reader.getOmimDiseaseGeneMap();
			System.out.println("1:" + diseaseGeneMap.size());
			hprdDiseaseGeneMap = getHprdDiseaseGeneMap(diseaseGeneMap);
			System.out.println("2:" + hprdDiseaseGeneMap.size());
		}
		
		GeneDiseaseCountStatistic sta = new GeneDiseaseCountStatistic(centralityMap, hprdDiseaseGeneMap);
		sta.run();
	}
	
	
	private Map<String, Gene> getHprdDiseaseGeneMap(Map<String, Gene> diseaseGeneMap){
		Map<String, HprdIdMapping> hprdIdMappingMap = ReaderFacade.getInstance().getHprdIdIndexedIdMappingMap(
				"E:/2013疾病研究/疾病数据/HumanPPI/HPRD_Release9_062910/FLAT_FILES_072010/HPRD_ID_MAPPINGS.txt");
		
		Map<String, HprdIdMapping> omimIdMappingMap = getOmimIdMappingMap(hprdIdMappingMap);
		
		Map<String, Gene> diseaseGeneMapTmp = new HashMap<String, Gene>();
		
		Iterator<Gene> itr = diseaseGeneMap.values().iterator();
		
		Gene gene = null;
		HprdIdMapping idMapping = null;
		while(itr.hasNext()){
			gene = itr.next();
			idMapping = omimIdMappingMap.get(gene.getOmimId());
			
			if(idMapping == null){
				//System.out.println(gene.getOmimId());
				continue;
			}
			gene.setHprdId(String.valueOf(Integer.parseInt(idMapping.getHrpdId())));
			gene.setEntrezId(idMapping.getEntrezGeneId());
			diseaseGeneMapTmp.put(gene.getHprdId(), gene);
		}
		return diseaseGeneMapTmp;
	}
	
	private Map<String, HprdIdMapping> getOmimIdMappingMap(Map<String, HprdIdMapping> hprdIdMappingMap){
		Map<String, HprdIdMapping> omimIdMapppingMap = new HashMap<String, HprdIdMapping>();
		
		Iterator<HprdIdMapping> itr = hprdIdMappingMap.values().iterator();
		HprdIdMapping idMapping = null;
		String omimId = null;
		while(itr.hasNext()){
			idMapping = itr.next();
			omimId = idMapping.getOmimId();
			if(null == omimId || omimId.equals("")|| omimId.equals("-")){
				continue;
			}
			omimIdMapppingMap.put(omimId, idMapping);
		}
		return omimIdMapppingMap;
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
	
	
	private void write2excel(WritableWorkbook wwb, String newSheetName,
			Map<String, Centrality2> centralityMap) throws RowsExceededException, WriteException{
		System.out.println("Create sheet: " + newSheetName);
        
        //创建Excel工作表 指定名称和位置
        int num = wwb.getNumberOfSheets();
        WritableSheet ws = wwb.createSheet(newSheetName, num);

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
        
        /* 增加统计信息*/       
    	CentralityAverageStatistic casta = new CentralityAverageStatistic(centralityMap);
		casta.run();
		String result = casta.getStatisticResult();
		String[] values = result.split("\n");
		
//		row += 2;
//		String[] cols = null;
//		for(String value: values){
//			cols = value.split("\t");
//			++row;
//			for(int i = 0; i < cols.length; ++i){
//				label = new Label(i, row, cols[i]);
//				ws.addCell(label);
//			}
//		}
		
		row = 3;
		int startCol = header.length + 2;
		String[] cols = null;
		for(String value: values){
			cols = value.split("\t");
			row += 2;
			for(int i = 0; i < cols.length; ++i){
				label = new Label(i + startCol, row, cols[i]);
				ws.addCell(label);
			}
		}
		
		/* ----- */
	}
	
	private void write2txt(String filename, Map<String, Centrality2> centralityMap){
		System.out.println("Create txt: " + filename);
		
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
		
		/* 增加统计信息*/       
//		CentralityAverageStatistic casta = new CentralityAverageStatistic(centralityMap);
//		casta.run();
//		String result = casta.getStatisticResult();
//		
//		sb.append(result).append("\n");
//		
		WriterUtil.write(filename, sb.toString());
	}
	
	public static void main(String[] args){
		String[] dirs = {
				//"E:/2013疾病研究/实验数据/神经退行性疾病/result/DTSPPIN_1_result",
				"E:/2013疾病研究/实验数据/神经退行性疾病/result/DTSPPIN_2_result",};
		try{
			for(String dir : dirs){
				CentralityTxt2Excel_backup reader = new CentralityTxt2Excel_backup();
				reader.read(dir);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}

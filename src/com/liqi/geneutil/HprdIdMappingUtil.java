package com.liqi.geneutil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.model.HprdIdMapping;
import com.liqi.reader.ReaderFacade;

/*
 * HPRD ���ݿ��е�HPRD_ID_MAPPINGS.txt
 * */
public class HprdIdMappingUtil {
	private static Map<String, HprdIdMapping> hprdIdIndexedIdMappingMap = null;
	private static Map<String, HprdIdMapping> omimIdIndexedIdMappingMap = null;
	private static Map<String, HprdIdMapping> entrezIdIndexIdMappingMap = null;
	private static Map<String, HprdIdMapping> symbolIndexIdMappingMap = null;
	
	
	public static Map<String, HprdIdMapping> getHprdIdIndexIdMapping(){
		if(hprdIdIndexedIdMappingMap == null){
			hprdIdIndexedIdMappingMap = ReaderFacade.getInstance().getHprdIdIndexedIdMappingMap(
				"E:/2013�����о�/��������/HumanPPI/HPRD_Release9_062910/FLAT_FILES_072010/HPRD_ID_MAPPINGS.txt");
			initOtherIdMappingMap();
			//System.out.println("Init HprdIdIndexedIdMapping Map.");
		}
		return hprdIdIndexedIdMappingMap;
	}
	
	public static Map<String, HprdIdMapping> getOmimIdIndexedIdMapping(){
		if(omimIdIndexedIdMappingMap == null){
			getHprdIdIndexIdMapping();
		}
		
		return omimIdIndexedIdMappingMap;
	}
	
	public static Map<String, HprdIdMapping> getEntrezIdIndexedIdMapping(){
		if(entrezIdIndexIdMappingMap == null){
			getHprdIdIndexIdMapping();
		}
		
		return entrezIdIndexIdMappingMap;
	}
	
	public static Map<String, HprdIdMapping> getSymbolIdIndexedIdMapping(){
		if(symbolIndexIdMappingMap == null){
			getHprdIdIndexIdMapping();
		}
		
		return symbolIndexIdMappingMap;
	}
	
	private static  void initOtherIdMappingMap(){
		omimIdIndexedIdMappingMap = new HashMap<String, HprdIdMapping>();
		entrezIdIndexIdMappingMap = new HashMap<String, HprdIdMapping>();
		symbolIndexIdMappingMap = new HashMap<String, HprdIdMapping>();
		
		Iterator<HprdIdMapping> itr = hprdIdIndexedIdMappingMap.values().iterator();
		HprdIdMapping idMapping = null;
		while(itr.hasNext()){
			idMapping = itr.next();
			addToOmimIndexedIdMappingMap(idMapping);
			addToSymbolIndexedIdMappingMap(idMapping);
			
			entrezIdIndexIdMappingMap.put(idMapping.getEntrezGeneId(), idMapping);
		}
	}
	
	private static void addToOmimIndexedIdMappingMap(HprdIdMapping idMapping){
		String omimId = idMapping.getOmimId();
		if(null == omimId || omimId.equals("")|| omimId.equals("-")){
			return;
		}
		omimIdIndexedIdMappingMap.put(omimId, idMapping);
	}
	
	private static void addToSymbolIndexedIdMappingMap(HprdIdMapping idMapping){
		String symbol = idMapping.getGeneSymbol();
		if(null == symbol || symbol.equals("")|| symbol.equals("-")){
			return;
		}
		symbolIndexIdMappingMap.put(symbol, idMapping);
	}

}

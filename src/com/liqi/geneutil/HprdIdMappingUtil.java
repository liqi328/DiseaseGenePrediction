package com.liqi.geneutil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.model.HprdIdMapping;
import com.liqi.reader.ReaderFacade;

/*
 * HPRD 数据库中的HPRD_ID_MAPPINGS.txt
 * */
public class HprdIdMappingUtil {
	private static Map<String, HprdIdMapping> hprdIdIndexedIdMappingMap = null;
	private static Map<String, HprdIdMapping> omimIdIndexedIdMappingMap = null;
	private static Map<String, HprdIdMapping> entrezIdIndexIdMappingMap = null;
	
	
	public static Map<String, HprdIdMapping> getHprdIdIndexIdMapping(){
		if(hprdIdIndexedIdMappingMap == null){
			hprdIdIndexedIdMappingMap = ReaderFacade.getInstance().getHprdIdIndexedIdMappingMap(
				"E:/2013疾病研究/疾病数据/HumanPPI/HPRD_Release9_062910/FLAT_FILES_072010/HPRD_ID_MAPPINGS.txt");
			initOtherIdMappingMap();
			//System.out.println("Init HprdIdIndexedIdMapping Map.");
		}
		return hprdIdIndexedIdMappingMap;
	}
	
	public static  Map<String, HprdIdMapping> getOmimIdIndexedIdMapping(){
		if(omimIdIndexedIdMappingMap == null){
			getHprdIdIndexIdMapping();
		}
		
		return omimIdIndexedIdMappingMap;
	}
	
	public static  Map<String, HprdIdMapping> getEntrezIdIndexedIdMapping(){
		if(entrezIdIndexIdMappingMap == null){
			getHprdIdIndexIdMapping();
		}
		
		return entrezIdIndexIdMappingMap;
	}
	
	private static  void initOtherIdMappingMap(){
		omimIdIndexedIdMappingMap = new HashMap<String, HprdIdMapping>();
		entrezIdIndexIdMappingMap = new HashMap<String, HprdIdMapping>();
		
		Iterator<HprdIdMapping> itr = hprdIdIndexedIdMappingMap.values().iterator();
		HprdIdMapping idMapping = null;
		while(itr.hasNext()){
			idMapping = itr.next();
			addToOmimIndexedIdMappingMap(idMapping);
			
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

}

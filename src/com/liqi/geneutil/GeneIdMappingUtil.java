package com.liqi.geneutil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.model.Gene;
import com.liqi.model.HprdIdMapping;
import com.liqi.reader.ReaderFacade;


/* �����в�ͬ��ID,��Hprd_id, Omim_id, entrez_id��
 * 
 * ���ฺ��һ�ֵ�ID������Gene Map ת��������ID������Gene Map
 * 
 * ���磺����һ����Omim_id�����Ļ���Map<String, Gene>,Ҳ����StringΪOmim_id,
 * ������Խ���װ��������Id�����Ļ���Map<String, Gene>. 
 * */
public class GeneIdMappingUtil {
	
	/**
	 *  ��Omim_id������Gene Mapװ���� Hprd_id������Gene Map
	 * */
	public static Map<String, Gene> transform2HprdIdIndexedGeneMap(Map<String, Gene> omimIdIdexedGeneMap){
		Map<String, HprdIdMapping> omimIdIndexedIdMappingMap = HprdIdMappingUtil.getOmimIdIndexedIdMapping();
		
		Map<String, Gene> hprdIdIndexedDiseaseGeneMap = new HashMap<String, Gene>();
		
		Iterator<Gene> itr = omimIdIdexedGeneMap.values().iterator();
		
		Gene gene = null;
		HprdIdMapping idMapping = null;
		while(itr.hasNext()){
			gene = itr.next();
			idMapping = omimIdIndexedIdMappingMap.get(gene.getOmimId());
			
			if(idMapping == null){
				//System.out.println(gene.getOmimId());
				continue;
			}
			gene.setHprdId(String.valueOf(Integer.parseInt(idMapping.getHrpdId())));
			gene.setEntrezId(idMapping.getEntrezGeneId());
			hprdIdIndexedDiseaseGeneMap.put(gene.getHprdId(), gene);
		}
		return hprdIdIndexedDiseaseGeneMap;
	}
	
	/** 
	 * ��Omim_id������Gene Mapװ���� symbol������Gene Map
	 * */
	public static Map<String, Gene> transform2SymbolIndexedGeneMap(Map<String, Gene> omimIdIdexedGeneMap){
		
		Map<String, Gene> symbolIndexedDiseaseGeneMap = new HashMap<String, Gene>();
		
		Iterator<Gene> itr = omimIdIdexedGeneMap.values().iterator();
		
		Gene gene = null;
		String[] symbols = null;
		while(itr.hasNext()){
			gene = itr.next();
			symbols = gene.getSymbols().split(",");
			for(String sym: symbols){
				symbolIndexedDiseaseGeneMap.put(sym.trim(), gene);
			}
		}
		return symbolIndexedDiseaseGeneMap;
	}
	
	
	/**
	 * Omim���������б���ֻ��symbol, 
	 * �˷�������Gene��HprdId��entrezId,omimid
	 * 
	 * 1���²���������ЩOMIMδ������HPRD_ID_MAPPING.txt��
	 * 2��HPRD_ID_MAPPING.txt�е���Щhprd_idû�ж�Ӧ��omim_id
	 * 
	 * */
	public static void setOtherGeneId2(Map<String, Gene> symbolIndexedDiseaseGeneMap){
		Map<String, HprdIdMapping> symbolIdIndexedIdMappingMap = HprdIdMappingUtil.getSymbolIdIndexedIdMapping();
		//System.out.println("getSymbolIdIndexedIdMapping = " + symbolIdIndexedIdMappingMap.size());
		System.out.println("1 before: Disease Gene Map size = " + symbolIndexedDiseaseGeneMap.size());
		Iterator<Map.Entry<String, Gene>> itr = symbolIndexedDiseaseGeneMap.entrySet().iterator();
		Gene gene = null;
		Map.Entry<String, Gene> entry = null;
		HprdIdMapping hprdIdMapping = null;
		while(itr.hasNext()){
			entry = itr.next();
			gene = entry.getValue();
			hprdIdMapping = symbolIdIndexedIdMappingMap.get(gene.getSymbols());
			if(hprdIdMapping == null){
				itr.remove();
				System.out.println("Disease Gene[omim:" + gene.getOmimId()+", hprdid:" + gene.getHprdId() + ", symbols:"
						+ gene.getSymbols() +" not in the HPRD_ID_MAPPING.txt.");
				gene.setHprdId("---null---");
				gene.setEntrezId("---null---");
				gene.setOmimId("---null---");
				continue;
			}
			gene.setEntrezId(hprdIdMapping.getEntrezGeneId());
			gene.setHprdId(hprdIdMapping.getHrpdId());
			gene.setOmimId(hprdIdMapping.getOmimId());
		}
		System.out.println("2 after: Disease Gene Map size = " + symbolIndexedDiseaseGeneMap.size());
	}

	/**
	 * Omim���������б���ֻ��OmimId, 
	 * �˷�������Gene��HprdId��entrezId
	 * 
	 * 1���²���������ЩOMIMδ������HPRD_ID_MAPPING.txt��
	 * 2��HPRD_ID_MAPPING.txt�е���Щhprd_idû�ж�Ӧ��omim_id
	 * 
	 * */
	public static void setOtherGeneId(Map<String, Gene> omimIdIdexedGeneMap){
		Map<String, HprdIdMapping> omimIdIndexedIdMappingMap = HprdIdMappingUtil.getOmimIdIndexedIdMapping();
		//System.out.println("getOmimIdIndexedIdMapping = " + omimIdIndexedIdMappingMap.size());
		System.out.println("1 before: Disease Gene Map size=" + omimIdIdexedGeneMap.size());
		Iterator<Map.Entry<String, Gene>> itr = omimIdIdexedGeneMap.entrySet().iterator();
		Gene gene = null;
		Map.Entry<String, Gene> entry = null;
		HprdIdMapping hprdIdMapping = null;
		while(itr.hasNext()){
			entry = itr.next();
			gene = entry.getValue();
			hprdIdMapping = omimIdIndexedIdMappingMap.get(gene.getOmimId());
			if(hprdIdMapping == null){
				itr.remove();
				System.out.println("Disease Gene[omim:" + gene.getOmimId()+", hprdid:" + gene.getHprdId() + ", symbols:"
							+ gene.getSymbols() +" not in the HPRD_ID_MAPPING.txt.");
				gene.setHprdId("---null---");
				gene.setEntrezId("---null---");
				continue;
			}
			gene.setEntrezId(hprdIdMapping.getEntrezGeneId());
			gene.setHprdId(hprdIdMapping.getHrpdId());
			gene.setSymbols(hprdIdMapping.getGeneSymbol());
		}
		System.out.println("2 after: Disease Gene Map size=" + omimIdIdexedGeneMap.size());
	}

}

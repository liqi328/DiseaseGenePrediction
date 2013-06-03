package com.liqi.geneutil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.model.Gene;
import com.liqi.model.HprdIdMapping;
import com.liqi.reader.ReaderFacade;


/* 基因有不同的ID,如Hprd_id, Omim_id, entrez_id等
 * 
 * 此类负责将一种的ID索引的Gene Map 转换成其它ID索引的Gene Map
 * 
 * 例如：现有一个用Omim_id索引的基因Map<String, Gene>,也就是String为Omim_id,
 * 此类可以将它装换成其它Id索引的基因Map<String, Gene>. 
 * */
public class GeneIdMappingUtil {
	
	/* 将Omim_id索引的Gene Map装换成 Hprd_id索引的Gene Map
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
	
	/* 将Omim_id索引的Gene Map装换成 symbol索引的Gene Map
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
	
	
	/*
	 * Omim疾病基因列表中只有OmimId, 
	 * 此方法设置Gene的HprdId和entrezId
	 * 
	 * 1：致病基因中有些OMIM未出现在HPRD_ID_MAPPING.txt中
	 * 2：HPRD_ID_MAPPING.txt中的有些hprd_id没有对应的omim_id
	 * 
	 * */
	public static void setOtherGeneId(Map<String, Gene> omimIdIdexedGeneMap){
		Map<String, HprdIdMapping> omimIdIndexedIdMappingMap = HprdIdMappingUtil.getOmimIdIndexedIdMapping();
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
				System.out.println("Disease Gene:" + gene.getOmimId() +" not in the HPRD_ID_MAPPING.txt.");
				gene.setHprdId("---null---");
				gene.setEntrezId("---null---");
				continue;
			}
			gene.setEntrezId(hprdIdMapping.getEntrezGeneId());
			gene.setHprdId(hprdIdMapping.getHrpdId());
		}
		System.out.println("2 after: Disease Gene Map size=" + omimIdIdexedGeneMap.size());
	}

}

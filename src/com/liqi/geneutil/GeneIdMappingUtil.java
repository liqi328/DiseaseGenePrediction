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

}

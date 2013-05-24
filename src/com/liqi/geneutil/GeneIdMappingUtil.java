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
	
	/* ��Omim_id������Gene Mapװ���� Hprd_id������Gene Map
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

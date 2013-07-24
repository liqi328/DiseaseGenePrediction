package com.liqi.geneutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.liqi.model.HprdIdMapping;
import com.liqi.util.WriterUtil;


public abstract class IDConvertor{
	public abstract Set<String> converte2hprdId(Set<String> idSet);
	public abstract Set<String> converte2entrezId(Set<String> idSet);
	public abstract Set<String> converte2omimId(Set<String> idSet);
	
	public abstract Set<HprdIdMapping> getHprdIdMappingSet(Set<String> idSet);
	
	protected static void write(String filename, Set<?> set){
		StringBuffer sb = new StringBuffer();
		Iterator<?> itr = set.iterator();
		while(itr.hasNext()){
			sb.append(itr.next()).append("\n");
		}
		
		WriterUtil.write(filename, sb.toString());
	}
	
	protected static Set<String> readFile(String filename){
		Set<String> list = new HashSet<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
			String line = null;
			
			while((line = in.readLine()) != null){
				list.add(line.trim());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}

/**
 * 将omim_id转换为Hprd_id, entrez_Id
 * @author Liqi
 *
 */
class OmimIDConvertor extends IDConvertor{
	/**
	 * 讲OMIM_ID集合转换为对应的HPRD_ID集合
	 * @param omimIdSet	OMIM_ID 集合
	 * @return				HPRD_ID集合
	 */
	public Set<String> converte2hprdId(Set<String> omimIdSet){
		Map<String, HprdIdMapping> omimIdIndexedIdMappingMap = HprdIdMappingUtil.getOmimIdIndexedIdMapping();
		
		Set<String> hprdIdSet = new HashSet<String>();
		Iterator<String> itr = omimIdSet.iterator();
		while(itr.hasNext()){
			String omimId = itr.next();
			HprdIdMapping idMapping = omimIdIndexedIdMappingMap.get(omimId);
			if(idMapping != null){
				hprdIdSet.add(String.valueOf(Integer.parseInt(idMapping.getHrpdId())));
			}else{
				System.out.println("omim_id: " + omimId + " has not find corresponding hprd_id.");
			}
		}
		
		return hprdIdSet;
	}
	
	public Set<String> converte2entrezId(Set<String> omimIdSet){
		Set<String> entrezIdSet = new HashSet<String>();
		
		Map<String, HprdIdMapping> omimIdIndexedIdMappingMap = HprdIdMappingUtil.getOmimIdIndexedIdMapping();
		Iterator<String> itr = omimIdSet.iterator();
		while(itr.hasNext()){
			String omimId = itr.next();
			HprdIdMapping idMapping = omimIdIndexedIdMappingMap.get(omimId);
			if(idMapping != null){
				entrezIdSet.add(String.valueOf(Integer.parseInt(idMapping.getEntrezGeneId())));
			}
		}
		return entrezIdSet;
	}
	
	@Override
	public Set<String> converte2omimId(Set<String> idSet) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Set<HprdIdMapping> getHprdIdMappingSet(Set<String> hprdIdSet){
		Map<String, HprdIdMapping> hprdIdIndexedIdMappingMap = HprdIdMappingUtil.getHprdIdIndexIdMapping();
		Set<HprdIdMapping> result = new HashSet<HprdIdMapping>();
		Iterator<String> itr = hprdIdSet.iterator();
		while(itr.hasNext()){
			result.add(hprdIdIndexedIdMappingMap.get(itr.next()));
		}
		return result;
	}
	
	public static void main(String[] args){
		String filename = "E:/2013疾病研究/实验数据/prioritizing_candidate_gene/sca_omimid.txt";
		Set<String> omimIdset = readFile(filename);
		
		IDConvertor convertor = new OmimIDConvertor();
		
		Set<String> hprdIdSet = convertor.converte2hprdId(omimIdset);		
		write("E:/2013疾病研究/实验数据/prioritizing_candidate_gene/sca_hprdid.txt", hprdIdSet);
		
		write("E:/2013疾病研究/实验数据/prioritizing_candidate_gene/debug_id_mapping.txt",
				convertor.getHprdIdMappingSet(readFile("E:/2013疾病研究/实验数据/prioritizing_candidate_gene/debug.txt")));
		
		Set<String> entrezIdset = convertor.converte2entrezId(omimIdset);
		write("E:/2013疾病研究/实验数据/prioritizing_candidate_gene/sca_entrezid.txt", entrezIdset);
	}
}

/**
 * 将entrez_Id转换为Hprd_id, omim_id
 * @author Liqi
 *
 */
class EntrezIDConvertor extends IDConvertor{

	@Override
	public Set<String> converte2hprdId(Set<String> entrezIdSet) {
		Map<String, HprdIdMapping> entrezIdIndexedIdMappingMap = HprdIdMappingUtil.getEntrezIdIndexedIdMapping();
		
		Set<String> hprdIdSet = new HashSet<String>();
		Iterator<String> itr = entrezIdSet.iterator();
		while(itr.hasNext()){
			String entrezId = itr.next();
			HprdIdMapping idMapping = entrezIdIndexedIdMappingMap.get(entrezId);
			if(idMapping != null){
				hprdIdSet.add(String.valueOf(Integer.parseInt(idMapping.getHrpdId())));
			}else{
				System.out.println("entrez_id: "+ entrezId + " has not find corresponding hprd_id.");
			}
		}
		
		return hprdIdSet;
	}
	
	@Override
	public Set<String> converte2omimId(Set<String> entrezIdSet) {
		Map<String, HprdIdMapping> entrezIdIndexedIdMappingMap = HprdIdMappingUtil.getEntrezIdIndexedIdMapping();
		
		Set<String> hprdIdSet = new HashSet<String>();
		Iterator<String> itr = entrezIdSet.iterator();
		while(itr.hasNext()){
			String entrezId = itr.next();
			HprdIdMapping idMapping = entrezIdIndexedIdMappingMap.get(entrezId);
			if(idMapping != null){
				hprdIdSet.add(String.valueOf(Integer.parseInt(idMapping.getOmimId())));
			}else{
				System.out.println("entrez_id: "+ entrezId + " has not find corresponding hprd_id.");
			}
		}
		
		return hprdIdSet;
	}
	

	@Override
	public Set<String> converte2entrezId(Set<String> entrezIdSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<HprdIdMapping> getHprdIdMappingSet(Set<String> entrezIdSet) {
		Map<String, HprdIdMapping> entrezIdIndexedIdMappingMap = HprdIdMappingUtil.getEntrezIdIndexedIdMapping();
		
		Set<HprdIdMapping> result = new HashSet<HprdIdMapping>();
		Iterator<String> itr = entrezIdSet.iterator();
		while(itr.hasNext()){
			result.add(entrezIdIndexedIdMappingMap.get(itr.next()));
		}
		return result;
	}
	
	public static void main(String[] args){
		String filename = "E:/2013疾病研究/实验数据/prioritizing_candidate_gene/orphanet/Achromatopsia_entrez_id.txt";
		Set<String> entrezIdset = readFile(filename);
		
		IDConvertor convertor = new EntrezIDConvertor();
		
		Set<String> hprdIdSet = convertor.converte2hprdId(entrezIdset);		
		write("E:/2013疾病研究/实验数据/prioritizing_candidate_gene/orphanet/Achromatopsia_hprd_id.txt", hprdIdSet);
		
		write("E:/2013疾病研究/实验数据/prioritizing_candidate_gene/orphanet/Achromatopsia_id_mapping.txt",
				convertor.getHprdIdMappingSet(readFile(filename)));
		
		Set<String> omimIdset = convertor.converte2omimId(entrezIdset);
		write("E:/2013疾病研究/实验数据/prioritizing_candidate_gene/orphanet/Achromatopsia_omim_id.txt", omimIdset);
	}

	
}

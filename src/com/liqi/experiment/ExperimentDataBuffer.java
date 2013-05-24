package com.liqi.experiment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Graph;
import com.liqi.model.Disease;
import com.liqi.model.Gene;
import com.liqi.model.HprdIdMapping;
import com.liqi.model.Tissue;
import com.liqi.model.TissueSpecificGeneExpression;
import com.liqi.reader.ReaderFacade;

public class ExperimentDataBuffer {
	private InputArgument inputArg;
	
	/* 人类的PPI网络 */
	private Graph ppi = null;	
	
	/* 组织数据 
	 * String:组织名称
	 * */
	private Map<String, Tissue> tissueMap = null;
	
	/* 组织特异性基因表达数据
	 * String:entrezGeneid
	 * */
	private Map<String, TissueSpecificGeneExpression> tissueSpecificGeneExpressionMap = null;
	
	/* HPRD 数据库中的HPRD_ID_MAPPINGS.txt
	 * String: hprdId
	 * */
	private Map<String, HprdIdMapping> hprdIdMappingMap = null;
	/* HPRD 数据库中的HPRD_ID_MAPPINGS.txt
	 * String: omim_id
	 * */
	private Map<String, HprdIdMapping> omimIdMapppingMap = null;
	/* HPRD 数据库中的HPRD_ID_MAPPINGS.txt
	 * String: entrez_gene_id
	 * */
	private Map<String, HprdIdMapping> entrezIdMappingMap = null;
	
	/* 致病基因 map
	 * String:omimId*/
	private Map<String, Gene> diseaseGeneMap = null;
	
	/* 疾病数据 
	 * String:diseaseName*/
	private Map<String, Disease> diseaseMap = null;
	
	public ExperimentDataBuffer(InputArgument inputArg){
		this.inputArg = inputArg;
	}

	public Graph getPpi() {
		if(ppi == null){
			ppi = ReaderFacade.getInstance().getPPI(inputArg.getPpiFilename());
		}
		return ppi;
	}

	public Map<String, Tissue> getTissueMap() {
		if(tissueMap == null){
			tissueMap = ReaderFacade.getInstance().getTissueMap(
					inputArg.getTissueSpecificGeneExpressionFilename());
		}
		return tissueMap;
	}

	public Map<String, TissueSpecificGeneExpression> getTissueSpecificGeneExpressionMap() {
		if(tissueSpecificGeneExpressionMap == null){
			tissueSpecificGeneExpressionMap = ReaderFacade.getInstance().getTissueSpecificGeneExpressionMap(
					inputArg.getTissueSpecificGeneExpressionFilename());
		}
		return tissueSpecificGeneExpressionMap;
	}

	public Map<String, HprdIdMapping> getHprdIdMappingMap() {
		if(hprdIdMappingMap == null){
			hprdIdMappingMap = ReaderFacade.getInstance().getHprdIdIndexedIdMappingMap(
					inputArg.getIdMappingFilename());
			initIdMappingMap();
		}
		return hprdIdMappingMap;
	}
	
	public Map<String, HprdIdMapping> getOmimIdMappingMap(){
		getHprdIdMappingMap();
		return omimIdMapppingMap;
	}
	
	public Map<String, HprdIdMapping> getEntrezIdMappingMap(){
		getHprdIdMappingMap();
		return entrezIdMappingMap;
	}

	public Map<String, Gene> getDiseaseGeneMap() {
		if(diseaseGeneMap == null){
			diseaseGeneMap = ReaderFacade.getInstance().getDiseaseGeneMap(
					inputArg.getGeneDiseaseAssociationFilename());
			initGene();
		}
		
		return diseaseGeneMap;
	}

	public Map<String, Disease> getDiseaseMap() {
		if(diseaseMap == null){
			diseaseMap = ReaderFacade.getInstance().getDiseaseMap(
					inputArg.getGeneDiseaseAssociationFilename());
		}
		return diseaseMap;
	}
	
	private void initIdMappingMap(){
		omimIdMapppingMap = new HashMap<String, HprdIdMapping>();
		entrezIdMappingMap = new HashMap<String, HprdIdMapping>();
		
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
			entrezIdMappingMap.put(idMapping.getEntrezGeneId(), idMapping);
		}
	}
	
	/*
	 * 1：致病基因中有些OMIM未出现在HPRD_ID_MAPPING.txt中
	 * 2：HPRD_ID_MAPPING.txt中的有些hprd_id没有对应的omim_id
	 * 
	 * */
	private void initGene(){
		getHprdIdMappingMap();
		System.out.println("1 before: Disease Gene Map size=" + diseaseGeneMap.size());
		Iterator<Map.Entry<String, Gene>> itr = diseaseGeneMap.entrySet().iterator();
		Gene gene = null;
		Map.Entry<String, Gene> entry = null;
		HprdIdMapping hprdIdMapping = null;
		while(itr.hasNext()){
			entry = itr.next();
			gene = entry.getValue();
			hprdIdMapping = omimIdMapppingMap.get(gene.getOmimId());
			if(hprdIdMapping == null){
				itr.remove();
				//diseaseGeneMap.remove(gene.getOmimId());
				System.out.println("Disease Gene:" + gene.getOmimId() +" not in the HPRD ppi.");
				continue;
			}
			gene.setEntrezId(hprdIdMapping.getEntrezGeneId());
			gene.setHprdId(hprdIdMapping.getHrpdId());
		}
	}
}

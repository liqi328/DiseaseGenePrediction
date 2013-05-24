package com.liqi.reader;

import java.util.Map;

import com.liqi.graph.Graph;
import com.liqi.model.Disease;
import com.liqi.model.DiseaseTissueAssociation;
import com.liqi.model.Gene;
import com.liqi.model.HprdIdMapping;
import com.liqi.model.Tissue;
import com.liqi.model.TissueSpecificGeneExpression;

public class ReaderFacade {
	private static ReaderFacade INSTANCE = new ReaderFacade();
		
	/* 组织数据 
	 * String:组织名称
	 * */
	private Map<String, Tissue> tissueMap = null;
	
	/* 组织特异性基因表达数据
	 * String:entrezGeneid
	 * */
	private Map<String, TissueSpecificGeneExpression> tissueSpecificGeneExpressionMap = null;	
	
	/* 致病基因 map
	 * String:omimId*/
	private Map<String, Gene> diseaseGeneMap = null;
	
	Map<String, Gene> omimIdIndexedDiseaseGeneMap = null;
	
	/* 疾病数据 
	 * String:diseaseName*/
	private Map<String, Disease> diseaseMap = null;
	
	private Map<String, Disease> omimDiseaseMap = null;
	
	private ReaderFacade(){}
	
	public static ReaderFacade getInstance(){
		return INSTANCE;
	}
	
	public Map<String, Gene> getDiseaseGeneMap(String filename){
		readGeneDiseaseAssociation(filename);
		return this.diseaseGeneMap;
	}
	
	public Map<String, Disease> getDiseaseMap(String filename){
		readGeneDiseaseAssociation(filename);
		return this.diseaseMap;
	}
	
	public Map<String, Gene> getOmimDiseaseGeneMap(){
		String filename = "E:/2013疾病研究/疾病数据/OMIM/morbidmap";
		readOmimGeneDiseaseAssociation(filename);
		return omimIdIndexedDiseaseGeneMap;
	} 
	
	public Map<String, Disease> getOmimDiseaseMap(){
		String filename = "E:/2013疾病研究/疾病数据/OMIM/morbidmap";
		readOmimGeneDiseaseAssociation(filename);
		return omimDiseaseMap;
	}
	
	public Graph getPPI(String filename){
		PPIReader reader = new PPIReader(filename);
		reader.read();
		/* 人类的PPI网络 */
		Graph ppi = reader.getPPI();
		return ppi;
	}
	
	public Map<String, DiseaseTissueAssociation> getDiseaseTissueAssociationMap(String filename){
		DiseaseTissueAssociationReader reader = new DiseaseTissueAssociationReader(filename);
		reader.read();
		
		/* 疾病与组织的关联关系
		 * String: 疾病的OMIM_ID
		 * */
		Map<String, DiseaseTissueAssociation> diseaseTissueAssMap = reader.getDiseaseTissueAssociationMap();
		
		return diseaseTissueAssMap;
	}
	
	public Map<String, HprdIdMapping> getHprdIdIndexedIdMappingMap(String filename){
		HprdIdMappingReader reader = new HprdIdMappingReader(filename);
		reader.read();
		
		/* HPRD 数据库中的HPRD_ID_MAPPINGS.txt
		 * String:hprdId
		 * */
		Map<String, HprdIdMapping> hprdIdMappingMap = reader.getHprdIdMappingMap();
		return hprdIdMappingMap;
	}
	
	public Map<String, Tissue> getTissueMap(String filename){
		readTissueSpecificGeneExpression(filename);
		return this.tissueMap;
	}
	
	public Map<String, TissueSpecificGeneExpression> getTissueSpecificGeneExpressionMap(String filename){
		readTissueSpecificGeneExpression(filename);
		return this.tissueSpecificGeneExpressionMap;
	}
	
	private void readTissueSpecificGeneExpression(String filename){
		TissuSpecificGeneExpressionReader reader = new TissuSpecificGeneExpressionReader(filename);
		reader.read();
		tissueMap = reader.getTissueMap();
		tissueSpecificGeneExpressionMap = reader.getTissueSpecificGeneExpressionMap();
	}
	
	private void readGeneDiseaseAssociation(String filename){
		GeneDiseaseAssociationReader reader = new GeneDiseaseAssociationReader(filename);
		reader.read();
		diseaseMap = reader.getDiseaseMap();
		diseaseGeneMap = reader.getDiseaseGeneMap();
	}
	
	private void readOmimGeneDiseaseAssociation(String filename){
		OmimGeneDiseaseAssociationReader reader = new OmimGeneDiseaseAssociationReader(
				filename);
		reader.read();
		omimDiseaseMap = reader.getOmimDiseaseMap();
		omimIdIndexedDiseaseGeneMap = reader.getOmimDiseaseGeneMap();
	}

}

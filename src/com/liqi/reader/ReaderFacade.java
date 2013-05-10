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
	private ReaderFacade facade = new ReaderFacade();
	
	private Graph ppi = null;
	private Map<String, DiseaseTissueAssociation> diseaseTissueAssMap = null;
	private Map<String, Tissue> tissueMap = null;
	private Map<String, TissueSpecificGeneExpression> tsGeneExpressionMap = null;
	private Map<String, HprdIdMapping> hprdIdMappingMap = null;
	
	/*String:omimId*/
	private Map<String, Gene> diseaseGeneMap = null;
	/*String:diseaseName*/
	private Map<String, Disease> diseaseMap = null;
	
	private ReaderFacade(){}
	
	public ReaderFacade getInstance(){
		return this.facade;
	}
	
	public Map<String, Gene> getDiseaseGeneMap(String filename){
		if(diseaseGeneMap == null){
			readGeneDiseaseAssociation(filename);
		}
		return this.diseaseGeneMap;
	}
	
	public Map<String, Disease> getDiseaseMap(String filename){
		if(diseaseMap == null){
			readGeneDiseaseAssociation(filename);
		}
		return this.diseaseMap;
	}
	
	public Graph getPPI(String filename){
		if(ppi == null){
			PPIReader reader = new PPIReader(filename);
			reader.read();
			ppi = reader.getPPI();
		}
		return this.ppi;
	}
	
	public Map<String, DiseaseTissueAssociation> getDiseaseTissueAssociationMap(String filename){
		if(diseaseTissueAssMap == null){
			DiseaseTissueAssociationReader reader = new DiseaseTissueAssociationReader(filename);
			reader.read();
			diseaseTissueAssMap = reader.getDiseaseTissueAssociationMap();
		}
		
		return diseaseTissueAssMap;
	}
	
	public Map<String, HprdIdMapping> getHprdIdMappingMap(String filename){
		if(hprdIdMappingMap == null){
			HprdIdMappingReader reader = new HprdIdMappingReader(filename);
			reader.read();
			hprdIdMappingMap = reader.getHprdIdMappingMap();
		}
		return this.hprdIdMappingMap;
	}
	
	public Map<String, Tissue> getTissueMap(String filename){
		if(tissueMap == null){
			readTissueSpecificGeneExpression(filename);
		}
		return this.tissueMap;
	}
	
	public Map<String, TissueSpecificGeneExpression> getTissueSpecificGeneExpressionMap(String filename){
		if(tsGeneExpressionMap == null){
			readTissueSpecificGeneExpression(filename);
		}
		return this.tsGeneExpressionMap;
	}
	
	
	private void readTissueSpecificGeneExpression(String filename){
		TissuSpecificGeneExpressionReader reader = new TissuSpecificGeneExpressionReader(filename);
		reader.read();
		tissueMap = reader.getTissueMap();
		tsGeneExpressionMap = reader.getTissueSpecificGeneExpressionMap();
	}
	
	private void readGeneDiseaseAssociation(String filename){
		GeneDiseaseAssociationReader reader = new GeneDiseaseAssociationReader(filename);
		reader.read();
		diseaseMap = reader.getDiseaseMap();
		diseaseGeneMap = reader.getDiseaseGeneMap();
	}

}

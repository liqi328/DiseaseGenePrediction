package com.liqi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.liqi.model.Disease;
import com.liqi.model.Gene;

public class GeneDiseaseAssociationReader extends AbstractModelReader{
	/*String:omimId*/
	private Map<String, Gene> diseaseGeneMap;
	/*String:diseaseName*/
	private Map<String, Disease> diseaseMap;
	
	public GeneDiseaseAssociationReader(String filename) {
		super(filename);
		diseaseMap = new HashMap<String, Disease>();
		diseaseGeneMap = new HashMap<String, Gene>();
	}
	
	public Map<String, Gene> getDiseaseGeneMap(){
		return this.diseaseGeneMap;
	}
	
	public Map<String, Disease> getDiseaseMap(){
		return this.diseaseMap;
	}

	@Override
	public void read() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(super.filename)));
			String line = in.readLine();
			Gene gene = null;
			Disease d = null;
			while((line = in.readLine()) != null){
				gene = createOneGene(line);
				d = createOneDisease(line);
				d.addGene(gene);				
				gene.addDisease(d);
			}
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	private Gene createOneGene(String line){
		String[] values = line.split("\t");
		Gene gene = null;
		
		if(diseaseGeneMap.containsKey(values[1])){
			gene = diseaseGeneMap.get(values[1]);
		}else{
			gene = new Gene();
			gene.setSymbols(values[0]);
			gene.setOmimId(values[1]);
			diseaseGeneMap.put(gene.getOmimId(), gene);
		}
	
		return gene;
	}
	
	private Disease createOneDisease(String line){
		String[] values = line.split("\t");
		Disease d = null;
		
		if(diseaseMap.containsKey(values[2])){
			d = diseaseMap.get(values[2]);
		}else{
			d = new Disease();
			d.setName(values[2]);
			diseaseMap.put(d.getName(), d);
		}
		return d;
	}

}

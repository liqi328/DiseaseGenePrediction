package com.liqi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.liqi.model.Disease;
import com.liqi.model.Gene;

public class DiseaseGeneReader extends AbstractModelReader{
	private Map<String, Disease> diseaseMap;
	
	public DiseaseGeneReader(String filename) {
		super(filename);
		diseaseMap = new HashMap<String, Disease>();
	}
	
	public Map<String, Disease> getDiseaseMap(){
		return this.diseaseMap;
	}

	@Override
	public void read() {
		try {
			File f = new File(super.filename);
			InputStreamReader readIn = new InputStreamReader (new FileInputStream(f),"UTF-8");
			BufferedReader in=new BufferedReader(readIn);
			//BufferedReader in = new BufferedReader(new FileReader(new File(super.filename)));
			String line = null;
			Gene gene = null;
			Disease d = null;
			String[] values = null;
			while((line = in.readLine()) != null){
				values = line.split("\\|");
				d = createOneDisease(values[0]);
				addGenes(values[1].split("\t"), d);
			}
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void addGenes(String[] genes, Disease d){
		Gene gene = null;
		for(String g : genes){
			gene = new Gene();
			gene.setOmimId(g.trim());
			d.addGene(gene);
		}
		
	}
	
	private Disease createOneDisease(String value){
		Disease d = null;
		d = new Disease();
		d.setName(value);
		diseaseMap.put(value, d);
		return d;
	}


}

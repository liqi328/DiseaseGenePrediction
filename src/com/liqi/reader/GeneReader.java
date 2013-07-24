package com.liqi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.liqi.model.Gene;

public class GeneReader extends AbstractModelReader{
	private Map<String, Gene> hprdIdIndexedGeneMap;
	public GeneReader(String filename) {
		super(filename);
		hprdIdIndexedGeneMap = new HashMap<String, Gene>();
	}
	
	public Map<String, Gene> getGeneMap(){
		return this.hprdIdIndexedGeneMap;
	}

	@Override
	public void read() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
			
			String line = null;
			while((line = in.readLine()) != null){
				addOneGene(line);
			}			
			in.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addOneGene(String line){
		Gene gene = new Gene();
		gene.setHprdId(line);
		hprdIdIndexedGeneMap.put(line, gene);
	}

}

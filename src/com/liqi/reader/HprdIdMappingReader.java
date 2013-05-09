package com.liqi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.liqi.model.HprdIdMapping;

public class HprdIdMappingReader extends ModelReader {

	private Map<String, HprdIdMapping> hprdIdMappingMap;
	
	public HprdIdMappingReader(String filename) {
		super(filename);
		hprdIdMappingMap = new HashMap<String, HprdIdMapping>();
	}
	
	public Map<String, HprdIdMapping> getHprdIdMappingMap(){
		return this.hprdIdMappingMap;
	}

	@Override
	public void read() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(super.filename)));
			String line = in.readLine();
			HprdIdMapping hm = null;
			while((line = in.readLine()) != null){
				hm = createHprdIdMapping(line);
				hprdIdMappingMap.put(hm.getHrpdId(), hm);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private HprdIdMapping createHprdIdMapping(String line){
		HprdIdMapping hm = new HprdIdMapping();
		String[] values = line.split("\t");
		hm.setHrpdId(values[0]);
		hm.setGeneSymbol(values[1]);
		hm.setNucleotideAccession(values[2]);
		hm.setProteinAccession(values[3]);
		hm.setEntrezGeneId(values[4]);
		hm.setOmimId(values[5]);
		hm.setSwissprotId(values[6]);
		hm.setMainName(values[7]);
		return hm;
	}

}

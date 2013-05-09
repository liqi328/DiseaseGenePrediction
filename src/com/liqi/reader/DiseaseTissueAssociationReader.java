package com.liqi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.liqi.model.DiseaseTissueAssociation;

public class DiseaseTissueAssociationReader extends ModelReader {
	private Map<String, DiseaseTissueAssociation> diseaseTissueAssMap;
	
	public DiseaseTissueAssociationReader(String filename) {
		super(filename);
		diseaseTissueAssMap = new HashMap<String, DiseaseTissueAssociation>();
	}
	
	public Map<String, DiseaseTissueAssociation> getDiseaseTissueAssociationMap(){
		return this.diseaseTissueAssMap;
	}

	@Override
	public void read() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
			String line = in.readLine();
			String[] header = createHeader(line);
			
			DiseaseTissueAssociation dta = null;
			while((line = in.readLine()) != null){
				dta = createDiseaseTissueAssociation(line, header);
				diseaseTissueAssMap.put(dta.getDiseaseOmimId(), dta);
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String[] createHeader(String line){
		String[] header = line.split("\t");
		return header;
	}
	
	private DiseaseTissueAssociation createDiseaseTissueAssociation(String line, String[] header){
		DiseaseTissueAssociation dta = new DiseaseTissueAssociation();
		String[] values = line.split("\t");
		
		int i=0;
		dta.setDiseaseOmimId(values[i++]);
		for(; i<header.length; ++i){
			dta.setAssociationValue(header[i], Double.parseDouble(values[i]));
		}
		
		return dta;
	}

}

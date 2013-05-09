package com.liqi.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* 疾病与组织关联矩阵
 * 
 * */
public class DiseaseTissueAssociation {
	private String diseaseOmimId;
	
	private Map<String, Double> associationMap;
	
	public DiseaseTissueAssociation(){
		associationMap = new LinkedHashMap<String, Double>();
	}

	public Iterator<Map.Entry<String, Double>> createAssociationIterator(){
		return associationMap.entrySet().iterator();
	}
	
	public void setAssociationValue(String key, double value){
		associationMap.put(key, value);
	}
	
	public double getAssociationValue(String key){
		return associationMap.get(key);
	}
	
	public String getDiseaseOmimId() {
		return diseaseOmimId;
	}

	public void setDiseaseOmimId(String diseaseOmimId) {
		this.diseaseOmimId = diseaseOmimId;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(diseaseOmimId).append("\t");
		
		Map.Entry<String, Double> entry = null;
		Iterator<Map.Entry<String, Double>> itr = associationMap.entrySet().iterator();
		while(itr.hasNext()){
			entry = itr.next();
			sb.append(entry.getValue()).append("\t");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	
}

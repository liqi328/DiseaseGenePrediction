package com.liqi.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TissueSpecificGeneExpression {
	private String identifier;
	private String entrezId;
	private String geneSymbol;
	private Map<String, Double> expressionValue;
	
	public TissueSpecificGeneExpression(){
		expressionValue = new LinkedHashMap<String, Double>();
	}
	
	public void setExpressionValue(String key, double value){
		expressionValue.put(key, value);
	}
	
	public double getExpressionValue(String key){
		return expressionValue.get(key);
	}
	
	public Iterator<Map.Entry<String, Double>> createExpressionValueIterator(){
		return expressionValue.entrySet().iterator();
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getEntrezId() {
		return entrezId;
	}
	public void setEntrezId(String entrezId) {
		this.entrezId = entrezId;
	}
	public String getGeneSymbol() {
		return geneSymbol;
	}
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(entrezId).append("\t");
		sb.append(identifier).append("\t");
		sb.append(geneSymbol).append("\t");
		Map.Entry<String, Double> entry = null;
		Iterator<Map.Entry<String, Double>> itr = expressionValue.entrySet().iterator();
		
		while(itr.hasNext()){
			entry = itr.next();
			sb.append(entry.getValue()).append("\t");
		}
		sb.append("\n");
	
		return sb.toString();
	}
	
}

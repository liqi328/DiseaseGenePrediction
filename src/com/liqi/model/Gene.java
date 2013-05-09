package com.liqi.model;
import java.util.HashSet;
import java.util.Set;



public class Gene {
	private String entrezId;
	private String hprdId;
	private String omimId;
	private String name;
	private String symbols;
	
	public Set<Disease> deseaseSet = new HashSet<Disease>();
	
	public void addDisease(Disease d){
		deseaseSet.add(d);
	}
	
	public Set<Disease> getDiseaseSet(){
		return this.deseaseSet;
	}
	
	
	public String getEntrezId() {
		return entrezId;
	}
	public void setEntrezId(String entrezId) {
		this.entrezId = entrezId;
	}
	public String getHprdId() {
		return hprdId;
	}
	public void setHprdId(String hprdId) {
		this.hprdId = hprdId;
	}
	public String getOmimId() {
		return omimId;
	}
	public void setOmimId(String omimId) {
		this.omimId = omimId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbols() {
		return symbols;
	}
	public void setSymbols(String symbols) {
		this.symbols = symbols;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(entrezId).append("\t");
		sb.append(hprdId).append("\t");
		sb.append(omimId).append("\t");
		sb.append(name).append("\t");
		sb.append(symbols).append("\n");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entrezId == null) ? 0 : entrezId.hashCode());
		result = prime * result + ((hprdId == null) ? 0 : hprdId.hashCode());
		result = prime * result + ((omimId == null) ? 0 : omimId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gene other = (Gene) obj;
		if (entrezId == null) {
			if (other.entrezId != null)
				return false;
		} else if (!entrezId.equals(other.entrezId))
			return false;
		if (hprdId == null) {
			if (other.hprdId != null)
				return false;
		} else if (!hprdId.equals(other.hprdId))
			return false;
		if (omimId == null) {
			if (other.omimId != null)
				return false;
		} else if (!omimId.equals(other.omimId))
			return false;
		return true;
	}

	
}

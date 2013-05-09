package com.liqi.model;

import java.util.HashSet;
import java.util.Set;


public class Disease {
	private String name;
	private String omimId;
	
	private Set<Gene> diseaseGene = new HashSet<Gene>();
	
	public void addGene(Gene g){
		this.diseaseGene.add(g);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOmimId() {
		return omimId;
	}

	public void setOmimId(String omimId) {
		this.omimId = omimId;
	}

	public Set<Gene> getDiseaseGene() {
		return diseaseGene;
	}
	
	@Override
	public String toString(){
		return name + "\t" + omimId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Disease other = (Disease) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (omimId == null) {
			if (other.omimId != null)
				return false;
		} else if (!omimId.equals(other.omimId))
			return false;
		return true;
	}
}

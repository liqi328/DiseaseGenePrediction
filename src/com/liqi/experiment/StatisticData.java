package com.liqi.experiment;

import com.liqi.model.Gene;

class StatisticData{
	private Gene gene;
	private int degree;	//邻居数目
	private int neighborDiseaseGeneCount;//邻居节点中为致病基因的数目
	
	public StatisticData(Gene gene){
		this.gene = gene;
	}

	public Gene getGene() {
		return gene;
	}

	public void setGene(Gene gene) {
		this.gene = gene;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getNeighborDiseaseGeneCount() {
		return neighborDiseaseGeneCount;
	}

	public void setNeighborDiseaseGeneCount(int neighborDiseaseGeneCount) {
		this.neighborDiseaseGeneCount = neighborDiseaseGeneCount;
	}
}


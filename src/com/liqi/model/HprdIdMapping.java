package com.liqi.model;


/*
 * HPRD 数据库中的HPRD_ID_MAPPINGS.txt
 * */
public class HprdIdMapping {
	private String hrpdId;
	private String omimId;
	private String entrezGeneId;
	private String geneSymbol;
	private String swissprotId;
	private String nucleotideAccession;
	private String proteinAccession;
	private String mainName;
	
	public String getHrpdId() {
		return hrpdId;
	}
	public void setHrpdId(String hrpdId) {
		this.hrpdId = hrpdId;
	}
	public String getOmimId() {
		return omimId;
	}
	public void setOmimId(String omimId) {
		this.omimId = omimId;
	}
	public String getEntrezGeneId() {
		return entrezGeneId;
	}
	public void setEntrezGeneId(String entrezGeneId) {
		this.entrezGeneId = entrezGeneId;
	}
	public String getGeneSymbol() {
		return geneSymbol;
	}
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
	public String getSwissprotId() {
		return swissprotId;
	}
	public void setSwissprotId(String swissprotId) {
		this.swissprotId = swissprotId;
	}
	public String getNucleotideAccession() {
		return nucleotideAccession;
	}
	public void setNucleotideAccession(String nucleotideAccession) {
		this.nucleotideAccession = nucleotideAccession;
	}
	public String getProteinAccession() {
		return proteinAccession;
	}
	public void setProteinAccession(String proteinAccession) {
		this.proteinAccession = proteinAccession;
	}
	public String getMainName() {
		return mainName;
	}
	public void setMainName(String mainName) {
		this.mainName = mainName;
	}
	
}

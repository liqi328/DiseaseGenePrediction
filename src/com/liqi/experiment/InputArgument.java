
package com.liqi.experiment;

class InputArgument{
	private String ppiFilename;
	private String geneDiseaseAssociationFilename;
	private String idMappingFilename;
	private String tissueSpecificGeneExpressionFilename;
	private String outputDir;
	
	public String getPpiFilename() {
		return ppiFilename;
	}
	
	public void setPpiFilename(String ppiFilename) {
		this.ppiFilename = ppiFilename;
	}
	
	public String getGeneDiseaseAssociationFilename() {
		return geneDiseaseAssociationFilename;
	}
	
	public void setGeneDiseaseAssociationFilename(
			String geneDiseaseAssociationFilename) {
		this.geneDiseaseAssociationFilename = geneDiseaseAssociationFilename;
	}
	
	public String getIdMappingFilename() {
		return idMappingFilename;
	}
	
	public void setIdMappingFilename(String idMappingFilename) {
		this.idMappingFilename = idMappingFilename;
	}
	
	public String getTissueSpecificGeneExpressionFilename() {
		return tissueSpecificGeneExpressionFilename;
	}

	public void setTissueSpecificGeneExpressionFilename(
			String tissueSpecificGeneExpressionFilename) {
		this.tissueSpecificGeneExpressionFilename = tissueSpecificGeneExpressionFilename;
	}

	public String getOutputDir() {
		return outputDir;
	}
	
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
}
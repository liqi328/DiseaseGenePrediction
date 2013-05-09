package com.liqi.model;

public class Tissue {
	private String tissueName;
	private String firstSampleId;
	private String secondSampleId;
	
	public String getTissueName() {
		return tissueName;
	}
	
	public void setTissueName(String name) {
		this.tissueName = name;
	}
	
	public String getFirstSampleId() {
		return firstSampleId;
	}
	public void setFirstSampleId(String firstSampleId) {
		this.firstSampleId = firstSampleId;
	}
	public String getSecondSampleId() {
		return secondSampleId;
	}
	public void setSecondSampleId(String secondSampleId) {
		this.secondSampleId = secondSampleId;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(tissueName).append("\t");
		sb.append(firstSampleId).append("\t");
		sb.append(secondSampleId).append("\n");
		
		return sb.toString();
	}
}

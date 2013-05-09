package com.liqi.reader;

public abstract class ModelReader {
	protected String filename;
	
	public ModelReader(String filename){
		this.filename = filename;
	}
	
	public abstract void read();
}

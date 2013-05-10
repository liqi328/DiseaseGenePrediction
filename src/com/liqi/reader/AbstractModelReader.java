package com.liqi.reader;

public abstract class AbstractModelReader {
	protected String filename;
	
	public AbstractModelReader(String filename){
		this.filename = filename;
	}
	
	public abstract void read();
}

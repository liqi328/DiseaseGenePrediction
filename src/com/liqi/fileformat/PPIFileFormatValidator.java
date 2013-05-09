package com.liqi.fileformat;

import java.io.File;

public class PPIFileFormatValidator implements FileFormatValidator{
	
	private FileFormat fileFormat;
	
	public PPIFileFormatValidator(FileFormat ff){
		this.fileFormat = ff;
	}
	
	@Override
	public boolean validate(File file){
		return false;	
	}

	@Override
	public boolean validate(String filename) {
		return this.validate(new File(filename));
	}
}

package com.liqi.fileformat;

import java.io.File;

public interface FileFormatValidator {
	public boolean validate(String filename);
	
	public boolean validate(File file);
}

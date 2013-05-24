package com.liqi.util;

import java.io.File;
import java.io.FileFilter;

/* 文件工具类
 * 
 * */
public class FileUtil {

	/* 获得指定目录下的所有文件
	 * @param dirName  目录路径
	 * */
	public static File[] getFileList(String dirName){
		File file = new File(dirName);
		if(!file.exists() || !file.isDirectory()){
			System.out.println("The given directory is not exist, or not a directory.");
			return null;
		}
		File[] files = file.listFiles(
				new FileFilter(){
					public boolean accept(File file){
						if(file.isDirectory()){
							return false;
						}
						return true;
					}
				});
		return files;
	}
	
	/* 获得指定目录下的所有子目录
	 * @param dirName  目录路径
	 * */
	public static File[] getDirectoryList(String dirName){
		File file = new File(dirName);
		if(!file.exists() || !file.isDirectory()){
			System.out.println("The given directory is not exist, or not a directory.");
			return null;
		}
		File[] files = file.listFiles(
				new FileFilter() {					
					@Override
					public boolean accept(File pathname) {
						// TODO Auto-generated method stub
						if(pathname.isDirectory()){
							return true;
						}
						return false;
					}
				});
		return files;
	}
	
	public static void makeDir(File dir){
		if(!dir.exists()){
			dir.mkdir();
		}
	}
	
	
	public static void main(String[] args){
		String dirName = "E:/2013疾病研究/实验数据/神经退行性疾病/result";
		File[] dirs = FileUtil.getDirectoryList(dirName);
		for(File dir: dirs){
			System.out.println(dir);
		}
		System.out.println("---------------------------");
		File[] files = FileUtil.getFileList(dirName);
		for(File file: files){
			System.out.println(file);
		}
		
	}
}

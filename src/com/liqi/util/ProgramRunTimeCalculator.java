package com.liqi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProgramRunTimeCalculator {
	private boolean isRunning;
	private long startTime;
	private long elapsedTime;
	
	private String taskName;
	
	public ProgramRunTimeCalculator(){
		this("Default");
	}
	
	public ProgramRunTimeCalculator(String taskName){
		setTaskName(taskName);
		reset();
	}
	
	public void setTaskName(String name){
		this.taskName = name;
	}
	
	public String getTaskName(){
		return this.taskName;
	}
	
	public void reset(){
		this.isRunning = false;
		this.elapsedTime = 0;
	}
	
	public void start(){
		if(this.isRunning)return;
		this.isRunning = true;
		startTime = System.currentTimeMillis();
	}
	
	public void stop(){
		if(!this.isRunning)return;
		this.isRunning = false;
		this.elapsedTime = System.currentTimeMillis() - this.startTime;
	}
	
	public long getElapsedTime(){
		if(isRunning){
			return System.currentTimeMillis() - this.startTime;
		}
		return this.elapsedTime;
	}
	
	public String report(){
		StringBuffer sb = new StringBuffer();
		sb.append("-------------- ").append(this.getTaskName()).append(" --------------\n");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startTime);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb.append("Start  time: ").append(format.format(calendar.getTime())).append("\n");
		
		long t = this.getElapsedTime();
		calendar.setTimeInMillis(startTime + t);  
		sb.append("Finish time: ").append(format.format(calendar.getTime())).append("\n");
		
		sb.append("Elapased time: ").append(DateTimeUtil.format(t)).append("\n");
		
		return sb.toString();
	}

}

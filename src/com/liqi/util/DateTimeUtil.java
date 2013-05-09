package com.liqi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {
	/* 将毫秒数换算成x天x时x分x秒x毫秒 */
	public static String format(long ms){
		int secUnit = 1000;
		int minUnit = 60 * secUnit;
		int hourUnit = 60 * minUnit;
		int dayUnit = 24 * hourUnit;
		
		long day = ms / dayUnit;
		long hour = (ms - day * dayUnit) / hourUnit;
		long min = (ms - day * dayUnit - hour * hourUnit) / minUnit;
		long sec = (ms - day * dayUnit - hour * hourUnit - min * minUnit) / secUnit;
		long msec = ms - day * dayUnit - hour * hourUnit - min * minUnit - sec * secUnit;
		
		StringBuffer sb = new StringBuffer();
		sb.append(day < 10 ? "0" : "").append(day).append("day ");
		sb.append(hour < 10? "0" : "").append(hour).append("hour ");
		sb.append(min < 10 ? "0" : "").append(min).append("min ");
		sb.append(sec < 10 ? "0" : "").append(sec).append("sec ");
		String msecond = sec < 10 ? "0" : "";
		msecond += sec < 100 ? "0" : "";
		msecond += msec;
		sb.append(msecond).append("ms");
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return days + " days " + hours + " hours " + minutes + " minutes "
				+ seconds + " seconds ";
	}
	/**
	 * 
	 * @param begin 时间段的开始
	 * @param end	时间段的结束
	 * @return	输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
	 * @author fy.zhang
	 */
	public static String formatDuring(Date begin, Date end) {
		return formatDuring(end.getTime() - begin.getTime());
	}

	
	 private static String calculatTime(long milliSecondTime) {
		  
		  long hour = milliSecondTime /(60*60*1000);
		  long minute = (milliSecondTime - hour*60*60*1000)/(60*1000);
		  long seconds = (milliSecondTime - hour*60*60*1000 - minute*60*1000)/1000;
		  
		  if(seconds >= 60 )
		  {
		   seconds = seconds % 60;
		      minute+=seconds/60;
		  }
		  if(minute >= 60)
		  {
		    minute = minute % 60;
		    hour  += minute/60;
		  }
		  
		  String sh = "";
		  String sm ="";
		  String ss = "";
		  if(hour <10) {
		     sh = "0" + String.valueOf(hour);
		  }else {
		     sh = String.valueOf(hour);
		  }
		  if(minute <10) {
		     sm = "0" + String.valueOf(minute);
		  }else {
		     sm = String.valueOf(minute);
		  }
		  if(seconds <10) {
		     ss = "0" + String.valueOf(seconds);
		  }else {
		     ss = String.valueOf(seconds);
		  }
		  
		  return sh +":"+sm+":"+ ss;
	 }
	 
	 /**
	 * 毫秒转日期字符串 65秒转换成 00:01:05
	 * 
	 * @param str
	 * @return
	 */
	 public static String secondsToStr(long time) {
	 String result = "";
	 int seconds = new Double(time/1000).intValue();
	 //System.out.println("毫秒数:-->"+time+" 秒-->"+seconds);
	 int hour = 0;
	 int minute = 0;
	 int second = 0;
	 if (seconds > 60) {
	 if (seconds > 3600) {
	 hour = seconds / 3600;
	 minute = seconds % 3600 / 60;
	 second = seconds % 3600 % 60 % 60;
	 } else {
	 second = seconds % 60;
	 minute = seconds / 60;
	 }
	 } else {
	 second = seconds;
	 }
	 result = parseStr(hour) + ":" + parseStr(minute) + ":"
	 + parseStr(second);
	 return result;
	 }

	 private static String parseStr(int time) {
	 if (time >= 10) {
	 return time + "";
	 }
	 return "0" + time;
	 }

	
	private static void format2(){
		long  ms = 3 * 60 * 60 * 1000 + 3 * 60 * 1000 + 32 * 1000; ;//毫秒数     
		//初始化Formatter的转换格式。     
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		System.out.println(formatter.format(ms));
	}
	
	
	public static void main(String[] args){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time1 = "00:03:30";
		long ms = 3 * 60 * 60 * 1000 + 3 * 60 * 1000 + 32 * 1000;
		try {
			calendar.setTime(sdf.parse(time1));
			System.out.println(DateTimeUtil.format(calendar.getTimeInMillis()));
			System.out.println(DateTimeUtil.format(ms));
			System.out.println(DateTimeUtil.calculatTime(ms));
			format2();
			System.out.println(DateTimeUtil.secondsToStr(ms));
			System.out.println(DateTimeUtil.formatDuring(ms));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

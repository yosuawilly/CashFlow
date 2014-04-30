package com.cash.flow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.annotation.SuppressLint;

public class MyCalendar{
	
	@SuppressLint("SimpleDateFormat") 
	public static String toString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT);
		if (date==null) {
			return "";
		}
		String dateS = sdf.format(date);
		return dateS;
	}
	
	@SuppressLint("SimpleDateFormat") 
	public static String toString(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (date==null) {
			return "";
		}
		String dateS = sdf.format(date);
		return dateS;
	}
	
	public static Date toDate(String date){
		if (date==null) {
			return new Date();
		}
		if (date=="") {
			return new Date();
		}
		Date dateD = null;
		try {
			dateD = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT, Locale.ENGLISH).parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateD;
	}
	
	public static Date toDate(String date, String format){
		if (date==null) {
			return new Date();
		}
		if (date=="") {
			return new Date();
		}
		Date dateD = null;
		try {
			dateD = new SimpleDateFormat(format, Locale.ENGLISH).parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateD;
	}
	
	private final String[] monthName = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
	private final String[] dayName = {"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat","Sabtu"};
	
	
	public String getCurrentDate(){
		
		return String.valueOf(Calendar.getInstance().get(Calendar.DATE));
	}
	
	public String getYear(){
		
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}
	public static String getLogYear(){
		
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}
	public String getMonth(){
		
		return String.valueOf(monthName[Calendar.getInstance().get(Calendar.MONTH)]);
	}
	public static String getLogMonth(){
		Calendar calendar = Calendar.getInstance();
		String[] myMonth = {"Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des"};
		return String.valueOf(myMonth[calendar.get(Calendar.MONTH)]);
	}
	
	public String getNameOfDay(){
		
		return String.valueOf(dayName[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1]);
	}
	
	public  String getDay(){
		
		return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}
	
	public  String getDate(){
		
		return String.valueOf(Calendar.getInstance().get(Calendar.DATE));
	}
	
	public  String getHour(){
		
		return String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
	}
	
	public  String getMinute(){
		int minute = Calendar.getInstance().get(Calendar.MINUTE);
		String minuteString ; 
		if (minute<10) {
			minuteString = "0"+String.valueOf(minute);
		}else{
			minuteString = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
		}
		return minuteString;
	}
	public  String getSecond(){
		int second = Calendar.getInstance().get(Calendar.SECOND);
		String secondString ; 
		if (second<10) {
			secondString = "0"+String.valueOf(second);
		}else{
			secondString = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
		}
		return secondString;
	}
	public  String getTime(){
		
		return "";
	}
	
	public static Date getPlusDate(Date date, int plus) {
		Calendar calendar = Calendar.getInstance(new Locale("in", "ID"));
		calendar.setTime(date);
		calendar.add(Calendar.DATE, plus);
		return calendar.getTime();
	}
	
	public static String parseLocaleDate(Date date, String format){
		Locale locale = new Locale("in", "ID");
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);	
		String parseLocaleDate = sdf.format(date);
		return parseLocaleDate;
	}
	
	public static String parseLocaleDate(int year, int month, int day, String format){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		Locale locale = new Locale("in", "ID");
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);	
		String parseLocaleDate = sdf.format(cal.getTime());
		return parseLocaleDate;
	}
	
}

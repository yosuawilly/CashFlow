package com.cash.flow.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import android.os.Environment;
import android.util.Log;

public class LogMe{

	private static File externalStorageDir = null;
	private static File myFile = null ;
	private static FileWriter fileWriter = null;
	private static StringBuffer sb = null ;
	private static BufferedWriter out = null ;
	
	private static final String logFolderName = "/CashFlow/log";
	
	public static void activity(String message){
		Log.i("Message Activity", message);
		sb = new StringBuffer();
		try {
			externalStorageDir = new File(Environment.getExternalStorageDirectory()+logFolderName) ;
			if (!externalStorageDir.exists()) {
				externalStorageDir.mkdir();
			}
			myFile = new File(externalStorageDir , "logMe"+MyCalendar.getLogYear()+" - "+MyCalendar.getLogMonth()+".txt");
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			fileWriter = new FileWriter(myFile, true);
			out = new BufferedWriter(fileWriter);
			sb.append(MyCalendar.toString(new Date())+" => "+message);
			Log.i("activity", MyCalendar.toString(new Date())+" => "+message);
			out.write(sb.toString()+"\n");
			out.close();
			fileWriter.close();
		} catch (Exception ex) {
//			write(ex);
		}
	}
	
	public static void write(Throwable e){
		e.printStackTrace();
		
		sb = new StringBuffer();
		try {
			externalStorageDir = new File(Environment.getExternalStorageDirectory()+logFolderName) ;
			if (!externalStorageDir.exists()) {
				externalStorageDir.mkdir();
			}
			myFile = new File(externalStorageDir , "logMe"+MyCalendar.getLogYear()+" - "+MyCalendar.getLogMonth()+".txt");
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			fileWriter = new FileWriter(myFile, true);
			out = new BufferedWriter(fileWriter);
			if (e.getStackTrace().length>0) {
				Log.i("","Error ==== >"+e.getMessage());
				sb.append(MyCalendar.toString(new Date())+" : "+e.getMessage());
				for (int i = 0; i < e.getStackTrace().length; i++) {
					sb.append("\n");
					sb.append("          ");
					sb.append("Class   - ").append(e.getStackTrace()[i].getClassName().toString());
					sb.append(" => Methode - ").append(e.getStackTrace()[i].getMethodName());
					sb.append(" => Line    - ").append(e.getStackTrace()[i].getLineNumber());
					Log.i("","Error ==== "+String.valueOf(i)+"- Class : "+e.getStackTrace()[i].getClassName().toString()+
							" => Methode : "+e.getStackTrace()[i].getMethodName()+" => Line : "+e.getStackTrace()[i].getLineNumber());
				}
			}
			out.write(sb.toString()+"\n");
			out.close();
			fileWriter.close();
		} catch (Exception ex) {
//			write(ex);
		}
	}
	
	public static void write(Exception e){
		e.printStackTrace();
		sb = new StringBuffer();
		try {
			externalStorageDir = new File(Environment.getExternalStorageDirectory()+logFolderName) ;
			if (!externalStorageDir.exists()) {
				externalStorageDir.mkdir();
			}
			myFile = new File(externalStorageDir , "logMe"+MyCalendar.getLogYear()+" - "+MyCalendar.getLogMonth()+".txt");
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			fileWriter = new FileWriter(myFile, true);
			out = new BufferedWriter(fileWriter);
			if (e.getStackTrace().length>0) {
				Log.i("","Error ==== >"+e.getMessage());
				sb.append(MyCalendar.toString(new Date())+" : "+e.getMessage());
				for (int i = 0; i < e.getStackTrace().length; i++) {
					sb.append("\n");
					sb.append("          ");
					sb.append("Class   - ").append(e.getStackTrace()[i].getClassName().toString());
					sb.append(" => Methode - ").append(e.getStackTrace()[i].getMethodName());
					sb.append(" => Line    - ").append(e.getStackTrace()[i].getLineNumber());
					Log.i("","Error ==== "+String.valueOf(i)+"- Class : "+e.getStackTrace()[i].getClassName().toString()+
							" => Methode : "+e.getStackTrace()[i].getMethodName()+" => Line : "+e.getStackTrace()[i].getLineNumber());
				}
			}
			out.write(sb.toString()+"\n");
			out.close();
			fileWriter.close();
		} catch (Exception ex) {
//			write(ex);
		}
	}
	public static void write(OutOfMemoryError e){
		
		sb = new StringBuffer();
		try {
			externalStorageDir = new File(Environment.getExternalStorageDirectory()+logFolderName) ;
			if (!externalStorageDir.exists()) {
				externalStorageDir.mkdir();
			}
			myFile = new File(externalStorageDir , "logMe"+MyCalendar.getLogYear()+" - "+MyCalendar.getLogMonth()+".txt");
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			fileWriter = new FileWriter(myFile, true);
			out = new BufferedWriter(fileWriter);
			if (e.getStackTrace().length>0) {
				Log.i("","Error ==== >"+e.getMessage());
				sb.append(MyCalendar.toString(new Date())+" : "+e.getMessage());
				for (int i = 0; i < e.getStackTrace().length; i++) {
					sb.append("\n");
					sb.append("          ");
					sb.append("Class   - ").append(e.getStackTrace()[i].getClassName().toString());
					sb.append(" => Methode - ").append(e.getStackTrace()[i].getMethodName());
					sb.append(" => Line    - ").append(e.getStackTrace()[i].getLineNumber());
					Log.i("","Error ==== "+String.valueOf(i)+"- Class : "+e.getStackTrace()[i].getClassName().toString()+
							" => Methode : "+e.getStackTrace()[i].getMethodName()+" => Line : "+e.getStackTrace()[i].getLineNumber());
				}
			}
			out.write(sb.toString()+"\n");
			out.close();
			fileWriter.close();
		} catch (Exception ex) {
//			write(ex);
		}
	}
}

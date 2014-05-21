package com.cash.flow.util;

import android.annotation.SuppressLint;

@SuppressLint("SdCardPath") 
public final class Constant {
	
	//public static final String BASE_URL = "http://192.168.16.90/TrackingPelabuhanServer/rest/";
	
	public static final int DB_VERSION = 1;
	public static final String DB_USER_NAME="CashFlowUserDB";
	public static final String DB_NAME="CashFlowDB";
	public static final String DB_PATH="/data/data/com.cash.flow/databases/";
	
	public static final String LOGIN_STATUS = "LOGIN_STATUS";
	public static final String DEFAULT_PREFERENCE = "CASHFLOW_PREFERENCE";
	
	public static final String SIMPLE_DATE_FORMAT = "E MMM dd ss:mm:HH z yyyy";
	public static final String FORMAT_DATE_DDMMYYYY = "dd/MM/yyyy";
	public static final String FORMAT_DATE_DDMMMMYYYY = "dd MMMM yyyy";
	public static final String FORMAT_DATE_DDMMYYYY_HMS = "dd/MM/yyyy HH:mm:ss";
	
	/*Constant for restfull http request*/
	public static final int REST_GET = 0;
	public static final int REST_PUT = 1;
	public static final int REST_POST = 2;
	public static final int REST_DELETE = 3;
	
	public static final String URL = "url";
	public static final String REST_METHOD = "rest_method";
	public static final String REST_RESULT = "rest_result";
	public static final String REST_CONN_TIMEOUT = "conn_timeout";
	
	public static final int START_ACTIVITY = 0;
	public static final int REDIRECT_TO_HOME = 1;
	public static final int REDIRECT_TO_TRANSACTION = 2;
	public static final int REDIRECT_TO_SETTING = 3;
	public static final int REDIRECT_TO_LOGIN = 4;
	
	/* Alarm Constant*/
	public static final String RECORD_ID = "RECORD_ID";
	public static final String IS_SNOOZE = "isSnooze";
	
	public static final int NO_REPEAT = 0;
	public static final int DAILY = 1;
	public static final int WEEKLY = 2;
	public static final int MONTHLY = 3;
	public static final int FIVE_MINUTE = 4;
	public static final int TEN_MINUTE = 5;
	public static final int FIVTEEN_MINUTE = 6;
	public static final int ONE_HOUR = 7;
	
	public static final int LOW_BALANCE_ALARM = 10;
	public static final int NOTIF_LOW_BALANCE = 123;

}

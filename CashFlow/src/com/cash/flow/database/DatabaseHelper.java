package com.cash.flow.database;

import java.sql.SQLException;

import com.cash.flow.database.dao.CashFlowDao;
import com.cash.flow.database.dao.UserDao;
import com.cash.flow.util.Constant;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private int DB_TYPE;
	
	public static enum DATABASE_TYPE{
		USER_DB,
		CASHFLOW_DB
	}

	public DatabaseHelper(Context context, DATABASE_TYPE type) {
		super(context, type.equals(DATABASE_TYPE.USER_DB) ? Constant.DB_USER_NAME : Constant.DB_NAME, null, 1);
		this.DB_TYPE = type.ordinal();
	}
	
	private void createAllTable(SQLiteDatabase database) {
		ConnectionSource connectionSource = new AndroidConnectionSource(database);
		
		if(DB_TYPE == DATABASE_TYPE.USER_DB.ordinal()) {
			UserDao.createTable(connectionSource);
		} else {
			CashFlowDao.createTable(connectionSource);
		}
		
		try {
			connectionSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteAllTable(SQLiteDatabase database) {
		ConnectionSource connectionSource = new AndroidConnectionSource(database);
		
		if(DB_TYPE == DATABASE_TYPE.USER_DB.ordinal()) {
			UserDao.deleteTable(connectionSource);
		} else {
			CashFlowDao.deleteTable(connectionSource);
		}
		
		try {
			connectionSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createAllTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		deleteAllTable(db);
		createAllTable(db);
	}

}

package com.cash.flow.database;

import com.cash.flow.database.dao.UserDao;
import com.cash.flow.util.Constant;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		super(context, Constant.DB_NAME, null, 1);
	}
	
	private void createAllTable(SQLiteDatabase database) {
		ConnectionSource connectionSource = new AndroidConnectionSource(database);
		UserDao.createTable(connectionSource);
	}
	
	private void deleteAllTable(SQLiteDatabase database) {
		ConnectionSource connectionSource = new AndroidConnectionSource(database);
		UserDao.deleteTable(connectionSource);
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

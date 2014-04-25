package com.cash.flow.database.dao;

import java.sql.SQLException;
import android.content.Context;
import android.util.Log;

import com.cash.flow.database.DatabaseHelper;
import com.cash.flow.model.CashFlow;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class CashFlowDao extends BaseDao<CashFlow, Integer>{
	
	private static final String TAG = CashFlowDao.class.getSimpleName();
	
	public CashFlowDao(Context context) throws SQLException {
		super(new AndroidConnectionSource(new DatabaseHelper(context)), CashFlow.class);
	}

	public CashFlowDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, CashFlow.class);
	}
	
	public static CashFlowDao getInstance(Context context) {
		try {
			return new CashFlowDao(context);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean createTable(ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, CashFlow.class);
			Log.i(TAG, "create Table CashFlow");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean deleteTable(ConnectionSource connectionSource) {
		try {
			TableUtils.clearTable(connectionSource, CashFlow.class);
			Log.i(TAG, "delete Table CashFlow");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
package com.cash.flow.database.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.cash.flow.database.DatabaseHelper;
import com.cash.flow.model.User;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class UserDao extends BaseDaoImpl<User, String>{
	
	private static final String TAG = UserDao.class.getSimpleName();
	
	public UserDao(Context context) throws SQLException {
		super(new AndroidConnectionSource(new DatabaseHelper(context)), User.class);
	}

	public UserDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, User.class);
	}
	
	public void closeConnection() {
		try {
			connectionSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static UserDao getInstance(Context context) {
		try {
			return new UserDao(context);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean createTable(ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, User.class);
			Log.i(TAG, "create Table User");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean deleteTable(ConnectionSource connectionSource) {
		try {
			TableUtils.clearTable(connectionSource, User.class);
			Log.i(TAG, "delete Table User");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<User> findAll() {
		try {
			return queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User findUser(String username) {
		try {
			return queryForId(username);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean createUser(User user) {
		try {
			return create(user) > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateUser(User user) {
		try {
			return update(user) > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}

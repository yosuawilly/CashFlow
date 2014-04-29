package com.cash.flow.database.dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class BaseDao<T, ID> extends BaseDaoImpl<T, ID>{

	public BaseDao(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}
	
	public void closeConnection() {
		try {
			connectionSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<T> findAll() {
		try {
			return queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean createData(T data) {
		try {
			return create(data) > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateData(T data) {
		try {
			return update(data) > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteData(T data) {
		try {
			return delete(data) > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}

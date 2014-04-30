package com.cash.flow.model;

import java.util.Date;

import com.cash.flow.database.dao.CashFlowDao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_cash_flow", daoClass=CashFlowDao.class)
public class CashFlow {
	
	//@DatabaseField(generatedId=true)
	//private int id;
	
	@DatabaseField(dataType=DataType.LONG)
	private long nominal;
	
	@DatabaseField(dataType=DataType.STRING)
	private String description;
	
	@DatabaseField(dataType=DataType.DATE_LONG) //for compare with query between
	private Date timestamp;
	
	@DatabaseField(dataType=DataType.ENUM_STRING)
	private CashType typeCash;
	
	@DatabaseField(dataType=DataType.LONG)
	private long balance;
	
	public static enum CashType {
		CASH_IN ("Cash In"),
		CASH_OUT ("Cash Out");
		
		private final String name;
		
		private CashType(String name) {
			this.name = name;
		}
		
		public boolean equalsName(String otherName){
	        return (otherName == null) ? false : name.equals(otherName);
	    }
		
		@Override
		public String toString() {
			return name;
		}
		
	}
	
	public CashFlow() {
		this.timestamp = new Date();
	}
	
//	public int getId() {
//		return id;
//	}
//	
//	public void setId(int id) {
//		this.id = id;
//	}

	public long getNominal() {
		return nominal;
	}

	public void setNominal(long nominal) {
		this.nominal = nominal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public CashType getTypeCash() {
		return typeCash;
	}

	public void setTypeCash(CashType typeCash) {
		this.typeCash = typeCash;
	}
	
	public long getBalance() {
		return balance;
	}
	
	public void setBalance(long balance) {
		this.balance = balance;
	}

}

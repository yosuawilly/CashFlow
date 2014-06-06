package com.cash.flow.model;

import java.io.Serializable;

import com.cash.flow.database.dao.UserDao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_user", daoClass=UserDao.class)
public class User implements Serializable{
	
	private static final long serialVersionUID = -5667885497702615226L;

	@DatabaseField(id=true, canBeNull=false, dataType=DataType.STRING)
	private String username;
	
	@DatabaseField(canBeNull=false, dataType=DataType.STRING)
	private String password;
	
	@DatabaseField(dataType=DataType.STRING)
	private String sequrityQuestion;
	
	@DatabaseField(dataType=DataType.STRING)
	private String answer;
	
	@DatabaseField(dataType=DataType.LONG)
	private long balance;
	
	@DatabaseField(dataType=DataType.LONG)
	private long margin;
	
	@DatabaseField(dataType=DataType.ENUM_STRING)
	private Currency currency;
	
	public User() {
		this.balance = 0L;
		this.margin = 0L;
		this.currency = Currency.IDR;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSequrityQuestion() {
		return sequrityQuestion;
	}

	public void setSequrityQuestion(String sequrityQuestion) {
		this.sequrityQuestion = sequrityQuestion;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public long getBalance() {
		return balance;
	}
	
	public void setBalance(long balance) {
		this.balance = balance;
	}
	
	public long getMargin() {
		return margin;
	}
	
	public void setMargin(long margin) {
		this.margin = margin;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}

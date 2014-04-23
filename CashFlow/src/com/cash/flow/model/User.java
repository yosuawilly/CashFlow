package com.cash.flow.model;

import com.cash.flow.database.dao.UserDao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_user", daoClass=UserDao.class)
public class User {
	
	@DatabaseField(id=true, canBeNull=false, dataType=DataType.STRING)
	private String username;
	
	@DatabaseField(canBeNull=false, dataType=DataType.STRING)
	private String password;
	
	@DatabaseField(dataType=DataType.STRING)
	private String sequrityQuestion;
	
	@DatabaseField(dataType=DataType.STRING)
	private String answer;
	
	public User() {
		// TODO Auto-generated constructor stub
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

}

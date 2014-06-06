package com.cash.flow.global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cash.flow.model.User;

public class GlobalVar implements Serializable{
	
	private static final long serialVersionUID = 6668652291040678573L;
	
	private static GlobalVar instance;
	private List<String> classNames = new ArrayList<String>();
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public GlobalVar() {
	}
	
	static {
		instance = new GlobalVar();
	}
	
	public static GlobalVar getInstance() {
		return GlobalVar.instance;
	}
	
	public static void setInstance(GlobalVar instance) {
		GlobalVar.instance = instance;
	}
	
	public List<String> getClassNames() {
		return classNames;
	}
	
	public void setClassNames(List<String> classNames) {
		this.classNames = classNames;
	}
	
	public void clearAllObject(){
		this.user = null;
	}

}

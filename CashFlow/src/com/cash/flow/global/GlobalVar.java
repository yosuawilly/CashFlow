package com.cash.flow.global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GlobalVar implements Serializable{
	
	private static final long serialVersionUID = 6668652291040678573L;
	
	private static GlobalVar instance;
	private List<String> classNames = new ArrayList<String>();
	
	public GlobalVar() {
	}
	
	static {
		instance = new GlobalVar();
	}
	
	public static GlobalVar getInstance() {
		return GlobalVar.instance;
	}
	
	public List<String> getClassNames() {
		return classNames;
	}
	
	public void setClassNames(List<String> classNames) {
		this.classNames = classNames;
	}
	
	public void clearAllObject(){
		//getInstance().setSiswa(null);
	}

}

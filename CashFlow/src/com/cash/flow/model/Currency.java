package com.cash.flow.model;

public enum Currency {
	
	IDR ("Rp"),
	USD ("$"),
	GBP ("£"),
	JPY ("¥"),
	EUR ("€");
	
	private final String name;
	
	private Currency(String name) {
		this.name = name;
	}
	
	public static String[]getCurrency() {
		String[]currency = new String[values().length];
		int index = 0;
		for(Currency curr : values()) {
			currency[index++] = curr.name();
		}
		return currency;
	}
	
	public boolean equalsName(String otherName){
        return (otherName == null) ? false : name.equals(otherName);
    }
	
	@Override
	public String toString() {
		return name;
	}
	
}

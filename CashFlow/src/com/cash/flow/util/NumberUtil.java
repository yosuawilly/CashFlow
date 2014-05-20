package com.cash.flow.util;

import android.annotation.SuppressLint;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import com.cash.flow.model.Currency;

public final class NumberUtil {
	
    private NumberUtil() {
		
	}
	
	public static String kata(int number){
	    number = Math.abs(number);
	 
	    String [] angka = {"","satu","dua","tiga","empat","lima","enam","tujuh","delapan","sembilan","sepuluh","sebelas"};
	    String temp = "";
	 
	    if(number < 12){
	        temp = " "+angka[number];
	    }else if(number < 20){
	        temp = kata(number - 10)+" belas";
	    }else if(number < 100){
	        temp = kata(number/10)+" puluh"+kata(number%10);
	    }else if (number < 200) {
	        temp = " seratus"+kata(number - 100);
	    }else if (number < 1000) {
	        temp = kata(number/100)+ " ratus"+ kata(number % 100);
	    }else if (number < 2000) {
	        temp = " seribu"+ kata(number - 1000);
	    }else if (number < 1000000) {
	        temp = kata(number/1000)+" ribu"+ kata(number % 1000);
	    }else if (number < 1000000000) {
	        temp = kata(number/1000000)+" juta"+ kata(number % 1000000);
	    }
	 
	    return temp;
	}
	
	@SuppressLint("DefaultLocale") 
	public static String terbilang(int number){
		String terbilangTeks  = kata(number).trim();
		return terbilangTeks.substring(0, 1).toUpperCase()+terbilangTeks.substring(1, terbilangTeks.length())+" rupiah.";
	}
	
	public static String toCurr(BigDecimal number){
		NumberFormat formatter = new DecimalFormat("#,##0.00;-#,##0.00");
		
		return formatter.format(number);
	}
	
	public static String toCurr(String number){
		String temp = "";
		for (int i = 0; i < number.length(); i++) {
			if (String.valueOf(number.charAt(i)).equals(".")||String.valueOf(number.charAt(i)).equals(",")||String.valueOf(number.charAt(i)).equals("-")) {
				
			}else{
				temp = temp+number.charAt(i);
			}
		}
		NumberFormat formatter = new DecimalFormat("#,##0;-#,##0");
		
		return formatter.format(new BigDecimal(temp));
	}
	
	public static String toCurr2(String number){
		BigDecimal dec = new BigDecimal(number);
		NumberFormat formatter = new DecimalFormat("#,###.##");
        String nilai = formatter.format(dec);
        nilai = nilai.replace(",", ".");
		return nilai;
	}
	
	public static String normalizeNumber(String number){
		number = number.replaceAll("(\\,+|\\.+)", "");
		return number;
//		String temp = "";
//		for (int i = 0; i < number.length(); i++) {
//			if (String.valueOf(number.charAt(i)).equals(".")||String.valueOf(number.charAt(i)).equals(",")||String.valueOf(number.charAt(i)).equals("-")) {
//				
//			}else{
//				temp = temp+number.charAt(i);
//			}
//		}
//		
//		return temp;
	}
	
	public static String toCurrDigitGrouping(String number, Currency currency){
		BigDecimal dec = new BigDecimal(number);
		NumberFormat formatter = new DecimalFormat("#,###.##");
        String nilai = formatter.format(dec);
        nilai = nilai.replace(",", ".");
		return currency.toString()+" " + nilai + ",00";
	}
	
	public static String toCurrDigitGrouping(String number){
		BigDecimal dec = new BigDecimal(number);
		NumberFormat formatter = new DecimalFormat("#,###.##");
        String nilai = formatter.format(dec);
        nilai = nilai.replace(",", ".");
		return "Rp " + nilai + ",00";
	}
	
	public static String toCurrDigitGrouping(BigDecimal number){
//		BigDecimal dec = new BigDecimal(number);
		DecimalFormatSymbols otherSymbol = new DecimalFormatSymbols();
		otherSymbol.setDecimalSeparator(',');
		otherSymbol.setGroupingSeparator('.');
		NumberFormat formatter = new DecimalFormat("#,###.##", otherSymbol);
        String nilai = formatter.format(number);
//        nilai = nilai.replace(",", ".");
        if("0".equals(nilai)) return "Rp " + nilai + ",00";
        if(!nilai.substring(nilai.length()-3, nilai.length()).startsWith(",")){
        	if(nilai.substring(nilai.length()-2, nilai.length()).startsWith(",")){
        		nilai += "0";
        	} else nilai += ",00";
        }
		return "Rp " + nilai;
	}
	
	
	public static String moneyFormat(String str) {
		String convertedString = str;

		int length = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '.')
				break;
			length++;
		}

		if (length > 0) {
			String temp = "";
			int i = length;
			while (i > 0) {
				if (i > 2) {
					if (temp.equalsIgnoreCase(""))
						temp = str.substring(i - 3, i);
					else
						temp = str.substring(i - 3, i) + "." + temp;
				} else {
					if (temp.equalsIgnoreCase(""))
						temp = str.substring(0, i);
					else
						temp = str.substring(0, i) + "." + temp;
					i = 0;
				}
				i = i - 3;
			}
			String decimal = (str.length() == length ? "00" : str
					.substring(length + 1));
			decimal = (decimal.length() == 1 ? decimal + "0" : decimal);
			convertedString = temp + "," + decimal;
		}

		return convertedString;
	}
	
	public static Boolean numberValid(String mustNumber){
		try {
			Integer.parseInt(mustNumber);
			return true ;
		} catch (Exception e) {
			//LogMe.write(e);
			return false ;
		}
	}
	
}

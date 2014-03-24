package com.cash.flow.util;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public class ResourceUtil {
    private static ResourceBundle RESOURCE_BUNDLE = null ;
	
	private ResourceUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static final ResourceBundle getBundle(){
	    if(RESOURCE_BUNDLE == null){
			ResourceBundle bundle = new ListResourceBundle(){
				protected Object[][] getContents() {					
					String [][] arr  = new String[][] {
							{"LK-0000","Problem komunikasi dengan server"},
							{"LK-0002","Tidak ada data"},
							{"LK-0003","Tidak ada data quiz"},
							{"IB-0600","Problem komunikasi dengan server"}
						};
					return arr;
				}
		    };
		    
		    RESOURCE_BUNDLE = bundle;
		}
	    
		return RESOURCE_BUNDLE;
	}

}

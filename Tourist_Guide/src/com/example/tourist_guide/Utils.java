package com.example.tourist_guide;


import static com.example.tourist_guide.Constants.SERVER_URL;
public class Utils {
	
	public static String createURLforServlet(String servletName){
		
		return SERVER_URL + servletName;
	}

}

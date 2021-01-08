package com.solus.sdk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class SdkUtil {

	 public static String getDate(){
	        DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmss", Locale.US);
	        Date date = new Date();
	        String format = dateFormat.format(date);
	        System.out.println(format);
	        return format;
	    }
	 
	 
	 public static String getUuid() {
		 String string = UUID.randomUUID().toString();
		 System.out.println(string);
		return string;
	 }


}

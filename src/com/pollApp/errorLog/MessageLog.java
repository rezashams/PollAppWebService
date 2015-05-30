package com.pollApp.errorLog;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageLog {
	public static void main(String[] args) {
	}

	public static void log(String messageLog) {
		 try {
	            PrintWriter pw = null;
	            String tomcatPath = System.getProperty("catalina.base");
	            File file = new File(tomcatPath+"/logs/polllogs.txt");
	            FileWriter fw = new FileWriter(file, true);
	            java.util.Date date = new Date();
	            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	            pw = new PrintWriter(fw);
	            if(messageLog!="")
	                pw.println(messageLog +" "+fd.format(date).toString());
	            if (pw != null)
	                pw.close();
	  	        }catch(Exception e){
	        System.out.println("There are fatal Error Log file");
	     e.printStackTrace();   
	    }
		
	}

}

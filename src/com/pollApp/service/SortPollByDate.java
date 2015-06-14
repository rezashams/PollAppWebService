package com.pollApp.service;

import java.util.Comparator;

import org.json.JSONException;
import org.json.JSONObject;

import com.pollApp.model.Poll;

public class SortPollByDate implements Comparator {
	public int compare(Object f, Object s) {
		JSONObject  poll1 = (JSONObject) f;
		JSONObject  poll2 = (JSONObject) s;
		long val1=0;
		long val2=0;
		try {
			val1 =  poll1.getLong("creationDate");
		    val2= poll2.getLong("creationDate");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (val1>=val2) {
		 return 1;	
		}
		return -1;
    }

}

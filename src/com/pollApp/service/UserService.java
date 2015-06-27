package com.pollApp.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pollApp.entityGetJson.User_checkUsers;
import com.pollApp.entityGetJson.User_checkUsers_phones;
import com.pollApp.entityGetJson.User_deactiveUserJson;
import com.pollApp.entityGetJson.User_registerUserJson;
import com.pollApp.entityGetJson.User_updateUserJson;
import com.pollApp.finder.*;
import com.pollApp.model.Groups;

public class UserService {

	public static long sendSms(String phoneNumber ,int code) {
		return 123456;
	}

	public static long registerUser(User_registerUserJson json) {
		long userId=UserFinder.getUser(json.getPhoneNumber());
		if (userId!=-1) {
			UserFinder.updateActiveUser(userId);
		} else {
			userId= UserFinder.addUser(json);
		}
		return userId;
		
	}

	public static List<Groups> getGroupsOfUser(long userId) {
		
		return UserFinder.getGroupsOfUser(userId);
	}

	public static boolean updateUser(User_updateUserJson updateUserJson) {
		
		return UserFinder.updateUser(updateUserJson);
	}

	public static boolean deactiveUser(User_deactiveUserJson deactiveUserJson) {
		// TODO Auto-generated method stub
		return UserFinder.deactiveUser(deactiveUserJson);
	}

	public static JSONArray checkUsers(User_checkUsers json) throws JSONException {
		JSONArray jsonResult = new JSONArray();
		
		for (User_checkUsers_phones phoneNumber:json.getPhones()) {
			long userId=UserFinder.getUser(phoneNumber.getPhone());
			if (userId==-1) {
				userId=0;		
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("phone", phoneNumber.getPhone());
			jsonObject.put("userId",userId);
			jsonResult.put(jsonObject);
		}
		return jsonResult;
	}
	
	}

package com.pollApp.service;

import java.util.List;

import com.pollApp.entityGetJson.User_updateUserJson;
import com.pollApp.finder.*;
import com.pollApp.model.Groups;

public class UserService {

	public static long sendSms(String phoneNumber ,int code) {
		return 123456;
	}

	public static long registerUser(String phoneNumber,long createdDate, String name) {
		long userId=UserFinder.getUser(phoneNumber);
		if (userId!=-1) {
			UserFinder.updateActiveUser(userId,createdDate);
		} else {
			userId= UserFinder.addUser(phoneNumber, createdDate,name);
		}
		return userId;
		
	}

	public static List<Groups> getGroupsOfUser(long userId) {
		
		return UserFinder.getGroupsOfUser(userId);
	}

	public static boolean updateUser(User_updateUserJson updateUserJson) {
		
		return UserFinder.updateUser(updateUserJson);
	}
	
	}

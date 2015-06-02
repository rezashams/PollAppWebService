package com.pollApp.service;

import com.pollApp.finder.*;

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
	
	}

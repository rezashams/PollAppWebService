package com.pollApp.webService;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;  
import javax.ws.rs.Produces;  
import javax.ws.rs.core.MediaType;  
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.pollApp.JsonSms;
import com.pollApp.entityGetJson.User_registerUserJson;
import com.pollApp.entityGetJson.User_sendSmsJson;
import com.pollApp.errorLog.MessageLog;
import com.pollApp.service.UserService;
class MyPojo {
    String phoneNumber;
    int code;
}

@Path("user")  
public class UserWebService {  

	@POST
	@Path("sendSms")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	public Response sendSms(User_sendSmsJson json) {
		JSONObject jsonObject = new JSONObject();
		try {
			Date date= new Date();
			int code=json.getCode();
			String phoneNumber=json.getPhoneNumber();
			long resultOfSms=UserService.sendSms(phoneNumber, code);
			jsonObject.put("code", code);
			jsonObject.put("resultOfSms", resultOfSms);
			jsonObject.put("date", date.getTime());
		} catch (JSONException e) {
			e.printStackTrace();
			MessageLog.log(e.getMessage());
			return Response.status(200).entity("JSON Error").build();
		}

		return Response.status(200).entity(jsonObject.toString()).build();

	}  

	@POST
	@Path("registerUser")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(User_registerUserJson json) { 
		Date date= new Date();
		String phoneNumber=json.getPhoneNumber();
		String name=json.getName();
		long UserId=UserService.registerUser(phoneNumber,date.getTime(),name);
		String status="success";
		if(UserId==0) {
			status="fail";
		}
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("status", status);
			jsonObject.put("userId", UserId);
			jsonObject.put("date", date.getTime());
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}

		return Response.status(200).entity(jsonObject.toString()).build();

	}  
}  

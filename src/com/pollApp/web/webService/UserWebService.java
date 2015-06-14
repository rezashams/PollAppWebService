package com.pollApp.web.webService;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;  
import javax.ws.rs.Produces;  
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;  
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pollApp.JsonSms;
import com.pollApp.entityGetJson.User_registerUserJson;
import com.pollApp.entityGetJson.User_sendSmsJson;
import com.pollApp.entityGetJson.User_updateUserJson;
import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Groups;
import com.pollApp.model.User;
import com.pollApp.service.GroupService;
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
	@Path("updateUser")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User_updateUserJson updateUserJson) { 
		boolean isUpdate=false;
		String status="success";
		if(updateUserJson.getUserId()>0) {
		  isUpdate=UserService.updateUser(updateUserJson);
		}
			
		if(!isUpdate ) {
			status="fail";
		}
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("status", status);
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
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
		if(UserId==-1) {
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
	//localhost:9999/PollAppWebService/user/getGroupsOfUser?userId=17
	@GET
	@Path("getGroupsOfUser")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")  
	public Response getGroupsOfUser(@DefaultValue("-1")@QueryParam("userId") long userId) {

		List <Groups> GroupList=UserService.getGroupsOfUser(userId);
		JSONArray memberArray= new JSONArray();     
		try{
			for (Groups group:GroupList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("groupId",group.getId() );
				memberArray.put(jsonObject);
			}
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}

		return Response.status(200).entity(memberArray.toString()).build();

	}

}  

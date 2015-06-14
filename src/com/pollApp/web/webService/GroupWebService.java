package com.pollApp.web.webService;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
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

import com.pollApp.entityGetJson.Group_addMemberToGroup;
import com.pollApp.entityGetJson.Group_createGroupJson;
import com.pollApp.entityGetJson.Group_deleteGroup;
import com.pollApp.entityGetJson.Group_removeMemberFromGroupJson;
import com.pollApp.entityGetJson.poll.AddPollNonPrizeJson;
import com.pollApp.entityGetJson.poll.DeletePollNonPrizeJson;
import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Poll;
import com.pollApp.model.User;
import com.pollApp.service.GroupService;
import com.pollApp.service.PollService;



@Path("group") 
public class GroupWebService {
	//localhost:9999/PollAppWebService/group/createGroup
	//{"adminId":17,"name":"dalghakha"}
	@POST
	@Path("createGroup")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createGroup(Group_createGroupJson groupJson) { 
		Date date = new Date();
		long groupId=GroupService.createGroup(groupJson);
		String status="success";
		if(groupId==-1) {
			status="fail";
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("groupId", groupId);
			jsonObject.put("status", status);
			jsonObject.put("date", date.getTime());
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}

		return Response.status(200).entity(jsonObject.toString()).build();



	}
     //localhost:9999/PollAppWebService/group/addMemberToGroup
	//{"memberId":17,"groupId":2}
	@POST
	@Path("addMemberToGroup")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMemberToGroup(Group_addMemberToGroup memberJson) { 
		Date date = new Date();
		boolean st=GroupService.addMemberToGroup(memberJson);
		String status="success";
		if(!st) {
			status="fail";
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("status", status);
			jsonObject.put("date", date.getTime());
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}

		return Response.status(200).entity(jsonObject.toString()).build();	
	}
	//localhost:9999/PollAppWebService/group/removeMemberFromGroup
	//{"memberId":18,"groupId":3}

	@POST
	@Path("removeMemberFromGroup")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response removeMemberFromGroup(Group_removeMemberFromGroupJson removememberJson) {  
		Date date = new Date();
		boolean statusremove=GroupService.removeMemberFromGroup(removememberJson);
		String status="success";
		if(!statusremove) {
			status="fail";
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("status", status);
			jsonObject.put("date", date.getTime());
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}

		return Response.status(200).entity(jsonObject.toString()).build();

	}  
	//localhost:9999/PollAppWebService/group/deleteGroup
		//{"userId":18,"groupId":3}
	
	@POST
	@Path("deleteGroup")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response deleteGroup(Group_deleteGroup deleteGroupJson) {  
		Date date = new Date();
		boolean statusremove=GroupService.deleteGroup(deleteGroupJson);
		String status="success";
		if(!statusremove) {
			status="fail";
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("status", status);
			jsonObject.put("date", date.getTime());
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}

		return Response.status(200).entity(jsonObject.toString()).build();

	}  
	//localhost:9999/PollAppWebService/group/getMemberOfGroup

	@GET
	@Path("getMemberOfGroup")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")  
	public Response getMemberOfGroup(@DefaultValue("-1")@QueryParam("groupId") long groupId) {

		List <User> memberList=GroupService.getMemberOfGroup(groupId);
		JSONArray memberArray= new JSONArray();     
		try{
			for (User user:memberList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("userId",user.getId() );
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

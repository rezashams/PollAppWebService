package com.pollApp.webService;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.pollApp.entityGetJson.PollUser_voteJson;
import com.pollApp.errorLog.MessageLog;
import com.pollApp.service.PollUserService;


@Path("pollUser")  
public class PollUserWebService {
	@POST
	@Path("vote")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")  
	@Consumes(MediaType.APPLICATION_JSON)
	public Response voteToPoll(PollUser_voteJson json) { 
		Date date= new Date();
		long user_id=json.getUserId();
		long poll_id=json.getPollId();
		int vote=json.getVote();
		long vote_date= date.getTime();
	           long pollUser_id=PollUserService.voteToPoll(user_id, poll_id, vote_date, vote );
	           String status="success";
	           if(pollUser_id==0) {
	           	status="fail";
	           }
	JSONObject jsonObject = new JSONObject();
	try {
		//jsonObject.put("pollUser_id", pollUser_id);
		jsonObject.put("status", status);
		jsonObject.put("date", vote_date);
	} catch (JSONException e) {
		MessageLog.log(e.toString());
		e.printStackTrace();
		return Response.status(200).entity("JSON Error").build();
	}

	return Response.status(200).entity(jsonObject.toString()).build();

}  


}

package com.pollApp.web.webService;

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

import com.pollApp.entityGetJson.Vote_nonPrizeVoteJson;
import com.pollApp.errorLog.MessageLog;
import com.pollApp.service.VoteService;


@Path("vote")  
public class VoteWebService {
	//localhost:9999/PollAppWebService/vote/nonPrizePoll
	//{"userId":17,"pollId":49,"choice":1}
	@POST
	@Path("nonPrizePoll")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")  
	@Consumes(MediaType.APPLICATION_JSON)
	public Response voteToNonPrizePoll(Vote_nonPrizeVoteJson nonPrizeVoteJson) { 
		Date dateNow= new Date();
		long userId=nonPrizeVoteJson.getUserId();
		long pollId=nonPrizeVoteJson.getPollId();
		int vote=nonPrizeVoteJson.getChoice();
		long date= dateNow.getTime();
	           long voteId=VoteService.voteToNonPrizePoll(userId, pollId, date, vote );
	           String status="success";
	           if(voteId==-1) {
	           	status="fail";
	           }
	JSONObject jsonObject = new JSONObject();
	try {
		jsonObject.put("status", status);
		jsonObject.put("date", date);
	} catch (JSONException e) {
		MessageLog.log(e.toString());
		e.printStackTrace();
		return Response.status(200).entity("JSON Error").build();
	}

	return Response.status(200).entity(jsonObject.toString()).build();

}  


}

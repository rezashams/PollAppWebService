package com.pollApp.webService;

import java.util.ArrayList;
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

import com.pollApp.entityGetJson.poll.AddPollNonPrizeJson;
import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Choice;
import com.pollApp.model.Poll;
import com.pollApp.model.Question;
import com.pollApp.service.PollService;
import com.pollApp.service.UserService;

@Path("poll") 
public class PollWebService {
	//localhost:9999/PollAppWebService/poll/addPollNonPrize
	//{ "title":" is program work correctly?", "type":"public","ownerId":17  , "language": "persian", "country":"iran" , "options": [{"index":1 ,"title":"bale" } ,{"index":2 ,"title":"kheyr" } ]  }
	@POST
	@Path("addPollNonPrize")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPollNonPrize(AddPollNonPrizeJson pollNonPrize) {  //add default for prize
		Date date = new Date();
		long pollId=PollService.addPollNonPrize(pollNonPrize);
		String status="success";
		if(pollId==-1) {
			status="fail";
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("pollId", pollId);
			jsonObject.put("status", status);
			jsonObject.put("date", date.getTime());
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}

		return Response.status(200).entity(jsonObject.toString()).build();

	}  

	@GET
	@Path("getPolls")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")  
	public Response getPolls( @DefaultValue("1")@QueryParam("page") int page, @DefaultValue("20")@QueryParam("numOfEachPage") int numOfEachPage
			,@DefaultValue("public")@QueryParam("type") String type  ,@DefaultValue("false")@QueryParam("prize") boolean prize
             ,@DefaultValue("0")@QueryParam("lastUpdate") long last_update ,@DefaultValue("0")@QueryParam("groupId") long group_id) { 
		List <Poll> pollList =new ArrayList <Poll> ();
		List <Question> questionList =new ArrayList <Question> ();
		List <Choice> choiceList =new ArrayList <Choice> ();
		pollList=PollService.getPolls(page, numOfEachPage,last_update,type,prize,group_id);
		JSONArray pollArray= new JSONArray();     

		try {
			for (Poll poll:pollList) {
				JSONObject jsonPoll = new JSONObject();
				jsonPoll.put("pollId", poll.getId());
				jsonPoll.put("title", poll.getTitle());
				jsonPoll.put("ownerName", poll.getOwner().getName());
				
				/*jsonPoll.put("choices", poll.getChoice());
				jsonPoll.put("dateCreated", poll.getDate_created());
				jsonPoll.put("lastActivityTime ", poll.getLast_activity_time());
				*/
				jsonPoll.put("type", poll.getType());
				jsonPoll.put("prize", poll.getPrize());
				jsonPoll.put("numOfVote", poll.getNumOfVote());
				if(poll.getOwner()!=null) {
					jsonPoll.put("ownerId", poll.getOwner().getId());
				}
				if(poll.getGroup()!=null) {
					jsonPoll.put("groupId", poll.getGroup().getId());
				} 
				pollArray.put(jsonPoll);
			}
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}
		return Response.status(200).entity(pollArray.toString()).build();
	}  

	@GET
	@Path("getPoll")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	public Response getPoll(@DefaultValue("0")@QueryParam("pollId") long poll_id) { 
		Poll poll=PollService.getPoll(poll_id);
		JSONObject jsonPoll = new JSONObject();

		try {
			if (poll!=null) {
				jsonPoll.put("pollId", poll.getId());
				jsonPoll.put("title", poll.getTitle());
				if(poll.getCategory()!=null) {
					jsonPoll.put("categoryId", poll.getCategory().getId());
				}
				jsonPoll.put("ownerName", poll.getOwner().getName());
				/*jsonPoll.put("choices", poll.getChoice());
				jsonPoll.put("statistic", poll.getStatistic());
				jsonPoll.put("dateCreated", poll.getDate_created());
				jsonPoll.put("lastActivityTime ", poll.getLast_activity_time());
				jsonPoll.put("likeNumber", poll.getLike_number());
				jsonPoll.put("dislikeNumber", poll.getDislike_number());*/
				jsonPoll.put("type", poll.getType());
				jsonPoll.put("prize", poll.getPrize());
				jsonPoll.put("numOfVote", poll.getNumOfVote());
				if(poll.getOwner()!=null) {
					jsonPoll.put("ownerId", poll.getOwner().getId());
				}
			
				if(poll.getGroup()!=null) {
					jsonPoll.put("groupId", poll.getGroup().getId());
				} 
			} else if (poll==null) {
					jsonPoll.put("status", "fail");
				}
			
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}
		return Response.status(200).entity(jsonPoll.toString()).build();
	}  
	
	@GET
	@Path("getMyPolls")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	public Response getMyPolls(@DefaultValue("1")@QueryParam("page") int page, @DefaultValue("20")@QueryParam("numOfEachPage") int numOfEachPage,
			@DefaultValue("0")@QueryParam("userId") long user_id) {
		List <Poll> pollList =new ArrayList <Poll> ();
	    pollList=PollService.getMyPolls(page, numOfEachPage,user_id);
		JSONArray pollArray= new JSONArray();     
		try {
			for (Poll poll:pollList) {
				JSONObject jsonPoll = new JSONObject();
				jsonPoll.put("pollId", poll.getId());
				jsonPoll.put("title", poll.getTitle());
				jsonPoll.put("ownerName", poll.getOwner().getName());
				
				if(poll.getCategory()!=null) {
					jsonPoll.put("categoryId", poll.getCategory().getId());
				}
			/*	jsonPoll.put("choices", poll.getChoice());
				jsonPoll.put("statistic", poll.getStatistic());
				jsonPoll.put("dateCreated", poll.getDate_created());
				jsonPoll.put("lastActivityTime ", poll.getLast_activity_time());
				jsonPoll.put("likeNumber", poll.getLike_number());
				jsonPoll.put("dislikeNumber", poll.getDislike_number());*/
				jsonPoll.put("type", poll.getType());
				jsonPoll.put("prize", poll.getPrize());
				jsonPoll.put("numOfVote", poll.getNumOfVote());
				if(poll.getOwner()!=null) {
					jsonPoll.put("ownerId", poll.getOwner().getId());
				}
				if(poll.getGroup()!=null) {
					jsonPoll.put("groupId", poll.getGroup().getId());
				} 
				pollArray.put(jsonPoll);
			}
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}
		return Response.status(200).entity(pollArray.toString()).build();
	}
}

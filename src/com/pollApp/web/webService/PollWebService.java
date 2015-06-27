package com.pollApp.web.webService;

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
import com.pollApp.entityGetJson.poll.DeletePollNonPrizeJson;
import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Choice;
import com.pollApp.model.Poll;
import com.pollApp.model.Question;
import com.pollApp.model.Vote;
import com.pollApp.service.ChoiceService;
import com.pollApp.service.PollService;
import com.pollApp.service.QuestionService;
import com.pollApp.service.UserService;
import com.pollApp.service.VoteService;

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
	//localhost:9999/PollAppWebService/poll/getPublicPolls?page=1&numOfEachPage=3
	@GET
	@Path("getPublicPolls")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")  
	public Response getPublicePolls( @DefaultValue("1")@QueryParam("page") int page, @DefaultValue("20")@QueryParam("numOfEachPage") int numOfEachPage
			 ,@DefaultValue("0")@QueryParam("lastUpdate") long lastUpdate ) { 
		List <Poll> pollList =new ArrayList <Poll> ();
		List <Question> questionList =new ArrayList <Question> ();
		pollList=PollService.getPublicePolls(page, numOfEachPage,lastUpdate);
		JSONArray questionArray= new JSONArray();  
		try {
			for (Poll poll:pollList) {
				questionList=QuestionService.getQuestions(poll.getId());
				for (Question question:questionList) {
					JSONObject jsonQuestion = new JSONObject();
					jsonQuestion.put("pollId", poll.getId());
					jsonQuestion.put("creationDate", poll.getCreationDate());
					jsonQuestion.put("ownerName", poll.getOwner().getName());
					jsonQuestion.put("title", question.getTitle());
					jsonQuestion.put("numOfVote", poll.getNumOfVote());
					jsonQuestion.put("ownerId", poll.getOwner().getId());

					questionArray.put(jsonQuestion);
				}
				
			}
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}
		return Response.status(200).entity(questionArray.toString()).build();
	}  

	//localhost:9999/PollAppWebService/webService/poll/getPoll?pollId=49
	@GET
	@Path("getPoll")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	public Response getPoll(@DefaultValue("0")@QueryParam("pollId") long pollId) { 
		Poll poll=PollService.getPoll(pollId);
		JSONObject jsonPoll = new JSONObject();
		List <Question> questionList =new ArrayList <Question> ();
		List <Choice> choiceList =new ArrayList <Choice> ();
		try {
			if (poll!=null) {
				questionList=QuestionService.getQuestions(poll.getId());
				JSONArray questionArray= new JSONArray();  
				for (Question question:questionList) {
					JSONObject jsonQuestion = new JSONObject();
					jsonQuestion.put("title", question.getTitle());
					jsonQuestion.put("kind", question.getType());
					choiceList=ChoiceService.getChoices(question.getId());
					JSONArray choiceArray= new JSONArray();  
					for (Choice choice:choiceList) {
						JSONObject jsonChoice = new JSONObject();
						jsonChoice.put("title", choice.getTitle());
						jsonChoice.put("index", choice.getIndex());
						choiceArray.put(jsonChoice);
					}
					jsonQuestion.put("options", choiceArray);
					questionArray.put(jsonQuestion);
				}

				jsonPoll.put("questions", questionArray);
				jsonPoll.put("pollId", poll.getId());
				jsonPoll.put("title", poll.getTitle());
				jsonPoll.put("ownerName", poll.getOwner().getName());
				jsonPoll.put("type", poll.getType());
				jsonPoll.put("language", poll.getLanguage());
				jsonPoll.put("country", poll.getCountry());
				jsonPoll.put("numOfVote", poll.getNumOfVote());
				jsonPoll.put("ownerId", poll.getOwner().getId());
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
	//localhost:9999/PollAppWebService/poll/getMyPolls?userId=17&page=1&numOfEachPage=20&type=all
	@GET
	@Path("getMyPolls")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	public Response getMyPolls(@DefaultValue("1")@QueryParam("page") int page, @DefaultValue("20")@QueryParam("numOfEachPage") int numOfEachPage,
			@DefaultValue("-1")@QueryParam("userId") long userId , @DefaultValue("all")@QueryParam("type") String type ) {
		JSONArray pollArray= new JSONArray(); 
		try {
			if (type.equals("owner")) {
				pollArray=PollService.getMyOwnerPolls(page, numOfEachPage,userId);
			} else if (type.equals("vote")) {
				pollArray=PollService.getMyVotePolls(page, numOfEachPage,userId);
			}else if (type.equals("group")) {
				pollArray=PollService.getMyGroupPolls(page, numOfEachPage,userId);
			} else {
				pollArray=PollService.getMyAllPolls(page, numOfEachPage,userId);
			}
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}

		return Response.status(200).entity(pollArray.toString()).build();
	}
	
	//localhost:9999/PollAppWebService/webService/poll/nonPrizeStatistic?pollId=50
	@GET
	@Path("nonPrizeStatistic")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	public Response getNonPrizeStatistic(@DefaultValue("-1")@QueryParam("pollId") long pollId) {
		List<Vote> voteList=VoteService.getVoteByPollId(pollId);
		JSONObject jsonStatistic = new JSONObject();
		try{
			jsonStatistic = VoteService.calculateNonPrizeStatistic(voteList,pollId);
		} catch (JSONException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
			return Response.status(200).entity("JSON Error").build();
		}
		return Response.status(200).entity(jsonStatistic.toString()).build();

	}
	//localhost:9999/PollAppWebService/poll/deletePollNonPrize
	//{"userId":17,"pollId":49}
	@POST
	@Path("deletePollNonPrize")  
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8") 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePollNonPrize(DeletePollNonPrizeJson deletepollNonPrize) {  //add default for prize
		Date date = new Date();
		boolean statusPoll=PollService.deletePollNonPrize(deletepollNonPrize);
		String status="success";
		if(!statusPoll) {
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


}

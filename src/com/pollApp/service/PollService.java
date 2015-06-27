package com.pollApp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pollApp.entityGetJson.poll.AddPollNonPrizeJson;
import com.pollApp.entityGetJson.poll.DeletePollNonPrizeJson;
import com.pollApp.errorLog.MessageLog;
import com.pollApp.finder.PollFinder;
import com.pollApp.model.Choice;
import com.pollApp.model.Poll;
import com.pollApp.model.Question;

public class PollService {
	public static void main(String[] args) {

	}

	public static long addPollNonPrize (AddPollNonPrizeJson pollNonPrize ) {
		if (pollNonPrize.getType().equals("public")) {
			pollNonPrize.setGroupId(-1);
		}

		return PollFinder.addPollNonPrize(pollNonPrize );
	}


	public static boolean deletePollNonPrize (DeletePollNonPrizeJson pollNonPrize ) {

		return PollFinder.deletePollNonPrize(pollNonPrize );
	}

	public static List<Poll> getPublicePolls (int page, int numOfEachPage,long last_update ) {

		return PollFinder.getPublicPolls(page, numOfEachPage,last_update);
	}

	public static Poll getPoll (long pollId ) {

		return PollFinder.getPoll(pollId);
	}


	@SuppressWarnings("unchecked")
	public static JSONArray getMyOwnerPolls(int page, int numOfEachPage,long userId) throws JSONException {
		JSONArray jsonResult= new JSONArray();     
		List<Poll> MyOwnerPolls= PollFinder.getMyOwnerPolls(userId);
		jsonResult=constructJsonArray(MyOwnerPolls,"owner");
		jsonResult=boundedJsonPoll(jsonResult,page, numOfEachPage);
		return jsonResult;
	}

	public static JSONArray getMyVotePolls(int page, int numOfEachPage,
			long userId) throws JSONException {
		JSONArray jsonResult= new JSONArray();     
		List<Poll> MyVotePolls= PollFinder.getMyVotePolls(userId);
		jsonResult=constructJsonArray(MyVotePolls,"vote");
		jsonResult=boundedJsonPoll(jsonResult,page, numOfEachPage);		
		return jsonResult;
	}

	public static JSONArray getMyGroupPolls(int page, int numOfEachPage,
			long userId) throws JSONException {
		JSONArray jsonResult= new JSONArray();     
		List<Poll> MyGroupePolls= PollFinder.getMyGroupPolls(userId);
		jsonResult=constructJsonArray(MyGroupePolls,"group");
		jsonResult=boundedJsonPoll(jsonResult,page, numOfEachPage);		
		return jsonResult;
	}

	public static JSONArray getMyAllPolls(int page, int numOfEachPage,
			long userId) throws JSONException {
		JSONArray jsonResult= new JSONArray();     
		List<Poll> MyGroupePolls= PollFinder.getMyGroupPolls(userId);
		List<Poll> MyVotePolls= PollFinder.getMyVotePolls(userId);
		List<Poll> MyOwnerPolls= PollFinder.getMyOwnerPolls(userId);
		jsonResult=mergeAllPollList(MyGroupePolls,MyVotePolls,MyOwnerPolls);
		jsonResult=boundedJsonPoll(jsonResult,page, numOfEachPage);		
		return jsonResult;
	}

	private static JSONArray mergeAllPollList(List<Poll> myGroupePolls,
			List<Poll> myVotePolls, List<Poll> myOwnerPolls) throws JSONException {
		JSONArray jsonResult= new JSONArray();     
		JSONArray votejson= new JSONArray();     
		JSONArray groupjson= new JSONArray(); 
		JSONArray ownerjson= new JSONArray();
		ownerjson=constructJsonArray(myOwnerPolls,"owner");
		Iterator<Poll> iteVote = myVotePolls.iterator();
		while(iteVote.hasNext()) {
			Poll poll=iteVote.next();
			for (Poll ownerPoll:myOwnerPolls) {
				if (ownerPoll.getId()==poll.getId()) {
					iteVote.remove();
					break;
					}
			}
		}
		votejson=constructJsonArray(myVotePolls,"vote");
		Iterator<Poll> iteGroup = myGroupePolls.iterator();
		while(iteGroup.hasNext()) {
			Poll poll=iteGroup.next();
			for (Poll ownerPoll:myOwnerPolls) {
				if (ownerPoll.getId()==poll.getId()) {
					iteGroup.remove();
					break;
				}
			}
		}
		Iterator<Poll> iteGroup2 = myGroupePolls.iterator();
		while(iteGroup2.hasNext()) {
			Poll poll=iteGroup2.next();
			for (Poll votePoll:myVotePolls) {
				if (votePoll.getId()==poll.getId()) {
					iteGroup2.remove();	
					break;
				}
			}
		}
		groupjson=constructJsonArray(myGroupePolls,"group");
		for (int i=0; i<ownerjson.length();i++) {
			jsonResult.put(ownerjson.getJSONObject(i));
		}
		for (int i=0; i<votejson.length();i++) {
			jsonResult.put(votejson.getJSONObject(i));
		}
		for (int i=0; i<groupjson.length();i++) {
			jsonResult.put(groupjson.getJSONObject(i));
		}

		return jsonResult;
	}

	private static JSONArray boundedJsonPoll(JSONArray jsonResult,int page, int numOfEachPage) throws JSONException {
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		JSONArray sortedJsonArray = new JSONArray();
		for (int i = 0; i < jsonResult.length(); i++) {
			jsonValues.add(jsonResult.getJSONObject(i));
		}	
		Collections.sort(jsonValues, new SortPollByDate());
		List<JSONObject> pagingPoll = new ArrayList<JSONObject>();
		int from=numOfEachPage*(page-1);
		int maxValue=(numOfEachPage*page > jsonValues.size())? jsonValues.size():numOfEachPage*page;
		if (from<jsonValues.size()) {
			pagingPoll =jsonValues.subList(numOfEachPage*(page-1), maxValue);
		}
		for (int i = 0; i < pagingPoll.size(); i++) {
			sortedJsonArray.put(jsonValues.get(i));
		}
		return sortedJsonArray;
	}

	private static JSONArray constructJsonArray(List<Poll> pollList,String type) throws JSONException {
		JSONArray pollArray= new JSONArray();     
		List <Question> questionList =new ArrayList <Question> ();
		List <Choice> choiceList =new ArrayList <Choice> ();
		for (Poll poll:pollList) {
			JSONObject jsonPoll = new JSONObject();
			questionList=QuestionService.getQuestions(poll.getId());
			JSONArray questionArray= new JSONArray();  
			/*for (Question question:questionList) {
				JSONObject jsonQuestion = new JSONObject();
				jsonQuestion.put("title", question.getTitle());
				jsonQuestion.put("level", question.getLevel());
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

			jsonPoll.put("questions", questionArray);*/
			jsonPoll.put("pollId", poll.getId());
			jsonPoll.put("relatedType",type);
			jsonPoll.put("title", poll.getTitle());
			jsonPoll.put("ownerName", poll.getOwner().getName());
			jsonPoll.put("type", poll.getType());
			jsonPoll.put("language", poll.getLanguage());
			jsonPoll.put("country", poll.getCountry());
			jsonPoll.put("numOfVote", poll.getNumOfVote());
			jsonPoll.put("ownerId", poll.getOwner().getId());
			jsonPoll.put("creationDate", poll.getCreationDate());
			if(poll.getGroup()!=null) {
				jsonPoll.put("groupId", poll.getGroup().getId());
			} 
			pollArray.put(jsonPoll);
		}
		return pollArray;
	}







}

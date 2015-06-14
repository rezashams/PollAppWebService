package com.pollApp.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pollApp.finder.QuestionFinder;
import com.pollApp.finder.VoteFinder;
import com.pollApp.model.Question;
import com.pollApp.model.Vote;

public class VoteService {

	public static long voteToNonPrizePoll (long userId,long pollId, long creationVote, int vote) {		
		return VoteFinder.voteToNonPrizePoll(userId, pollId, creationVote, vote );		
	}

	public static List<Vote> getVoteByPollId (long pollId) {		
		return VoteFinder.getVoteByPollId(pollId );		
	}

	public static JSONObject calculateStatistic (List<Vote> voteList, long pollId) throws JSONException {	

		JSONObject jsonPollStatistic = new JSONObject();
		JSONObject jsonstatistic = new JSONObject();

		jsonPollStatistic.put("pollId", pollId);	
		List<Question> questionList=QuestionFinder.getQuestions(pollId);
		int numOfQuestion=0;
		for(Question question:questionList){
			numOfQuestion=question.getNumOfOption();   	
		}
		int statitic [] = new int [numOfQuestion];
		for (int num=1;num<=numOfQuestion;num++) {
			statitic[num-1]=0;
		}
		for (Vote vote:voteList) {
			statitic[vote.getIndexOfSelecChoice()-1]++;
		}
		
		for (int num=1;num<=numOfQuestion;num++) {
			jsonstatistic.put(String.valueOf(num), statitic[num-1]);
		}
		jsonPollStatistic.put("statistic", jsonstatistic);
		return jsonPollStatistic;		
	}


}

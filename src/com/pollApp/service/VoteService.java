package com.pollApp.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pollApp.finder.PollFinder;
import com.pollApp.finder.QuestionFinder;
import com.pollApp.finder.VoteFinder;
import com.pollApp.model.Poll;
import com.pollApp.model.Question;
import com.pollApp.model.Vote;

public class VoteService {

	public static long voteToNonPrizePoll (long userId,long pollId, long creationVote, int vote) {		
		return VoteFinder.voteToNonPrizePoll(userId, pollId, creationVote, vote );		
	}

	public static List<Vote> getVoteByPollId (long pollId) {		
		return VoteFinder.getVoteByPollId(pollId );		
	}

	public static JSONObject calculateNonPrizeStatistic (List<Vote> voteList, long pollId) throws JSONException {	

		JSONObject jsonPollStatistic = new JSONObject();
		JSONArray jsonstatistic = new JSONArray();
        Poll poll=PollFinder.getPoll(pollId);
        int numOfVote=poll.getNumOfVote();
        long adminId=poll.getOwner().getId();
		jsonPollStatistic.put("numOfVote", numOfVote);
		jsonPollStatistic.put("adminId", adminId);
		jsonPollStatistic.put("pollId", pollId);

		List<Question> questionList=QuestionFinder.getQuestions(pollId);
		int numOfOption=0;
		for(Question question:questionList){
			numOfOption=question.getNumOfOption();   	
		}
		int statitic [] = new int [numOfOption];
		for (int num=1;num<=numOfOption;num++) {
			statitic[num-1]=0;
		}
		for (Vote vote:voteList) {
			statitic[vote.getIndexOfSelecChoice()-1]++;
		}
		
		for (int num=1;num<=numOfOption;num++) {
			JSONObject jsonChoice = new JSONObject();
			jsonChoice.put("index", String.valueOf(num));
			jsonChoice.put("count", statitic[num-1]);
			jsonstatistic.put(jsonChoice);
		}
		
		jsonPollStatistic.put("choices", jsonstatistic);
		return jsonPollStatistic;		
	}


}

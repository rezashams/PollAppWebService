package com.pollApp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.pollApp.finder.PollFinder;
import com.pollApp.model.Poll;

public class PollService {
	public static void main(String[] args) {
		List<Poll> p=getMyPolls(3, 3, 3L);
		for (Poll p1:p) {
			System.out.println(p1.getId());
		}
	}

	public static long addPoll (String title, String choices, long date_created,
			String type, long owner_id, int prize,long group_id ) {
		String statistic= initialStatistic(choices);
		if (type.equals("public")) {
			group_id=0;
		}

		return PollFinder.addPoll(title, choices,statistic, date_created, type, owner_id, prize, group_id);
	}


	public static List<Poll> getPolls (int page, int numOfEachPage,long last_update,String type,boolean prize,long group_id ) {

		return PollFinder.getPolls(page, numOfEachPage,last_update,type,prize,group_id);
	}

	public static Poll getPoll (long poll_id ) {

		return PollFinder.getPoll(poll_id);
	}

	private static String initialStatistic(String choices) {
		String[] choiceArray=choices.split("[$][$][$]");
		StringBuilder statistic = new StringBuilder(); 
		for (int i=0;i<choiceArray.length-1;i++) {
			statistic.append('0');
			statistic.append("$$$");
		}
		statistic.append('0');
		return statistic.toString();

	}


	@SuppressWarnings("unchecked")
	public static List<Poll> getMyPolls(int page, int numOfEachPage,long user_id) {
		List<Poll> MyPolls= PollFinder.getMyPolls(user_id);
		Collections.sort(MyPolls, new SortPollByDate());
		List<Poll> pagingPoll = new ArrayList<Poll>();
		int from=numOfEachPage*(page-1);
		int maxValue=(numOfEachPage*page > MyPolls.size())? MyPolls.size():numOfEachPage*page;
		if (from<MyPolls.size()) {
			pagingPoll =MyPolls.subList(numOfEachPage*(page-1), maxValue);
		}
		return pagingPoll;
	}

}

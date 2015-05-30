package com.pollApp.service;

import com.pollApp.finder.PollUserFinder;

public class PollUserService {
	
public static long voteToPoll (long user_id,long poll_id, long vote_created, int vote) {		
		return PollUserFinder.voteToPoll(user_id, poll_id, vote_created, vote );		
	}

}

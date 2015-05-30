package com.pollApp.entityGetJson;

public class PollUser_voteJson {
	long userId;
	long pollId;
	int vote;
	
	public PollUser_voteJson(){}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getPollId() {
		return pollId;
	}

	public void setPollId(long pollId) {
		this.pollId = pollId;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	
}

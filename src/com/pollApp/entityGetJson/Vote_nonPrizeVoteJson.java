package com.pollApp.entityGetJson;

public class Vote_nonPrizeVoteJson {
	long userId;
	long pollId;
	int selectedIndex;
	
	public Vote_nonPrizeVoteJson(){}

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

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	
	
}

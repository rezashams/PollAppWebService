package com.pollApp.entityGetJson;

public class Poll_addPollJson {
	String title;
	String choices;
	String type;
	long ownerId;
	long groupId;
	boolean prize;
	
	public Poll_addPollJson () {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChoices() {
		return choices;
	}

	public void setChoices(String choices) {
		this.choices = choices;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public boolean isPrize() {
		return prize;
	}

	public void setPrize(boolean prize) {
		this.prize = prize;
	}
	
	

}

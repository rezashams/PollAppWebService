package com.pollApp.entityGetJson.poll;

import java.util.ArrayList;
import java.util.List;


public class AddPollNonPrizeJson {
	String title;
	String type;
	long ownerId;
	long groupId;
	String language;
	String country;
	List<ChoicesJson> options = new ArrayList <ChoicesJson>();

	public AddPollNonPrizeJson () {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<ChoicesJson> getOptions() {
		return options;
	}

	public void setOptions(List<ChoicesJson> options) {
		this.options = options;
	}



	

}

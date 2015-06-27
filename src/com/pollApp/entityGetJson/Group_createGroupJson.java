package com.pollApp.entityGetJson;

import java.util.ArrayList;
import java.util.List;

public class Group_createGroupJson {
	
	long adminId;
	String name;
	long id;
	long userId;
	List<Group_createGroup_membersJson> members = new ArrayList<Group_createGroup_membersJson> () ;
	
	public long getAdminId() {
		return adminId;
	}
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Group_createGroup_membersJson> getMembers() {
		return members;
	}
	public void setMembers(List<Group_createGroup_membersJson> members) {
		this.members = members;
	}

}

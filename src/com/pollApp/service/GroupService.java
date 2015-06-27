package com.pollApp.service;

import java.util.List;

import com.pollApp.entityGetJson.Group_addMemberToGroup;
import com.pollApp.entityGetJson.Group_createGroupJson;
import com.pollApp.entityGetJson.Group_createGroup_membersJson;
import com.pollApp.entityGetJson.Group_deleteGroup;
import com.pollApp.entityGetJson.Group_removeMemberFromGroupJson;
import com.pollApp.finder.GroupFinder;
import com.pollApp.finder.PollFinder;
import com.pollApp.model.Poll;
import com.pollApp.model.User;

public class GroupService {

	public static long createGroup(Group_createGroupJson groupJson) {
		long groupId=GroupFinder.createGroup(groupJson );
		boolean status=true;
		if (groupId>0) {
			status=GroupFinder.addToGroup(groupJson.getAdminId(),groupId);
			for (Group_createGroup_membersJson member:groupJson.getMembers()) {		
				GroupFinder.addToGroup(member.getUserId(),groupId);
			}
		}		
		if (!status) {
			groupId=-1;
		}
		return groupId;
	}

	public static boolean addMemberToGroup(Group_addMemberToGroup memberJson) {
		return GroupFinder.addToGroup(memberJson.getUserId(),memberJson.getGroupId());
	}

	public static boolean removeMemberFromGroup(Group_removeMemberFromGroupJson removememberJson) {
		return GroupFinder.removeMemberFromGroup(removememberJson.getUserId(),removememberJson.getGroupId());
	}

	public static boolean deleteGroup(Group_deleteGroup deleteGroupJson) {
		return GroupFinder.deleteGroup(deleteGroupJson);

	}

	public static List<User> getMemberOfGroup(long groupId) {
		return GroupFinder.getMemberOfGroup(groupId);
	}

	public static List<Poll> getPollsOfGroup(int page, int numOfEachPage,
			long lastUpdate, long groupId) {
		return GroupFinder.getPollsOfGroup(page, numOfEachPage,lastUpdate,groupId);
	}

}

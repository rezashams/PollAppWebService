package com.pollApp.service;

import java.util.List;

import com.pollApp.entityGetJson.Group_addMemberToGroup;
import com.pollApp.entityGetJson.Group_createGroupJson;
import com.pollApp.entityGetJson.Group_deleteGroup;
import com.pollApp.entityGetJson.Group_removeMemberFromGroupJson;
import com.pollApp.finder.GroupFinder;
import com.pollApp.finder.PollFinder;
import com.pollApp.model.User;

public class GroupService {

	public static long createGroup(Group_createGroupJson groupJson) {
		long groupId=GroupFinder.createGroup(groupJson );
		boolean status=true;
		if (groupId>0) {
			status=GroupFinder.addToGroup(groupJson.getAdminId(),groupId);
		}		
		if (!status) {
			groupId=-1;
		}
		return groupId;
	}

	public static boolean addMemberToGroup(Group_addMemberToGroup memberJson) {
		return GroupFinder.addToGroup(memberJson.getMemberId(),memberJson.getGroupId());
	}

	public static boolean removeMemberFromGroup(Group_removeMemberFromGroupJson removememberJson) {
		return GroupFinder.removeMemberFromGroup(removememberJson.getMemberId(),removememberJson.getGroupId());
	}

	public static boolean deleteGroup(Group_deleteGroup deleteGroupJson) {
		return GroupFinder.deleteGroup(deleteGroupJson);

	}

	public static List<User> getMemberOfGroup(long groupId) {
		return GroupFinder.getMemberOfGroup(groupId);
	}

}

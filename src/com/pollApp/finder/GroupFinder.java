package com.pollApp.finder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.pollApp.entityGetJson.Group_createGroupJson;
import com.pollApp.entityGetJson.Group_deleteGroup;
import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Groups;
import com.pollApp.model.User;

public class GroupFinder {

	public static long createGroup(Group_createGroupJson groupJson) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Transaction tx = null;
		Groups group=null;
		long groupId=-1;
		try{
			User  admin =  (User) session.get(User.class, groupJson.getAdminId());
			Date date= new Date();
			admin.setLastSeen(date.getTime());
			group = new Groups();
			group.setName(groupJson.getName());;
			group.setDate_created(date.getTime());
			group.setAdmin(admin);;
			tx = session.beginTransaction();
			groupId=  (long) session.save(group);
			tx.commit();
		
		}catch (HibernateException e) {		
			MessageLog.log(e.toString());
			e.printStackTrace();
		}finally { 
			session.close();
			factory.close(); 
		}
		return groupId; }

	public static boolean addToGroup(long adminId,long groupId) {
       boolean status=true;
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		try {
			Transaction tx = null;
			User  user =  (User) session.get(User.class,adminId);
			Date date= new Date();
			user.setLastSeen(date.getTime()); 
			tx = session.beginTransaction();
			session.update(user);
			tx.commit();
			Groups  group =  (Groups) session.get(Groups.class,groupId);
			Set<User> userList= group.getJoinedusers();
			userList.add(user);
			group.setJoinedusers(userList);
			tx = session.beginTransaction();
			session.update(group);
			tx.commit();
		}catch (HibernateException e) {		
			MessageLog.log(e.toString());
			e.printStackTrace();
			status=false;
		}finally { 
			session.close();
			factory.close(); 
		}

    return status;

	}

	public static boolean removeMemberFromGroup(long memberId, long groupId) {
		
		 boolean status=true;
			SessionFactory factory=Factory.initial();
			Session session = factory.openSession();
			try {
				Transaction tx = null;
				User  user =  (User) session.get(User.class,memberId);
				Date date= new Date();
				user.setLastSeen(date.getTime()); 
				tx = session.beginTransaction();
				session.update(user);
				tx.commit();
				Groups  group =  (Groups) session.get(Groups.class,groupId);
				Set<User> userList= group.getJoinedusers();
				userList.remove(user);
				group.setJoinedusers(userList);
				tx = session.beginTransaction();
				session.update(group);
				tx.commit();
			}catch (HibernateException e) {		
				MessageLog.log(e.toString());
				e.printStackTrace();
				status=false;
			}finally { 
				session.close();
				factory.close(); 
			}

	    return status;
	}

	public static boolean deleteGroup(Group_deleteGroup deleteGroupJson) {
		boolean status=true;
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		try {

			Date date= new Date();
			Groups group=null;
			User user=null;
			Transaction tx = null;
			group =  (Groups) session.get(Groups.class, deleteGroupJson.getGroupId());
			if (deleteGroupJson.getUserId()>0) {
			user =  (User) session.get(User.class, deleteGroupJson.getUserId());
			user.setLastSeen(date.getTime());
			tx = session.beginTransaction();
			session.update(user);
			tx.commit();
		}
			tx = session.beginTransaction();
			session.delete(group);
			tx.commit();
		}catch (HibernateException e) {		
			MessageLog.log(e.toString());
			e.printStackTrace();
			status=false;
		}finally { 
			session.close();
			factory.close(); 
		}
		return status;	
		}

	public static List<User> getMemberOfGroup(long groupId) {

		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		List<User> Userlist = new ArrayList<User>(0);
		try {
			Groups group=null;
			group =  (Groups) session.get(Groups.class, groupId);
			Userlist.addAll(group.getJoinedusers());
		}catch (HibernateException e) {		
			MessageLog.log(e.toString());
			e.printStackTrace();
		}finally { 
			session.close();
			factory.close(); 
		}
		return Userlist ;	
	
	}

}

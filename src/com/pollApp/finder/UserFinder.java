package com.pollApp.finder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.pollApp.entityGetJson.User_updateUserJson;
import com.pollApp.errorLog.*;
import com.pollApp.model.*;
public class UserFinder {
    public static void main(String[] args) {
    	//addUser("123132", 2222L,"reza");
    	System.out.println(getUser("123132"));
    	updateActiveUser(17,1111L);
	}
	public static long getUser(String phoneNumber) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		long userId=-1;
		try{
			Transaction tx = null;
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("phone", phoneNumber));
			User user = (User) criteria.uniqueResult();
			if (user!=null ) {
				userId=user.getId();
			}    
			tx.commit();
		}catch (Exception e) {
			MessageLog.log(e.toString());
			e.printStackTrace();
		}finally { 
			session.close();
			factory.close();
		}	
		return userId;
	}

	public static void updateActiveUser(long userId , long activedDate) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		try{
			Transaction tx = null;
			tx = session.beginTransaction();
			User  user =  (User) session.get(User.class, userId);
			user.setActive(true);
			user.setLastSeen(activedDate);
			session.update(user);
			tx.commit();
		}catch (HibernateException e) {
			MessageLog.log(e.getMessage());
			e.printStackTrace();
		}finally { 
			session.close();		
			factory.close();
		}	
	}

	public static long addUser(String phoneNumber ,long date, String name) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Transaction tx = null;
		long userId = -1;
		try{
			User user = new User();
			user.setLastSeen(date);
			user.setCreationDate(date);
			user.setPhone(phoneNumber);
			user.setName(name);
			user.setActive(true);
			tx = session.beginTransaction();
			userId= (long) session.save(user);
			tx.commit();
		}catch (HibernateException e) {		
			MessageLog.log(e.getMessage());
			e.printStackTrace();
		}finally {
			session.close();
			factory.close();
		}
		return userId;
	}
	public static List<Groups> getGroupsOfUser(long userId) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		List<Groups> grouplist = new ArrayList<Groups>(0);
		try {
			User user=null;
			user =  (User) session.get(User.class, userId);
			grouplist.addAll(user.getIsInGroups());
		}catch (HibernateException e) {		
			MessageLog.log(e.toString());
			e.printStackTrace();
		}finally { 
			session.close();
			factory.close(); 
		}
		return grouplist ;		}
	public static boolean updateUser(User_updateUserJson updateUserJson) {
		boolean status=true;
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			Date date = new Date();
			User user=null;
			user =  (User) session.get(User.class, updateUserJson.getUserId());
			if (user==null) {
				return false;
			}
			tx = session.beginTransaction();
			if(updateUserJson.getAge()!=0) {
				user.setAge(updateUserJson.getAge());
			}
			if(updateUserJson.getName()!=null) {
				user.setName(updateUserJson.getName());
			}
			if(updateUserJson.getFamily()!=null) {
				user.setFamily(updateUserJson.getFamily());
			}
			if(updateUserJson.getGender()!=null) {
			user.setGender(updateUserJson.getGender());
			}
			if(updateUserJson.getDegree()!=null) {
				user.setDegree(updateUserJson.getDegree());
			}
			if(updateUserJson.getJob()!=null) {
				user.setJob(updateUserJson.getJob());
			}
			if(updateUserJson.getEmail()!=null) {
				user.setEmail(updateUserJson.getEmail());
			}
			if(updateUserJson.getPhoto()!=null) {
				user.setPhoto(updateUserJson.getPhoto());
			}
			if(updateUserJson.getCountry()!=null) {
				user.setCountry(updateUserJson.getCountry());
			}
			if(updateUserJson.getProvince()!=null) {
				user.setProvince(updateUserJson.getProvince());
			}
			if(updateUserJson.getCity()!=null) {
				user.setCity(updateUserJson.getCity());
			}
			if(updateUserJson.getLanguage()!=null) {
				user.setLanguage(updateUserJson.getLanguage());
			}
			if(updateUserJson.getCountryCode()!=0) {
				user.setCountryCode(updateUserJson.getCountryCode());
			}
			user.setLastSeen(date.getTime());
			session.update(user);
		    session.flush();
			tx.commit();
		}catch (HibernateException e) {		
			MessageLog.log(e.toString());
			status=false;
		}finally { 
			session.close();
			factory.close(); 
		}
		return status ;		}

}

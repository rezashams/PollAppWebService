package com.pollApp.finder;

import org.hibernate.Criteria;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.pollApp.errorLog.*;
import com.pollApp.model.*;
public class UserFinder {

	public static long getUser(String phoneNumber) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		long userId=0;
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
			user.setLast_seen(activedDate);
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
		long userId = 0;
		try{
			User user = new User();
			user.setLast_seen(date);
			user.setDate_created(date);
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

}

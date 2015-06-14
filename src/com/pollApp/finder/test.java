package com.pollApp.finder;


import java.util.Date;
import java.util.List;

import com.pollApp.model.*;
import com.pollApp.service.PollService;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.json.JSONException;
import org.json.JSONObject;



public class test {
public static void main(String[] args) {
	testgetPolls();
	JSONObject jsonObject = new JSONObject();
	try {
		jsonObject.put("user_id", 3);
		jsonObject.put("poll_id", 18);
		jsonObject.put("vote", 3);

	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//System.out.println(jsonObject.toString());
}
public static void testgetPolls() {
	int page=1;
	int numOfEachPage=5;
	long last_update=12345;
	String type="public";
	boolean prize=true;
	long group_id=0;
	List <Poll> pollList=PollService.getPolls(page, numOfEachPage,last_update,type,group_id);
	for (Poll poll:pollList) {
		System.out.println(poll.getId());
		System.out.println(poll.getCreationDate());
	}
}


public static void testAddUser() {
	Date date= new Date();
	   long createddate=date.getTime();
	  String phoneNumber="+989158591805";
	      long userId=UserFinder.getUser(phoneNumber);
	      if (userId!=0) {
	    	  UserFinder.updateActiveUser(userId,createddate);
	      } else {
	    	  //userId= UserFinder.addUser(phoneNumber, createddate);
	      }
}

public static void testAddPoll() {
	String title="nazari dar morede footbal dari?";
	String choices= "shayad$$$na$$$man az koja bedonam";
	long date_created=5234;
	String type="public";
	long owner_id=1;
	int prize=1;
	long group_id=0;
	//PollService.addPoll(title, choices, date_created, type, owner_id, prize, group_id);
}

public static void delete() {
	SessionFactory factory=Factory.initial();
	Session session = factory.openSession();
    Transaction tx = null;
	
    try{

    Poll  group =  (Poll) session.get(Poll.class, 6L);
    tx = session.beginTransaction();
    session.delete(group);
    tx.commit();
}catch (HibernateException e) {
	e.printStackTrace();
}finally { factory.close(); }	
}
}

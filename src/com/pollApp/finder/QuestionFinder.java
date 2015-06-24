package com.pollApp.finder;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.pollApp.model.Poll;
import com.pollApp.model.Question;

public class QuestionFinder {
	public static void main(String[] args) {		
	}
	
	public static List<Question> getQuestions (Long pollId ) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Transaction tx = null;
		String hql = "FROM Question where pollId="+String.valueOf(pollId);
		Query query = session.createQuery(hql);
		List<Question>QuestionList=query.list();
        session.close();
		 factory.close(); 
		return  QuestionList;
	}

}

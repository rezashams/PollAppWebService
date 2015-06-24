package com.pollApp.finder;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.pollApp.model.Choice;


public class ChoiceFinder {
	public static void main(String[] args) {
	}
	
	public static List<Choice> getChoices (Long questionId ) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Transaction tx = null;
		String hql = "FROM Choice where QuestionId="+String.valueOf(questionId);
		Query query = session.createQuery(hql);
		List<Choice>ChoiceList=query.list();
        session.close();
		 factory.close(); 
		return  ChoiceList;
	}

}

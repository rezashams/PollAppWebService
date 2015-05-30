package com.pollApp.finder;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Category;
import com.pollApp.model.Comment;
import com.pollApp.model.Groups;
import com.pollApp.model.Poll;
import com.pollApp.model.PollUser;
import com.pollApp.model.User;

public class Factory {
	public static SessionFactory  initial(){
		SessionFactory factory=null;
		try{
			factory = new AnnotationConfiguration(). configure().addPackage("persistent")
					.addAnnotatedClass(Category.class)
					.addAnnotatedClass(User.class)
					.addAnnotatedClass(Groups.class)
					.addAnnotatedClass(Poll.class)
					.addAnnotatedClass(PollUser.class)
					.addAnnotatedClass(Comment.class)
					. buildSessionFactory();

		}catch (Throwable ex) {
			ex.printStackTrace();
			MessageLog.log(ex.toString());
		}
		return factory;
	}

}

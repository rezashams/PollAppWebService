package com.pollApp.finder;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Category;
import com.pollApp.model.Choice;
import com.pollApp.model.Comment;
import com.pollApp.model.Groups;
import com.pollApp.model.Poll;
import com.pollApp.model.PollUser;
import com.pollApp.model.Prize;
import com.pollApp.model.Question;
import com.pollApp.model.SelectedChoice;
import com.pollApp.model.User;
import com.pollApp.model.Vote;

public class Factory {
	public static SessionFactory  initial(){
		SessionFactory factory=null;
		try{
			factory = new AnnotationConfiguration(). configure().addPackage("persistent")
					.addAnnotatedClass(Choice.class)
					.addAnnotatedClass(User.class)
					.addAnnotatedClass(Groups.class)
					.addAnnotatedClass(Poll.class)
					.addAnnotatedClass(Vote.class)
					.addAnnotatedClass(Prize.class)
					.addAnnotatedClass(Question.class)
					.addAnnotatedClass(Prize.class)
					.addAnnotatedClass(SelectedChoice.class)
					.addAnnotatedClass(Category.class)
					.addAnnotatedClass(Comment.class)
					.addAnnotatedClass(PollUser.class)
					. buildSessionFactory();

		}catch (Throwable ex) {
			ex.printStackTrace();
			MessageLog.log(ex.toString());
		}
		return factory;
	}

}

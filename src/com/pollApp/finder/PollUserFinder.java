package com.pollApp.finder;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Poll;
import com.pollApp.model.PollUser;
import com.pollApp.model.User;

public class PollUserFinder {

	public static void main(String[] args) {
	}

	public static long voteToPoll (long user_id,long poll_id, long vote_date, int vote) {
		long pollUser_id=0;
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		try {
			String hql = "FROM PollUser where poll_id='"+String.valueOf(poll_id) +"'"
					+"and user_id='"+String.valueOf(user_id) +"'";
			Query query = session.createQuery(hql);
			List <PollUser> pollUserList= query.list();
			for (PollUser pollUser:pollUserList) {
				pollUser_id=pollUser.getId();
			}
			Transaction tx = null;	

			if (pollUser_id==0) {
				User  user =  (User) session.get(User.class, user_id);
				Poll  poll =  (Poll) session.get(Poll.class, poll_id);
				int numOfVote=poll.getNumOfVote()+1;
				poll=updateStatistic(poll,vote); 
				poll.setLast_activity_time(vote_date);
				user.setLast_seen(vote_date);
				poll.setNumOfVote(numOfVote);
				PollUser pollUser= new PollUser();
				pollUser.setUser(user);
				pollUser.setPoll(poll);
				pollUser.setIs_like(false);
				pollUser.setIs_dislike(false);
				pollUser.setVote_created(vote_date);
				pollUser.setVote(vote);
				tx = session.beginTransaction();
				pollUser_id=  (long) session.save(pollUser);
				tx.commit();
			}else {
				PollUser  pollUser =  (PollUser) session.get(PollUser.class, pollUser_id);
				if (pollUser.getVote()==0) {
					User  user =  (User) session.get(User.class, user_id);
					Poll  poll =  (Poll) session.get(Poll.class, poll_id);
					poll=updateStatistic(poll,vote); 
					poll.setLast_activity_time(vote_date);
					user.setLast_seen(vote_date);
					pollUser.setUser(user);
					pollUser.setPoll(poll);
					pollUser.setVote_created(vote_date);
					pollUser.setVote(vote);
					tx = session.beginTransaction();
					session.update(pollUser);
					tx.commit();
				} else {
					pollUser_id=0;
				}
			}
		}catch (Exception e) {		
			MessageLog.log(e.getLocalizedMessage());
			e.printStackTrace();
		}finally { 
            session.close();
			factory.close();
                 }
		return pollUser_id;
	}

	private static Poll updateStatistic (Poll poll,int vote) throws Exception {
		String statistic=poll.getStatistic();
		StringBuilder changedStatistic = new StringBuilder(); 
		String[] statisticArray=statistic.split("[$][$][$]");
		statisticArray[vote-1]=String.valueOf(Integer.valueOf(statisticArray[vote-1])+1);
		for (int i=0;i<statisticArray.length-1;i++) {
			changedStatistic.append(statisticArray[i]);
			changedStatistic.append("$$$");
		}
		changedStatistic.append(statisticArray[statisticArray.length-1]);
		poll.setStatistic(changedStatistic.toString());
		return poll;
	}
}

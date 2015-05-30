package com.pollApp.finder;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Comment;
import com.pollApp.model.Groups;
import com.pollApp.model.Poll;
import com.pollApp.model.PollUser;
import com.pollApp.model.User;

public class PollFinder {
	public static void main(String[] args) {
	/*	List<Poll> p=getMyPolls(1, 5, 3L);
		for (Poll p1:p) {
			System.out.println(p1.getId());
		}*/
		
	}

	public static long addPoll (String title, String choices, String statistic, long date_created,
			String type, long owner_id, int prize,long group_id ) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Transaction tx = null;
		Poll poll=null;
		long poll_id=0;
		try{
			User  owner =  (User) session.get(User.class, owner_id);
			owner.setLast_seen(date_created);
			poll = new Poll();
			poll.setTitle(title);
			poll.setChoice(choices);
			poll.setStatistic(statistic);
			poll.setDate_created(date_created);
			poll.setType(type);
			poll.setLast_activity_time(date_created);
			poll.setLike_number(0);
			poll.setDislike_number(0);
			poll.setPrize(prize==1?true:false);
			poll.setOwner(owner);
			if (group_id==0) {
				poll.setGroup(null);
			} else {
				Groups  group =  (Groups) session.get(Groups.class, group_id);
				poll.setGroup(group);
			}
			tx = session.beginTransaction();
			poll_id=  (long) session.save(poll);
			tx.commit();

		}catch (HibernateException e) {		
			MessageLog.log(e.toString());
			e.printStackTrace();
		}finally { 
			session.close();
			factory.close(); 
		          }
		return poll_id; 
	}

	public static List<Poll> getPolls(int page, int numOfEachPage,long last_update,String type,boolean prize,long group_id  ) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Transaction tx = null;
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("FROM Poll where date_created > "+ String.valueOf(last_update));
		if (prize==true) {
			hqlBuilder.append(" and prize=true");
		}
		if (type.equals("public")) {
			hqlBuilder.append(" and type='public'");
		} else {
			hqlBuilder.append(" and type='group'");
			hqlBuilder.append(" and group_id="+String.valueOf(last_update));
		}
		hqlBuilder.append("  order by date_created desc");

		String hql = hqlBuilder.toString();
		Query query = session.createQuery(hql);
		query.setFirstResult(numOfEachPage*(page-1));
		query.setMaxResults(numOfEachPage);
		List<Poll> pollList=query.list();
        session.close();
		 factory.close(); 
		return  pollList;

	}
	
	public static Poll getPoll (long poll_id ) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Poll poll=null;
		poll =  (Poll) session.get(Poll.class, poll_id);
        session.close();
		 factory.close(); 
		return  poll;

	}

	public static List<Poll> getMyPolls(long user_id) {
		List<Poll> finalList = new ArrayList();
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		User  user =  (User) session.get(User.class, user_id);
		List<Poll> ownerList = new ArrayList<Poll>(user.getPolls());
		List<PollUser> voteLikeList = new ArrayList<PollUser>(user.getPollUsers());
		List<Poll> voteList = new ArrayList<Poll>();
		for (PollUser pollUser:voteLikeList) {
			voteList.add(pollUser.getPoll());
		}
		List<Comment> commentList = new ArrayList<Comment>(user.getComments());
		List<Poll> userComment = new ArrayList<Poll>();
		for (Comment com:commentList) {
			userComment.add(com.getPoll());
		}
		session.close();
		factory.close();
		ownerList.removeAll(voteList);
		voteList.addAll(ownerList);
		voteList.removeAll(userComment);
		userComment.addAll(voteList);
		finalList=userComment;
		return finalList;
	}	
}

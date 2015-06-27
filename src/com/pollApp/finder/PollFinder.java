package com.pollApp.finder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.pollApp.entityGetJson.poll.AddPollNonPrizeJson;
import com.pollApp.entityGetJson.poll.DeletePollNonPrizeJson;
import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Choice;
import com.pollApp.model.Comment;
import com.pollApp.model.Groups;
import com.pollApp.model.Poll;
import com.pollApp.model.PollUser;
import com.pollApp.model.Question;
import com.pollApp.model.User;
import com.pollApp.model.Vote;

public class PollFinder {
	public static void main(String[] args) {

	}

	public static long addPollNonPrize (AddPollNonPrizeJson pollNonPrize ) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Transaction tx = null;
		Poll poll=null;
		long pollId=-1;
		try{
			User  owner =  (User) session.get(User.class, pollNonPrize.getOwnerId());
			Date date= new Date();
			owner.setLastSeen(date.getTime());
			poll = new Poll();
			poll.setTitle(pollNonPrize.getTitle());
			poll.setCreationDate(date.getTime());
			poll.setType(pollNonPrize.getType());
			poll.setLastActivityDate(date.getTime());
			poll.setOwner(owner);
			if (pollNonPrize.getGroupId()==-1) {
				poll.setGroup(null);
			} else {
				Groups  group =  (Groups) session.get(Groups.class, pollNonPrize.getGroupId());
				poll.setGroup(group);
			}		
			Question question = new Question ();
			question.setTitle(pollNonPrize.getTitle());
			question.setType("oneChoice");
			question.setPoll(poll);
			question.setLevel(1);
			question.setRelativeQuestion(false);
			question.setNumOfSelectableChoices(1);
			question.setNumOfOption(pollNonPrize.getOptions().size());
			question.setParentId(null);
			tx = session.beginTransaction();
			pollId=  (long) session.save(poll);
			tx.commit();
			for (int option=0 ; option < pollNonPrize.getOptions().size(); option++) {
				tx = session.beginTransaction();
				Choice choice = new Choice();
				choice.setIndex(pollNonPrize.getOptions().get(option).getIndex());
				choice.setTitle(pollNonPrize.getOptions().get(option).getTitle());
				choice.setQuestion(question);
				session.save(choice);
				tx.commit();
			}
		}catch (HibernateException e) {		
			MessageLog.log(e.toString());
			e.printStackTrace();
		}finally { 
			session.close();
			factory.close(); 
		}
		return pollId; 
	}

	public static List<Poll> getPublicPolls(int page, int numOfEachPage,long lastUpdate ) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		session.createSQLQuery("SET GLOBAL max_allowed_packet = 1024*1024");
		
		Transaction tx = null;
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("FROM Poll where creationDate > "+ String.valueOf(lastUpdate));
		hqlBuilder.append(" and type='public'");
		hqlBuilder.append("  order by creationDate desc");
		String hql = hqlBuilder.toString();
		Query query = session.createQuery(hql);
		query.setFirstResult(numOfEachPage*(page-1));
		query.setMaxResults(numOfEachPage);
		List<Poll> pollList=query.list();
		session.close();
		factory.close(); 
		return  pollList;

	}

	public static Poll getPoll (long pollId ) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Poll poll=null;
		poll =  (Poll) session.get(Poll.class, pollId);
		session.close();
		factory.close(); 
		return  poll;

	}

	public static List<Poll> getMyOwnerPolls(long user_id) {
		List<Poll> finalList = new ArrayList();
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		User  user =  (User) session.get(User.class, user_id);
		List<Poll> ownerList = new ArrayList<Poll>(user.getPolls());
		session.close();
		factory.close();
		return ownerList;
		
	}

	public static boolean deletePollNonPrize(DeletePollNonPrizeJson pollNonPrize) {
		boolean status=true;
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		try {

			Date date= new Date();
			Poll poll=null;
			User user=null;
			Transaction tx = null;
			poll =  (Poll) session.get(Poll.class, pollNonPrize.getPollId());
			if (pollNonPrize.getUserId()>0) {
			user =  (User) session.get(User.class, pollNonPrize.getUserId());
			user.setLastSeen(date.getTime());
			tx = session.beginTransaction();
			session.update(user);
			tx.commit();
		}
			tx = session.beginTransaction();
			session.delete(poll);
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

	public static List<Poll> getMyVotePolls(long userId) {
		List<Poll> finalList = new ArrayList<Poll>();
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		User  user =  (User) session.get(User.class, userId);
		List<Vote> votedPollList = new ArrayList<Vote>(user.getVotes());
		for (Vote vote:votedPollList) {
			finalList.add(vote.getPoll());
		}
		session.close();
		factory.close();
		return finalList;	
	}

	public static List<Poll> getMyGroupPolls(long userId) {
		List<Poll> finalList = new ArrayList<Poll>();
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		User  user =  (User) session.get(User.class, userId);
		List<Groups> groupList = new ArrayList<Groups>(user.getGroups());
		for (Groups group:groupList) {
			List<Poll> PollList=new ArrayList<Poll>(group.getPolls());
			finalList.addAll(PollList);
		}
		session.close();
		factory.close();
		return finalList;	
	}	
}

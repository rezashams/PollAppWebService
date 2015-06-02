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
import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Choice;
import com.pollApp.model.Comment;
import com.pollApp.model.Groups;
import com.pollApp.model.Poll;
import com.pollApp.model.PollUser;
import com.pollApp.model.Question;
import com.pollApp.model.User;

public class PollFinder {
	public static void main(String[] args) {
	List<Poll> p=getPolls(1,5,1L,"public",0L );
		for (Poll p1:p) {
			System.out.println(p1.getId());
		}
		
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

	public static List<Poll> getPolls(int page, int numOfEachPage,long lastUpdate,String type,long groupId  ) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		Transaction tx = null;
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("FROM Poll where creationDate > "+ String.valueOf(lastUpdate));
		if (type.equals("public")) {
			hqlBuilder.append(" and type='public'");
		} else if (type.equals("group")){
			hqlBuilder.append(" and type='group'");
			hqlBuilder.append(" and groupId="+String.valueOf(groupId));
		}  else if (type.equals("prize")){
			hqlBuilder.append(" and type='prize'");
		}
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
		List<Poll> voteList = new ArrayList<Poll>();
		
		List<Poll> userComment = new ArrayList<Poll>();
		
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

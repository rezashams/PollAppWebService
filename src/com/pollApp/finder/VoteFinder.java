package com.pollApp.finder;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.pollApp.errorLog.MessageLog;
import com.pollApp.model.Poll;
import com.pollApp.model.Vote;
import com.pollApp.model.User;

public class VoteFinder {

	public static void main(String[] args) {
		voteToNonPrizePoll(17L,49L,12121L,2);
	}

	public static long voteToNonPrizePoll (long userId,long pollId, long creationVote, int choice) {
		long voteId=-1;
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		try {
			String hql = "FROM Vote where pollId='"+String.valueOf(pollId) +"'"
					+"and userId='"+String.valueOf(userId) +"'";
			Query query = session.createQuery(hql);
			List <Vote> VoteList= query.list();
			for (Vote vote :VoteList) {
				voteId=vote.getId();
			}
			Transaction tx = null;	

			if (voteId==-1) {
				User  user =  (User) session.get(User.class, userId);
				Poll  poll =  (Poll) session.get(Poll.class, pollId);
				int numOfVote=poll.getNumOfVote()+1;
				poll.setLastActivityDate(creationVote);
				user.setLastSeen(creationVote);
				poll.setNumOfVote(numOfVote);
				Vote vote= new Vote();
				vote.setUser(user);
				vote.setPoll(poll);
				vote.setDate(creationVote);
				vote.setIndexOfSelecChoice(choice);
				tx = session.beginTransaction();
				voteId=  (long) session.save(vote);
				tx.commit();
			}else {
				Vote  vote =  (Vote) session.get(Vote.class, voteId);
					User  user =  (User) session.get(User.class, userId);
					Poll  poll =  (Poll) session.get(Poll.class, pollId);
					poll.setLastActivityDate(creationVote);
					user.setLastSeen(creationVote);
					vote.setUser(user);
					vote.setPoll(poll);
					vote.setDate(creationVote);
					vote.setIndexOfSelecChoice(choice);
					tx = session.beginTransaction();
					session.update(vote);
					tx.commit();
			
			}
		}catch (Exception e) {		
			MessageLog.log(e.getLocalizedMessage());
			e.printStackTrace();
		}finally { 
            session.close();
			factory.close();
                 }
		return voteId;
	}

	public static List<Vote> getVoteByPollId(long pollId) {
		SessionFactory factory=Factory.initial();
		Session session = factory.openSession();
		String hql="FROM Vote where pollId = "+ String.valueOf(pollId);
		Query query = session.createQuery(hql);
		List<Vote> voteList=query.list();
        session.close();
		factory.close(); 
		return  voteList;
	}

	
}

package com.pollApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PollUser")
public class PollUser {
	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	@ManyToOne
    @JoinColumn(name = "user_id")  
	private User user;
	@ManyToOne
    @JoinColumn(name = "poll_id")  
	private Poll poll;
	@Column(name = "vote_created")
	private long vote_created;
	@Column(name = "is_dislike")
	private boolean is_dislike;
	@Column(name = "is_like")
	private boolean is_like;
	@Column(name = "vote")
	private int vote;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	public long getVote_created() {
		return vote_created;
	}
	public void setVote_created(long vote_created) {
		this.vote_created = vote_created;
	}
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	public boolean isIs_dislike() {
		return is_dislike;
	}
	public void setIs_dislike(boolean is_dislike) {
		this.is_dislike = is_dislike;
	}
	public boolean isIs_like() {
		return is_like;
	}
	public void setIs_like(boolean is_like) {
		this.is_like = is_like;
	}


}

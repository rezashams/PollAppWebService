package com.pollApp.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Comment")
public class Comment {
	
	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	@ManyToOne
    @JoinColumn(name="user_id")
    private User user;
	@ManyToOne
    @JoinColumn(name="poll_id")
    private Poll poll;
	@Column(name = "body")
	private String body;
	@Column(name = "comment_date")
	private long comment_date;
	public Comment() {}
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
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public long getComment_date() {
		return comment_date;
	}
	public void setComment_date(long comment_date) {
		this.comment_date = comment_date;
	}

}

package com.pollApp.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name="Poll")
public class Poll {
	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	@Column(name = "title")
	private String title;
	@ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
	@Column(name = "choices")
	private String choice;
	@Column(name = "statistic")
	private String statistic;
	@Column(name = "date_created")
	private long date_created;
	@Column(name = "last_activity_time")
	private long last_activity_time;
	@Column(name = "like_number")
	private Integer like_number;
	@Column(name = "dislike_number")
	private Integer dislike_number;
	@Column(name = "prize")
	private Boolean prize;
	@Column(name = "numOfVote")
	private int numOfVote;
	@ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;
	@ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="group_id")
    private Groups group;
	@Column(name = "type")
	private String type;
	@OneToMany(mappedBy="poll",cascade = CascadeType.ALL)
	private Set<PollUser> pollUsers=new HashSet<PollUser>(0);
	@OneToMany(mappedBy="poll",cascade = CascadeType.ALL)
	private Set<Comment> comments=new HashSet<Comment>(0);
	public Poll(){}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public String getStatistic() {
		return statistic;
	}
	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}
	public long getDate_created() {
		return date_created;
	}
	public void setDate_created(long date_created) {
		this.date_created = date_created;
	}
	public long getLast_activity_time() {
		return last_activity_time;
	}
	public void setLast_activity_time(long last_activity_time) {
		this.last_activity_time = last_activity_time;
	}
	
	public boolean isPrize() {
		return prize;
	}
	public void setPrize(boolean prize) {
		this.prize = prize;
	}
	public Groups getGroup() {
		return group;
	}
	public void setGroup(Groups group) {
		this.group = group;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Set<PollUser> getPollUsers() {
		return pollUsers;
	}

	public void setPollUsers(Set<PollUser> pollUsers) {
		this.pollUsers = pollUsers;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Boolean getPrize() {
		return prize;
	}

	public void setPrize(Boolean prize) {
		this.prize = prize;
	}

	public void setLike_number(Integer like_number) {
		this.like_number = like_number;
	}

	public void setDislike_number(Integer dislike_number) {
		this.dislike_number = dislike_number;
	}

	public Integer getLike_number() {
		return like_number;
	}

	public Integer getDislike_number() {
		return dislike_number;
	}

	public int getNumOfVote() {
		return numOfVote;
	}

	public void setNumOfVote(int numOfVote) {
		this.numOfVote = numOfVote;
	}

}

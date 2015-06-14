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
import javax.persistence.OneToOne;
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
	@Column(name = "creationDate")
	private long creationDate;
	@Column(name = "lastActivityDate")
	private long lastActivityDate;
	@Column(name = "numOfVote")
	private int numOfVote;
	@ManyToOne
    @JoinColumn(name="ownerId")
    private User owner;
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="groupId")
    private Groups group;
	@Column(name = "type")
	private String type;
	@Column(name = "numOfQuestion")
	private int numOfQuestion;
	@Column(name = "language")
	private String language;
	@Column(name = "country")
	private String country;
	
	@OneToOne(mappedBy="poll",cascade = CascadeType.ALL)
	private Prize prize;
	
	@OneToMany(mappedBy="poll",cascade = CascadeType.ALL)
	private Set<Vote> votes=new HashSet<Vote>(0);
	
	@OneToMany(mappedBy="poll",cascade = CascadeType.ALL)
	  private Set<Question> question=new HashSet<Question>(0);
	
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


	public int getNumOfVote() {
		return numOfVote;
	}

	public void setNumOfVote(int numOfVote) {
		this.numOfVote = numOfVote;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public long getLastActivityDate() {
		return lastActivityDate;
	}

	public void setLastActivityDate(long lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	public int getNumOfQuestion() {
		return numOfQuestion;
	}

	public void setNumOfQuestion(int numOfQuestion) {
		this.numOfQuestion = numOfQuestion;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Set<Vote> getVotes() {
		return votes;
	}

	public void setVotes(Set<Vote> votes) {
		this.votes = votes;
	}

	public Set<Question> getQuestion() {
		return question;
	}

	public void setQuestion(Set<Question> question) {
		this.question = question;
	}

	public Prize getPrize() {
		return prize;
	}

	public void setPrize(Prize prize) {
		this.prize = prize;
	}

}

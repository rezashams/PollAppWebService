package com.pollApp.model;

import java.util.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;  
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

@Entity
@Table(name="Groups")
public class Groups {
	
	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "date_created")
	private long date_created;
	@ManyToOne
    @JoinColumn(name="admin_id")
    private User admin;
	@OneToMany (mappedBy="group")
	private Set<Poll> polls=new HashSet<Poll>(0);
	@ManyToMany
	@JoinTable(name="UserGroup",
     joinColumns={@JoinColumn(name="group_id")},
     inverseJoinColumns={@JoinColumn(name="user_id")})
	private Set<User> joinedusers = new HashSet <User>();  
	public Groups() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDate_created() {
		return date_created;
	}

	public void setDate_created(long date_created) {
		this.date_created = date_created;
	}
	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public Set<User> getJoinedusers() {
		return joinedusers;
	}

	public void setJoinedusers(Set<User> joinedusers) {
		this.joinedusers = joinedusers;
	}

	public Set<Poll> getPolls() {
		return polls;
	}

	public void setPolls(Set<Poll> polls) {
		this.polls = polls;
	}

}

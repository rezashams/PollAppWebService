package com.pollApp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Category")
public class Category {
	
	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	@Column(name = "photo")
	private String photo;
	@OneToMany(mappedBy="category")
	private Set<Poll> polls=new HashSet<Poll>(0);
	
	public Category (){}
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Set<Poll> getPolls() {
		return polls;
	}
	public void setPolls(Set<Poll> polls) {
		this.polls = polls;
	}
	
}
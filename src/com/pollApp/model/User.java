package com.pollApp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.metamodel.binding.CascadeType;

@Entity
@Table(name="User")
public class User{
	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "phone")
	private String phone;
	@Column(name = "family")
	private String family;
	@Column(name = "gender")
	private String gender;
	@Column(name = "age")
	private Integer age;
	@Column(name = "degree")
	private String degree;
	@Column(name = "job")
	private String job;
	@Column(name = "email")
	private String email;
	@Column(name = "active")
	private Boolean active;
	@Column(name = "photo_small")
	private String photo_small;
	@Column(name = "country")
	private String country;
	@Column(name = "province")
	private String province;
	@Column(name = "city")
	private String city;
	@Column(name = "date_created")
	private long date_created;
	@Column(name = "last_seen")
	private long last_seen;
	@OneToMany(mappedBy="admin")
	private Set<Groups> groups;
	@OneToMany(mappedBy="owner")
	private Set<Poll> polls;
	@OneToMany(mappedBy="user")
	private Set<PollUser> pollUsers=new HashSet<PollUser>(0);
	@OneToMany(mappedBy="user")
	private Set<Comment> comments=new HashSet<Comment>(0);
	@ManyToMany()
	private Set<Groups> isInGroups = new HashSet <Groups>();  
	public User(){}

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPhoto_small() {
		return photo_small;
	}

	public void setPhoto_small(String photo_small) {
		this.photo_small = photo_small;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getDate_created() {
		return date_created;
	}

	public void setDate_created(long date_created) {
		this.date_created = date_created;
	}

	public long getLast_seen() {
		return last_seen;
	}

	public void setLast_seen(long last_seen) {
		this.last_seen = last_seen;
	}
	public Set<Groups> getGroups() {
		return groups;
	}

	public void setGroups(Set<Groups> groups) {
		this.groups = groups;
	}

	public Set<Groups> getIsInGroups() {
		return isInGroups;
	}

	public void setIsInGroups(Set<Groups> isInGroups) {
		this.isInGroups = isInGroups;
	}

	public Set<Poll> getPolls() {
		return polls;
	}

	public void setPolls(Set<Poll> polls) {
		this.polls = polls;
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
	
}

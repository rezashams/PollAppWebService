package com.pollApp.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="Prize")
public class Prize {

	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "expireDate")
	private long expireDate;
	@Column(name = "organization")
	private String organization;
	@Column(name = "orgBanner")
	private String orgBanner;
	@Column(name = "orgMoreInfo")
	private String orgMoreInfo;
	@Column(name = "prizeValue")
	private String prizeValue;
	@Column(name = "kind")
	private String kind;
	@Column(name = "hasExpired")
	private boolean hasExpired;
	@OneToOne
    @JoinColumn(name = "pollId")  
	private Poll poll;
	@ManyToMany
	@JoinTable(name="UserPrize",
     joinColumns={@JoinColumn(name="prizeId")},
     inverseJoinColumns={@JoinColumn(name="userId")})
	private Set<User> winnerusers = new HashSet <User>(0); 
	
	public Prize() {}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(long expireDate) {
		this.expireDate = expireDate;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getOrgBanner() {
		return orgBanner;
	}
	public void setOrgBanner(String orgBanner) {
		this.orgBanner = orgBanner;
	}
	public String getOrgMoreInfo() {
		return orgMoreInfo;
	}
	public void setOrgMoreInfo(String orgMoreInfo) {
		this.orgMoreInfo = orgMoreInfo;
	}
	public String getPrizeValue() {
		return prizeValue;
	}
	public void setPrizeValue(String prizeValue) {
		this.prizeValue = prizeValue;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public boolean isHasExpired() {
		return hasExpired;
	}
	public void setHasExpired(boolean hasExpired) {
		this.hasExpired = hasExpired;
	}
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	public Set<User> getWinnerusers() {
		return winnerusers;
	}
	public void setWinnerusers(Set<User> winnerusers) {
		this.winnerusers = winnerusers;
	}
	
	
}

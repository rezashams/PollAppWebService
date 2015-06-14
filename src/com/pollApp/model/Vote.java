package com.pollApp.model;

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

@Entity
@Table(name="Vote")
public class Vote {

	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	@ManyToOne
    @JoinColumn(name = "userId")  
	private User user;
	@ManyToOne
    @JoinColumn(name = "pollId")  
	private Poll poll;
	
	@Column(name = "indexOfSelecChoice")
	private int indexOfSelecChoice;
	
	@Column(name = "date")
	private long date;

	
	@OneToMany(mappedBy="vote",cascade = CascadeType.ALL)
	private Set<SelectedChoice> selectedChoices=new HashSet<SelectedChoice>(0);
	
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
	
	public Set<SelectedChoice> getSelectedChoices() {
		return selectedChoices;
	}
	public void setSelectedChoices(Set<SelectedChoice> selectedChoices) {
		this.selectedChoices = selectedChoices;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getIndexOfSelecChoice() {
		return indexOfSelecChoice;
	}
	public void setIndexOfSelecChoice(int indexOfSelecChoice) {
		this.indexOfSelecChoice = indexOfSelecChoice;
	}
	
}

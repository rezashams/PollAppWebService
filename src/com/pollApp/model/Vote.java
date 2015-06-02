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
    @JoinColumn(name = "useId")  
	private User user;
	@ManyToOne
    @JoinColumn(name = "pollId")  
	private Poll poll;
	@ManyToOne
	@JoinColumn(name = "choiceId")  
    private Choice choice;
	
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
	public Choice getChoice() {
		return choice;
	}
	public void setChoice(Choice choice) {
		this.choice = choice;
	}
	public Set<SelectedChoice> getSelectedChoices() {
		return selectedChoices;
	}
	public void setSelectedChoices(Set<SelectedChoice> selectedChoices) {
		this.selectedChoices = selectedChoices;
	}
	
}

package com.pollApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SelectedChoice")
public class SelectedChoice {
	
	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	@ManyToOne
    @JoinColumn(name = "voteId")  
	private Vote vote;
	@ManyToOne
    @JoinColumn(name = "questionId")  
	private Question question;
	@ManyToOne
	@JoinColumn(name = "choiceId")  
    private Choice choice;
	@Column(name = "index_")
	private int index;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Vote getVote() {
		return vote;
	}
	public void setVote(Vote vote) {
		this.vote = vote;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Choice getChoice() {
		return choice;
	}
	public void setChoice(Choice choice) {
		this.choice = choice;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

}

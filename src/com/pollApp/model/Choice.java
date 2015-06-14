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
@Table(name="Choice")
public class Choice {
	
	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	@Column(name = "title")
	private String title;
	@Column(name = "index_")
	private int index;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="questionId")
    private Question question;
	public long getId() {
		return id;
	}
	
	
	@OneToMany(mappedBy="choice",cascade = CascadeType.ALL)
	private Set<SelectedChoice> selectedChoices=new HashSet<SelectedChoice>(0);
	
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public Set<SelectedChoice> getSelectedChoices() {
		return selectedChoices;
	}
	public void setSelectedChoices(Set<SelectedChoice> selectedChoices) {
		this.selectedChoices = selectedChoices;
	}
}

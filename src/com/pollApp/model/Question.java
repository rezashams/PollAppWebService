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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Question")
public class Question {
	
	@Id @GeneratedValue
	@Column(name = "id")
	private long id;
	@Column(name = "title")
	private String title;
	@ManyToOne
    @JoinColumn(name="pollId")
    private Poll poll;
	@Column(name = "type")
	private String type;
	@Column(name = "isRelativeQuestion")
	private boolean isRelativeQuestion;
	@Column(name = "level")
	private int level;
	@Column(name = "numOfOption")
	private int numOfOption;
	@Column(name = "numOfSelectableChoices")
	private int numOfSelectableChoices;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="parentId")
    private Question paretntId;
	@OneToMany(mappedBy="question",cascade = CascadeType.ALL)
	private Set<Choice> choices=new HashSet<Choice>(0);
	
	@OneToMany(mappedBy="question",cascade = CascadeType.ALL)
	private Set<SelectedChoice> selectedChoices=new HashSet<SelectedChoice>(0);
	
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
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isRelativeQuestion() {
		return isRelativeQuestion;
	}
	public void setRelativeQuestion(boolean isRelativeQuestion) {
		this.isRelativeQuestion = isRelativeQuestion;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getNumOfOption() {
		return numOfOption;
	}
	public void setNumOfOption(int numOfOption) {
		this.numOfOption = numOfOption;
	}
	public int getNumOfSelectableChoices() {
		return numOfSelectableChoices;
	}
	public void setNumOfSelectableChoices(int numOfSelectableChoices) {
		this.numOfSelectableChoices = numOfSelectableChoices;
	}
	public Question getParentId() {
		return paretntId;
	}
	public void setParentId(Question question) {
		this.paretntId = question;
	}
	public Set<Choice> getChoices() {
		return choices;
	}
	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
	}
	public Set<SelectedChoice> getSelectedChoices() {
		return selectedChoices;
	}
	public void setSelectedChoices(Set<SelectedChoice> selectedChoices) {
		this.selectedChoices = selectedChoices;
	}
	

}

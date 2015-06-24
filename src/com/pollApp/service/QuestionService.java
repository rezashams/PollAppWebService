package com.pollApp.service;

import java.util.List;

import com.pollApp.finder.QuestionFinder;
import com.pollApp.model.Poll;
import com.pollApp.model.Question;

public class QuestionService {
	
	public static List<Question> getQuestions (Long pollId ) {

		return QuestionFinder.getQuestions(pollId);
	}

}

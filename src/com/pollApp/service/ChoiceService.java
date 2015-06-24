package com.pollApp.service;

import java.util.List;

import com.pollApp.finder.ChoiceFinder;
import com.pollApp.model.Choice;


public class ChoiceService {

	public static List<Choice> getChoices (Long questionId ) {

		return ChoiceFinder.getChoices(questionId);
	}
	
}

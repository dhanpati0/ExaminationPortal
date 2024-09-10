package com.csmtech.service;

import java.util.List;

import com.csmtech.model.CorrectAnswer;

public interface CorrectAnswerService {

	List<CorrectAnswer> getAllAnswerByQuestionId(Integer questionId);

	CorrectAnswer saveCrAns(CorrectAnswer ans);
	
	List<CorrectAnswer> getAllCorrectAnswer();

}

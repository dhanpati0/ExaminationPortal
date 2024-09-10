package com.csmtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.model.CorrectAnswer;
import com.csmtech.repository.CorrectAnswerRepo;


@Service
public class CorrectAnswerServiceImpl implements CorrectAnswerService {
	
	@Autowired
	private CorrectAnswerRepo correctAnswerRepo;

	@Override
	public List<CorrectAnswer> getAllAnswerByQuestionId(Integer questionId) {
		
		return correctAnswerRepo.getAllAnswerByQuestionId(questionId);
		
		//correctAnswerRepo.getAllAnswerByQuestionId(questionId);
	}

	@Override
	public CorrectAnswer saveCrAns(CorrectAnswer ans) {
		// TODO Auto-generated method stub
		return correctAnswerRepo.save(ans);
	}

	@Override
	public List<CorrectAnswer> getAllCorrectAnswer() {
		
		return correctAnswerRepo.findAll();
	}

	

}

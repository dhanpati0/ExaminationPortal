package com.csmtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.model.Answer;
import com.csmtech.model.CorrectAnswer;
import com.csmtech.repository.AnswerRepository;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public Answer save(Answer ans) {

		return answerRepository.save(ans);
	}

	@Override
	public List<Integer> getMark(Integer candidateId) {
		return answerRepository.getMark(candidateId);
	}

	@Override
	public List<Integer> getMarksById(Integer ids) {
		return answerRepository.getMarkById(ids);
	}

	@Override
	public Integer getAnswerByCandidateId(Integer cid) {
		
		return answerRepository.getAnswerByCandidateId(cid);
	}

	@Override
	public List<Answer> checkQuestionIsAvialableOrNot(Integer questionId,Integer cId) {
		
		return answerRepository.checkQuestionIsAvialableOrNot(questionId,cId);
	}

	@Override
	public List<Answer> save(List<Answer> checkAns) {
		// TODO Auto-generated method stub
		return answerRepository.saveAll(checkAns);
	}

	@Override
	public Answer checkQuestionIsAvialable(Integer qId, Integer candid) {
		
		return answerRepository.checkQuestionIsAvialable(qId, candid);
	}

	@Override
	public void updateAnswer(String status, Integer mark, String optionchoose, Integer candidate, Integer question,Integer ansId) {
		
		 answerRepository.updateAnswer(status,mark,optionchoose,candidate,question,ansId);
	}

	@Override
	public Integer getIdByOptionChoosed(String optionchoose, Integer cId, Integer qId) {
		
		return answerRepository.getIdByOptionChoose(optionchoose,cId,qId);
	}

	@Override
	public void deleteAnswerByQuestionId(Integer qId, Integer candid) {
		
		 answerRepository.deleteAnswerByQuestionId(qId,candid);
	}

	@Override
	public List<Answer> findByCandidate(Integer c) {
		return answerRepository.findByCandidate(c);
	}

	@Override
	public List<Answer> getAnswersByCandidateId(Integer cid) {
		
		return answerRepository.getAnswersByCandidateId(cid);
	}


}

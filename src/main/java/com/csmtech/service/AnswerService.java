package com.csmtech.service;

import java.util.List;

import com.csmtech.model.Answer;
import com.csmtech.model.CorrectAnswer;

public interface AnswerService {

	Answer save(Answer ans);

	List<Integer> getMark(Integer candidateId);

	List<Integer> getMarksById(Integer ids);

	Integer getAnswerByCandidateId(Integer cid);

	List<Answer> checkQuestionIsAvialableOrNot(Integer questionId, Integer cId);

	List<Answer> save(List<Answer> checkAns);

	Answer checkQuestionIsAvialable(Integer qId, Integer candid);

	void updateAnswer(String status, Integer mark, String optionchoose, Integer candidate, Integer question, Integer integer);

	Integer getIdByOptionChoosed(String optionchoose, Integer integer, Integer integer2);

	void deleteAnswerByQuestionId(Integer qId, Integer candid);
	
	List<Answer> findByCandidate(Integer integer);

	List<Answer> getAnswersByCandidateId(Integer cid);

}

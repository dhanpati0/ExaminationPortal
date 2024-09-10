package com.csmtech.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csmtech.model.Answer;
import com.csmtech.model.CorrectAnswer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	@Query("select mark from Answer where candidate=:candidateId")
	List<Integer> getMark(Integer candidateId);
	@Query("select mark from Answer where question_id =:ids")
	List<Integer> getMarkById(Integer ids);
	@Query(nativeQuery = true,value="select count(distinct(question_id)) from answer where candidate_id=:cid")
	Integer getAnswerByCandidateId(Integer cid);
	/*
	 * @Query("select distinct a.question from Answer a where a.candidate=:cid")
	 * List<Answer> getAnswerByCandidateId(Integer cid);
	 */
	@Query("from Answer where question=:questionId and candidate=:cId")
	List<Answer> checkQuestionIsAvialableOrNot(Integer questionId, Integer cId);
	
	@Query("from Answer where question_id=:qId and candidate=:candid")
	Answer checkQuestionIsAvialable(Integer qId, Integer candid);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true,value ="UPDATE answer a SET a.status = :status, a.mark = :mark, a.opt_choose = :optionchoose WHERE a.candidate_id = :candidate AND a.question_id = :question AND a.ans_id = :ansId")
	void updateAnswer(@Param("status") String status, @Param("mark") Integer mark, @Param("optionchoose") String optionchoose, @Param("candidate") Integer candidate, @Param("question") Integer question, Integer ansId);
	
	@Query(nativeQuery = true,value ="select a.ans_id from answer a WHERE a.opt_choose = :optionchoose AND a.candidate_id = :cId AND a.question_id = :qId")
	Integer getIdByOptionChoose(String optionchoose, Integer cId, Integer qId);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true,value ="DELETE FROM answer WHERE candidate_id = :candid AND question_id = :qId")
	void deleteAnswerByQuestionId(@Param("qId") Integer qId,@Param("candid") Integer candid);
	
	List<Answer> findByCandidate(Integer c);
	
	@Query("from Answer where candidate=:cid")
	List<Answer> getAnswersByCandidateId(Integer cid);
}

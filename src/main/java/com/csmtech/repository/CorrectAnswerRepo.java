package com.csmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.csmtech.model.CorrectAnswer;

@Repository
public interface CorrectAnswerRepo extends JpaRepository<CorrectAnswer, Integer> {

	@Query("from CorrectAnswer where questionId.questionId = :questionId")
	List<CorrectAnswer> getAllAnswerByQuestionId(Integer questionId);

}

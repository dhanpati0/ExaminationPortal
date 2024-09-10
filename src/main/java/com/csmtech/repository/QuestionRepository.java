package com.csmtech.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csmtech.bean.QuestionBean;
import com.csmtech.model.Question;
import com.csmtech.model.QuestionType;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	@Query("FROM Question WHERE questionId=:questionId")
	Question getDetailsByQuestionId(Integer questionId);

	@Query("FROM Question Where subItem.subItemId=:subItemId")
	List<Question> getAllQuestionBySubItemId(Integer subItemId);

	@Query("FROM Question q WHERE q.questionId=:questionId")
	Question getQuestionById(Integer questionId);

	@Query("FROM Question WHERE subItem.subItemId=:subItemId and questionType.questionTypeId=:questionTypeId")
	List<Question> getAllQuestionBySubItemIdForTest(@Param("subItemId") Integer subItemId,@Param("questionTypeId") Integer questionTypeId);

	@Query("FROM Question WHERE questionId=:qId")
	Question findQuestionByQuestionId(Integer qId);

//	@Query("SELECT correctAns FROM Question WHERE questionId=:questionId")
//	String getCorrectAns(Integer questionId);

	@Query("FROM Question WHERE item.itemId=:itemId and questionType.questionTypeId=:questionTypeId and subItem.subItemId=null")
	List<Question> getAllQuestionByItemId(@Param("itemId") Integer itemId,@Param("questionTypeId") Integer questionTypeId);

	
	/*
	 * @Query("From Question WHERE ") List<Question>
	 * findAllById(@Param("selectedQList") Integer selectedQList);
	 */
	 
	
	List<Question> findByQuestionIdIn(List<Integer> selectedQList);

//	@Query(nativeQuery = true, value = "SELECT q FROM question q WHERE NOT EXISTS(SELECT a FROM answer a WHERE a.question_id=:q.question_id) ORDER BY RAND() LIMIT :remainingCount")
//	//@Query(nativeQuery = true, value ="from question q where not exists(select a from answer a where a.question_id=:q.question_id)order by rand() limit:remainingCount")
//	List<QuestionBean> getAllRemainingQuestions(@Param("remainingCount") Integer remainingCount);
	
	
}

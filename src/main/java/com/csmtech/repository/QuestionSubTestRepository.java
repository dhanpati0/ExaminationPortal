package com.csmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.model.QuestionSubTest;

@Repository
public interface QuestionSubTestRepository extends JpaRepository<QuestionSubTest, Integer> {

	@Query("from QuestionSubTest where question.questionId=?1")
	List<QuestionSubTest> findByQuestionId(Integer x);

	@Query("FROM QuestionSubTest WHERE subTest.subTestId= :sId")
	List<QuestionSubTest> findAllQuestionBySubTestId(Integer sId);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM question_subtest WHERE sub_test_id=:sId")
	Integer countAllQuestionSubtestId(Integer sId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM question_subtest WHERE sub_test_id=:sId ORDER BY RAND() LIMIT :noQuestion")
	List<QuestionSubTest> findQuestionsRandomlyByGivingAdminInput(Integer noQuestion, Integer sId);

	
	@Query(nativeQuery = true, value = "SELECT * FROM question_subtest q WHERE NOT EXISTS (SELECT 1 FROM answer a WHERE a.question_id = q.questioin_id AND a.candidate_id=:cId ) AND q.sub_test_id=:sId ORDER BY RAND() LIMIT :remainingCount")
	List<QuestionSubTest> findRemainingQuestionsRandomly(Integer remainingCount, Integer sId,Integer cId);

	@Query(nativeQuery = true, value = "select qu.question_subtest_id,qu.questioin_id,qu.sub_test_id from question_subtest qu \r\n"
			+ "left join configure_table ct on ct.sub_test_id=qu.sub_test_id \r\n"
			+ "left join candidate c on c.configure_id=ct.config_id where qu.sub_test_id=:subTestId and c.candidate_id=:cId and c.progress='Completed' order by rand() limit :enterNoQuestion")
	List<QuestionSubTest> findQuestionsForIndividualCandidate(Integer subTestId, Integer cId, Integer enterNoQuestion);

}

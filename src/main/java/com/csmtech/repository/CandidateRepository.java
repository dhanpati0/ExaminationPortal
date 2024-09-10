package com.csmtech.repository;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.model.Candidate;

@Repository

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

	@Query("SELECT c From  Candidate c where c.candidateemail=:username and c.candpassword=:password")
	Candidate findCandidateByCandnameAndPassword(@Param("username") String username, @Param("password") String password);

	@Transactional
	@Modifying
	@Query("Update Candidate set isdelete='Yes' where candid=:candid")
	void deleteCandidateById(Integer candid);

	@Query("From Candidate where isdelete='No'")
	List<Candidate> findAllNotDeleted();
	//SS
//	@Query(nativeQuery = true, value = "select * from Candidate where candid=:candid")
//	Candidate findDetailsById(Integer candid);
	@Query(nativeQuery = true, value = "select * from Candidate where candidate_id=:candid")
	Candidate findDetailsById(Integer candid);

	@Query("SELECT c From  Candidate c where c.candidateemail=:username and c.candpassword=:password")
	Candidate findCandidateByCandnameAndPasswordForCheck(@Param("username") String username, @Param("password") String password);

	@Query("From Candidate where subTestTaker.subTestTakerId=:subTestTakerId")
	List<Candidate> findCandidateBySubTestTakerId(Integer subTestTakerId);

	@Query("select markAppear from Candidate")
	List<Integer> getCandidateMarkList();

	@Query("From Candidate where subTestTaker.testTaker.testTakerId=:testTakerId")
	List<Candidate> findCandidateByTestTakerId(Integer testTakerId);

	@Query("From Candidate where subTestTaker.testTaker.testTakerId=:testTakerId and subTestTaker.subTestTakerId=:subTetTakerId")
	List<Candidate> findBytestTakerIdAndsubTetTakerId(Integer testTakerId, Integer subTetTakerId);
	
	@Query("SELECT c From  Candidate c where c.candidateemail=:username")
	Candidate getCandidateByEmail(@Param("username") String username);

	@Query("From Candidate where subTestTaker.testTaker.testTakerId=:testTakerId and subTestTaker.subTestTakerId=:subtestTakerId and markAppear!='null'")
	List<Candidate> getBytestTakerIdAndsubTetTakerId(Integer testTakerId, Integer subtestTakerId);

	@Query("From Candidate where subTestTaker.subTestTakerId=?1")
	List<Candidate> findAllCandidates( @Param("subTestTakerId") Integer subTestTakerId);

	@Query("From Candidate where status=?1 and subTestTaker.subTestTakerId=?2")
	List<Candidate> findAllCandidateListByStatusAndSubTestTaker( @Param("string") String string, @Param("subTestTakerId") Integer subTestTakerId);

	/**
	 * Our changes in this file goes here.
	 */
	@Query("FROM Candidate WHERE isdelete = 'No' AND status = '1'")
	List<Candidate> findAllCandidateWithActiveStatus();
	

	@Transactional 
    @Modifying
    @Query("UPDATE Candidate SET candstart_time = :startTime WHERE candidate_id = :candidateId")
    void updateCandidateStartTime(@Param("candidateId") Integer candidateId, @Param("startTime") LocalTime startTime);

	@Query("FROM Candidate WHERE candidateemail = :candidateEmail")
	Candidate findCandidateByCandidateEmail(String candidateEmail);

	@Transactional 
    @Modifying
	@Query("UPDATE Candidate SET reasonForLogOut = :reasonForLogout WHERE candidate_id = :cId")
	void updateCandidateIfReasonAvailable(Integer cId, String reasonForLogout);

	@Query("From Candidate Where markAppear IS NOT NULL")
	List<Candidate> findAllCandidateWhoAppearedAndGetMark();

	@Query("From Candidate where progress = 'Completed'")
	List<Candidate> getAllCandidateWhoAppearedTheExam();

	@Query("From Candidate where subTestTaker.testTaker.testTakerId=:testTakerId and progress = 'Completed'")
	List<Candidate> getfindAllCandidateWhoAppearedTheExamByTestTakerId(Integer testTakerId);

	@Query("From Candidate where subTestTaker.testTaker.testTakerId=:testTakerId and subTestTaker.subTestTakerId=:subTetTakerId and progress = 'Completed'")
	List<Candidate> getfindAllCandidateWhoAppearedTheExamtestTakerIdAndsubTetTakerId(Integer testTakerId,
			Integer subTetTakerId);

	@Query("FROM Candidate WHERE progress = 'Not Started' and configure.testDate <= CURRENT_DATE")
	List<Candidate> getAllCandidateWhoNotAppearedTheExam();

	@Query("FROM Candidate WHERE subTestTaker.testTaker.testTakerId=:testTakerId and subTestTaker.subTestTakerId=:subTetTakerId and progress = 'Not Started' and configure.testDate <= CURRENT_DATE")
	List<Candidate> getAllCandidateWhoNotAppearedTheExamtestTakerIdAndsubTetTakerId(Integer testTakerId,
			Integer subTetTakerId);

	@Query("FROM Candidate WHERE subTestTaker.testTaker.testTakerId=:testTakerId and progress = 'Not Started' and configure.testDate <= CURRENT_DATE")
	List<Candidate> getAllCandidateWhoNotAppearedTheExamByTestTakerId(Integer testTakerId);
	 
	
	@Query("From Candidate where progress = 'Completed'")
	List<Candidate> findAllQualifiedCandidates();

	
	@Query("From Candidate where progress = 'Completed' and markAppear >= :cutoffMark")
	List<Candidate> getAllCandidateByCutOff(Integer cutoffMark);
	
	@Query("From Candidate where progress = 'Completed' and markAppear >= :cutoffMark and subTestTaker.testTaker.testTakerId=:testTakerId")
	List<Candidate> getAllCandidateByCutOffByTestakerId(Integer cutoffMark, Integer testTakerId);

	@Query("From Candidate where progress = 'Completed' and markAppear >= :cutoffMark and subTestTaker.testTaker.testTakerId=:testTakerId and subTestTaker.subTestTakerId=:subTetTakerId")
	List<Candidate> getAllCandidateByCutOffByTestakerIdAndSubTetTakerId(Integer testTakerId, Integer subTetTakerId,
			Integer cutoffMark);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update candidate set configure_id = :configId where subtest_taker_id = :subTestTakerId")
	void saveConfigureToCandidates(@Param("subTestTakerId") Integer subTestTakerId, @Param("configId") Integer configId);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update candidate set total_mark = :totalMark where subtest_taker_id = :subTestTakerId")
	void getCandidateBySubTestTakerIdAndSetTotalMark(Integer subTestTakerId, Integer totalMark);

//	@Transactional
//	 @Modifying
//	    @Query(value = "UPDATE candidate SET candstart_time = :startTime WHERE candidate_id = :candidateId", nativeQuery = true)
//	    void updateCandidateStartTime(@Param("candidateId") Integer candidateId, @Param("startTime") LocalTime startTime);
//	
//	@Query(nativeQuery = true, value = "select status from Candidate where candidate_id=:candid")
//	String getCandidateStatus(Integer candid);
	
	//@Query("Select count(candid) From Candidate group by subTestTaker.testTaker.testTakerId")
	@Query(nativeQuery=true,value="SELECT COUNT(c.candidate_id) AS candidate_count FROM test_taker t LEFT JOIN sub_test_taker s ON t.test_taker_id = s.test_taker_id LEFT JOIN candidate c ON s.subtest_taker_id = c.subtest_taker_id GROUP BY t.test_taker_id")
	List<Integer> totalRegisteredCandidatesInCollege();
	
	//@Query("Select COALESCE(count(candid),0) From Candidate where progress = 'Completed' group by subTestTaker.testTaker.testTakerId")
	@Query(nativeQuery = true, value = "SELECT COALESCE(COUNT(c.candidate_id), 0) FROM test_taker t LEFT JOIN sub_test_taker s ON t.test_taker_id = s.test_taker_id LEFT JOIN candidate c ON s.subtest_taker_id = c.subtest_taker_id AND c.progress = 'Completed' GROUP BY t.test_taker_id")
	List<Integer> getApperaedCandidatesInCollege();
	
	@Query(nativeQuery = true,value="SELECT COUNT(CASE WHEN c.mark_appear >= 0.60 * c.total_mark AND c.progress = 'Completed' THEN c.candidate_id ELSE NULL END) FROM test_taker t LEFT JOIN sub_test_taker s ON t.test_taker_id = s.test_taker_id LEFT JOIN candidate c ON s.subtest_taker_id = c.subtest_taker_id GROUP BY t.test_taker_id")
	List<Integer> getQualifiedCandidatesInCollege();
	
	
	@Query(nativeQuery = true,value="SELECT \r\n"
			+ "    t.test_taker_id AS test_taker_id,\r\n"
			+ "    t.test_taker_name AS CollegeName,\r\n"
			+ "    s.subtest_taker_name AS BatchName,\r\n"
			+ "    COALESCE(\r\n"
			+ "        CASE \r\n"
			+ "            WHEN conf.subtest_taker_id = s.subtest_taker_id THEN conf.test_date \r\n"
			+ "            ELSE 'NA' \r\n"
			+ "        END,\r\n"
			+ "        'NA'\r\n"
			+ "    ) AS test_date,\r\n"
			+ "    COALESCE(COUNT(c.candidate_id), 0) AS total_students,\r\n"
			+ "    COUNT(CASE WHEN c.progress = 'Completed' THEN c.candidate_id ELSE NULL END) AS appeared_students,\r\n"
			+ "    COUNT(CASE WHEN c.mark_appear >= 0.60 * c.total_mark AND c.progress = 'Completed' THEN c.candidate_id ELSE NULL END) AS qualified_students\r\n"
			+ "FROM \r\n"
			+ "    test_taker t \r\n"
			+ "LEFT JOIN \r\n"
			+ "    sub_test_taker s ON t.test_taker_id = s.test_taker_id \r\n"
			+ "LEFT JOIN \r\n"
			+ "    candidate c ON s.subtest_taker_id = c.subtest_taker_id \r\n"
			+ "LEFT JOIN \r\n"
			+ "    configure_table conf ON c.configure_id = conf.config_id \r\n"
			+ "WHERE \r\n"
			+ "    t.is_deleted = 'No'\r\n"
			+ "GROUP BY \r\n"
			+ "    s.subtest_taker_id;")
	List<Map<String, Object>> getAllCountByCollege();

	@Query(nativeQuery=true, value="SELECT \r\n"
			+ "    t.test_taker_id as test_taker_id,\r\n"
			+ "    t.test_taker_name as CollegeName,\r\n"
			+ "    s.subtest_taker_name as BatchName,\r\n"
			+ "    COALESCE(\r\n"
			+ "        CASE \r\n"
			+ "            WHEN conf.subtest_taker_id = s.subtest_taker_id THEN conf.test_date \r\n"
			+ "            ELSE 'NA' \r\n"
			+ "        END, 'NA'\r\n"
			+ "    ) AS test_date,\r\n"
			+ "    COALESCE(COUNT(c.candidate_id), 0) AS total_students,\r\n"
			+ "    COUNT(CASE WHEN c.progress = 'Completed' THEN c.candidate_id ELSE NULL END) AS appeared_students,\r\n"
			+ "    COUNT(CASE WHEN c.mark_appear >= 0.60 * c.total_mark AND c.progress = 'Completed' THEN c.candidate_id ELSE NULL END) AS qualified_students\r\n"
			+ "FROM \r\n"
			+ "    test_taker t \r\n"
			+ "LEFT JOIN \r\n"
			+ "    sub_test_taker s ON t.test_taker_id = s.test_taker_id \r\n"
			+ "LEFT JOIN \r\n"
			+ "    candidate c ON s.subtest_taker_id = c.subtest_taker_id \r\n"
			+ "LEFT JOIN \r\n"
			+ "    configure_table conf ON c.configure_id = conf.config_id \r\n"
			+ "    WHERE conf.test_date BETWEEN ?1 AND ?2 \r\n"
			+ "GROUP BY \r\n"
			+ "    s.subtest_taker_id;")
	List<Map<String, Object>> getAllCountByCollegeBySession(String startDate, String endDate);

	@Query(nativeQuery=true, value="SELECT \r\n"
			+ "    t.test_taker_id as test_taker_id,\r\n"
			+ "    t.test_taker_name as CollegeName,\r\n"
			+ "    s.subtest_taker_name as BatchName,\r\n"
			+ "    COALESCE(\r\n"
			+ "        CASE \r\n"
			+ "            WHEN conf.subtest_taker_id = s.subtest_taker_id THEN conf.test_date \r\n"
			+ "            ELSE 'NA' \r\n"
			+ "        END, 'NA'\r\n"
			+ "    ) AS test_date,\r\n"
			+ "    COALESCE(COUNT(c.candidate_id), 0) AS total_students,\r\n"
			+ "    COUNT(CASE WHEN c.progress = 'Completed' THEN c.candidate_id ELSE NULL END) AS appeared_students,\r\n"
			+ "    COUNT(CASE WHEN c.mark_appear >= 0.60 * c.total_mark AND c.progress = 'Completed' THEN c.candidate_id ELSE NULL END) AS qualified_students\r\n"
			+ "FROM \r\n"
			+ "    test_taker t \r\n"
			+ "LEFT JOIN \r\n"
			+ "    sub_test_taker s ON t.test_taker_id = s.test_taker_id \r\n"
			+ "LEFT JOIN \r\n"
			+ "    candidate c ON s.subtest_taker_id = c.subtest_taker_id \r\n"
			+ "LEFT JOIN \r\n"
			+ "    configure_table conf ON c.configure_id = conf.config_id \r\n"
			+ "    WHERE conf.test_date BETWEEN ?1 AND ?2 AND t.test_taker_id = ?3 \r\n"
			+ "GROUP BY \r\n"
			+ "    s.subtest_taker_id;")
	List<Map<String, Object>> getAllCountByCollegeBySessionAndTestTakerId(String startDate, String endDate,
			Integer testTakerId);

	@Query(nativeQuery=true, value="SELECT \r\n"
			+ "    t.test_taker_id as test_taker_id,\r\n"
			+ "    t.test_taker_name as CollegeName,\r\n"
			+ "    s.subtest_taker_name as BatchName,\r\n"
			+ "    COALESCE(\r\n"
			+ "        CASE \r\n"
			+ "            WHEN conf.subtest_taker_id = s.subtest_taker_id THEN conf.test_date \r\n"
			+ "            ELSE 'NA' \r\n"
			+ "        END, 'NA'\r\n"
			+ "    ) AS test_date,\r\n"
			+ "    COALESCE(COUNT(c.candidate_id), 0) AS total_students,\r\n"
			+ "    COUNT(CASE WHEN c.progress = 'Completed' THEN c.candidate_id ELSE NULL END) AS appeared_students,\r\n"
			+ "    COUNT(CASE WHEN c.mark_appear >= 0.60 * c.total_mark AND c.progress = 'Completed' THEN c.candidate_id ELSE NULL END) AS qualified_students\r\n"
			+ "FROM \r\n"
			+ "    test_taker t \r\n"
			+ "LEFT JOIN \r\n"
			+ "    sub_test_taker s ON t.test_taker_id = s.test_taker_id \r\n"
			+ "LEFT JOIN \r\n"
			+ "    candidate c ON s.subtest_taker_id = c.subtest_taker_id \r\n"
			+ "LEFT JOIN \r\n"
			+ "    configure_table conf ON c.configure_id = conf.config_id \r\n"
			+ "    WHERE t.test_taker_id = ?1 \r\n"
			+ "GROUP BY \r\n"
			+ "    s.subtest_taker_id;")
	List<Map<String, Object>> getAllCountByCollegeBySessionAndTestTakerId(Integer testTakerId);
	
	
}

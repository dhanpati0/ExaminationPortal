package com.csmtech.service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.csmtech.model.Candidate;
import com.csmtech.model.Configure;

public interface CandidateService {

	Candidate findCandidateByCandnameAndPassword(String username, String password);

	Candidate saveCandidate(Candidate candidate);

	List<Candidate> findAllCandidate();

	void deleteCandidateById(Integer candid);

	Candidate updateCandidateById(Integer candid);

	Candidate findDetailsById(Integer candid);

	Candidate findCandidateByCandnameAndPasswordForCheck(String username, String password);

	List<Candidate> findCandidateBySubTestTakerId(Integer subTestTakerId);

	List<Integer> getCandidateMarkList();

	List<Candidate> findCandidateByTestTakerId(Integer testTakerId);

	List<Candidate> findBytestTakerIdAndsubTetTakerId(Integer testTakerId, Integer subTetTakerId);

	Candidate getCandidateByEmail(String username);

	List<Candidate> getBytestTakerIdAndsubTetTakerId(Integer testTakerId, Integer subtestTakerId);

	void saveAll(List<Candidate> ques);

	List<Candidate> findAllCandidates(Integer subTestTakerId);

	List<Candidate> findAllCandidateByStatusAndSubTestTaker(Integer subTestTakerId);

	/**
	 * Our Changes in this file starts from here.
	 */
	List<Candidate> findAllCandidateWithActiveStatus();
	
	boolean updateStatus(int candid);

	boolean updateCandidate(Candidate candidate);
	
	void updateCandidateStartTime(Integer candidateId, LocalTime startTime);

	Candidate findCandidateByCandidateEmail(String userEmail);

	

	void updateCandidateIfReasonAvailable(Integer cId, String reasonForLogout);

	List<Candidate> findAllCandidateWhoAppearedAndGetMark();

	void saveCandidate(List<Candidate> existingCandidate);

	List<Candidate> findAllCandidateWhoAppearedTheExam();

	List<Candidate> findAllCandidateWhoAppearedTheExamByTestTakerId(Integer testTakerId);

	List<Candidate> findAllCandidateWhoAppearedTheExamtestTakerIdAndsubTetTakerId(Integer testTakerId, Integer subTetTakerId);

	List<Candidate> findAllCandidateWhoNotAppearedTheExam();

	List<Candidate> findAllCandidateWhoNotAppearedTheExamtestTakerIdAndsubTetTakerId(Integer testTakerId,
			Integer subTetTakerId);

	List<Candidate> findAllCandidateWhoNotAppearedTheExamByTestTakerId(Integer testTakerId);
	 

	List<Candidate> findAllQualifiedCandidates();

	List<Candidate> findAllCandidateByCutOff(Integer cutoffMark);

	List<Candidate> findAllCandidateByCutOffByTestakerId(Integer cutoffMark, Integer testTakerId);

	List<Candidate> findAllCandidateByCutOffByTestakerIdAndSubTetTakerId(Integer testTakerId, Integer subTetTakerId,
			Integer cutoffMark);

	void saveConfigureToCandidates(Integer subTestTakerId, Configure saveConfig);


	void findCandidateBySubTestTakerIdAndSetTotalMark(Integer subTestTakerId, Integer noQuestion);

	List<Integer> totalRegisteredCandidatesInCollege();

	List<Integer> getApperaedCandidatesInCollege();

	List<Integer> getQualifiedCandidatesInCollege();

	List<Map<String, Object>> getAllCountByCollege();

	List<Map<String, Object>> getAllCountByCollegeBySession(String startDate, String endDate);

	List<Map<String, Object>> getAllCountByCollegeBySessionAndTestTakerId(String startDate, String endDate,
			Integer testTakerId);

	List<Map<String, Object>> getAllCountByCollegeByTestTakerId(Integer testTakerId);

	
//	String getCandidateStatus(int candid);
}

package com.csmtech.service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.model.Candidate;
import com.csmtech.model.Configure;
import com.csmtech.repository.CandidateRepository;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateRepository candidateRepo;

	@Override
	public Candidate findCandidateByCandnameAndPassword(String username, String password) {

		return candidateRepo.findCandidateByCandnameAndPassword(username, password);
	}

	@Override
	
	public Candidate saveCandidate(Candidate candidate) {
		
		return candidateRepo.save(candidate);
	}

	@Override
	public List<Candidate> findAllCandidate() {

		return candidateRepo.findAllNotDeleted();
	}

	@Override
	public void deleteCandidateById(Integer candid) {

		candidateRepo.deleteCandidateById(candid);
	}

	@Override
	public Candidate updateCandidateById(Integer candid) {

		return candidateRepo.findById(candid).get();
	}

	@Override
	public Candidate findDetailsById(Integer candid) {

		return candidateRepo.findDetailsById(candid);
	}

	@Override
	public Candidate findCandidateByCandnameAndPasswordForCheck(String username, String password) {

		return candidateRepo.findCandidateByCandnameAndPasswordForCheck(username, password);
	}

	@Override
	public List<Candidate> findCandidateBySubTestTakerId(Integer subTestTakerId) {

		return candidateRepo.findCandidateBySubTestTakerId(subTestTakerId);
	}

	@Override
	public List<Integer> getCandidateMarkList() {
		return candidateRepo.getCandidateMarkList();
	}

	@Override
	public List<Candidate> findCandidateByTestTakerId(Integer testTakerId) {
		return candidateRepo.findCandidateByTestTakerId(testTakerId);
	}

	@Override
	public List<Candidate> findBytestTakerIdAndsubTetTakerId(Integer testTakerId, Integer subTetTakerId) {
		return candidateRepo.findBytestTakerIdAndsubTetTakerId(testTakerId, subTetTakerId);
	}

	@Override
	public Candidate getCandidateByEmail(String username) {
		
		return candidateRepo.getCandidateByEmail(username);
	}

	@Override
	public List<Candidate> getBytestTakerIdAndsubTetTakerId(Integer testTakerId, Integer subtestTakerId) {
		
		return candidateRepo.getBytestTakerIdAndsubTetTakerId(testTakerId, subtestTakerId);
	}

	@Override
	public void saveAll(List<Candidate> ques) {
		candidateRepo.saveAll(ques);
		
	}

	@Override
	public List<Candidate> findAllCandidates(Integer subTestTakerId) {
		// TODO Auto-generated method stub
		return candidateRepo.findAllCandidates(subTestTakerId);
	}

	@Override
	public List<Candidate> findAllCandidateByStatusAndSubTestTaker(Integer subTestTakerId) {
		// TODO Auto-generated method stub
		return candidateRepo.findAllCandidateListByStatusAndSubTestTaker("1",subTestTakerId);
	}

	/**
	 * Our changes goes from here in this file.
	 */
	@Override
	public List<Candidate> findAllCandidateWithActiveStatus() {
		// TODO Auto-generated method stub
		return candidateRepo.findAllCandidateWithActiveStatus();
	}
	
	@Override
	public boolean updateStatus(int candid) {
	    Candidate candidate = candidateRepo.findById(candid).orElse(null);

	    if (candidate != null) {
	        
	    	if ("1".equals(candidate.getStatus())) {
	            candidate.setStatus("inactive");
	        } else {
	            candidate.setStatus("1");
	        }

	        // Save the updated candidate back to the repository
	        candidateRepo.save(candidate);
	        
	        return true;
	    }
		return false;
	}
	

	

	@Override
	public boolean updateCandidate(Candidate candidate) {
		try {candidateRepo.save(candidate);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	
	public void updateCandidateStartTime(Integer candidateId, LocalTime startTime) {
		candidateRepo.updateCandidateStartTime(candidateId, startTime);
		
	}

	@Override
	public Candidate findCandidateByCandidateEmail(String candidateEmail) {
		
		return candidateRepo.findCandidateByCandidateEmail(candidateEmail);
	}

	@Override
	public void updateCandidateIfReasonAvailable(Integer cId, String reasonForLogout) {
		
		candidateRepo.updateCandidateIfReasonAvailable(cId,reasonForLogout);
		
	}

	@Override
	public List<Candidate> findAllCandidateWhoAppearedAndGetMark() {
		
		return candidateRepo.findAllCandidateWhoAppearedAndGetMark();
	}

	@Override
	public void saveCandidate(List<Candidate> existingCandidate) {
		
		candidateRepo.saveAll(existingCandidate);
	}

	@Override
	public List<Candidate> findAllCandidateWhoAppearedTheExam() {
		
		return candidateRepo.getAllCandidateWhoAppearedTheExam();
	}

	@Override
	public List<Candidate> findAllCandidateWhoAppearedTheExamByTestTakerId(Integer testTakerId) {
		
		return candidateRepo.getfindAllCandidateWhoAppearedTheExamByTestTakerId(testTakerId);
	}

	@Override
	public List<Candidate> findAllCandidateWhoAppearedTheExamtestTakerIdAndsubTetTakerId(Integer testTakerId,
			Integer subTetTakerId) {
		// TODO Auto-generated method stub
		return candidateRepo.getfindAllCandidateWhoAppearedTheExamtestTakerIdAndsubTetTakerId(testTakerId,subTetTakerId);
	}
	
	@Override
	public List<Candidate> findAllCandidateWhoNotAppearedTheExam() {

		return candidateRepo.getAllCandidateWhoNotAppearedTheExam();
	}

	@Override
	public List<Candidate> findAllCandidateWhoNotAppearedTheExamtestTakerIdAndsubTetTakerId(Integer testTakerId,
			Integer subTetTakerId) {

		return candidateRepo.getAllCandidateWhoNotAppearedTheExamtestTakerIdAndsubTetTakerId(testTakerId,
				subTetTakerId);
	}

	@Override
	public List<Candidate> findAllCandidateWhoNotAppearedTheExamByTestTakerId(Integer testTakerId) {

		return candidateRepo.getAllCandidateWhoNotAppearedTheExamByTestTakerId(testTakerId);
	}

	@Override
	public List<Candidate> findAllQualifiedCandidates() {
		// TODO Auto-generated method stub
		return candidateRepo.findAllQualifiedCandidates();
	}


	@Override
	public List<Candidate> findAllCandidateByCutOff(Integer cutoffMark) {
		
		return candidateRepo.getAllCandidateByCutOff(cutoffMark);
	}

	@Override
	public List<Candidate> findAllCandidateByCutOffByTestakerId(Integer cutoffMark, Integer testTakerId) {
		
		return candidateRepo.getAllCandidateByCutOffByTestakerId(cutoffMark,testTakerId);
	}

	@Override
	public List<Candidate> findAllCandidateByCutOffByTestakerIdAndSubTetTakerId(Integer testTakerId,
			Integer subTetTakerId, Integer cutoffMark) {
		
		return candidateRepo.getAllCandidateByCutOffByTestakerIdAndSubTetTakerId(testTakerId,subTetTakerId,cutoffMark);
	}

	@Override
	public void saveConfigureToCandidates(Integer subTestTakerId, Configure saveConfig) {
		Integer configId = saveConfig.getConfigId();
		candidateRepo.saveConfigureToCandidates(subTestTakerId,configId);
		
	}

	@Override
	public void findCandidateBySubTestTakerIdAndSetTotalMark(Integer subTestTakerId, Integer noQuestion) {
	 Integer totalMark = 2*noQuestion;
		candidateRepo.getCandidateBySubTestTakerIdAndSetTotalMark(subTestTakerId,totalMark);
	}

	@Override
	public List<Integer> totalRegisteredCandidatesInCollege() {
		
		return candidateRepo.totalRegisteredCandidatesInCollege();
	}

	@Override
	public List<Integer> getApperaedCandidatesInCollege() {
		
		return candidateRepo.getApperaedCandidatesInCollege();
	}

	@Override
	public List<Integer> getQualifiedCandidatesInCollege() {
		// TODO Auto-generated method stub
		return candidateRepo.getQualifiedCandidatesInCollege();
	}

	@Override
	public List<Map<String, Object>> getAllCountByCollege() {
		
		return candidateRepo.getAllCountByCollege();
	}

	@Override
	public List<Map<String, Object>> getAllCountByCollegeBySession(String startDate, String endDate) {
		
		return candidateRepo.getAllCountByCollegeBySession(startDate,endDate);
	}

	@Override
	public List<Map<String, Object>> getAllCountByCollegeBySessionAndTestTakerId(String startDate, String endDate,
			Integer testTakerId) {
		
		return candidateRepo.getAllCountByCollegeBySessionAndTestTakerId(startDate,endDate,testTakerId);
	}

	@Override
	public List<Map<String, Object>> getAllCountByCollegeByTestTakerId(Integer testTakerId) {
		
		return candidateRepo.getAllCountByCollegeBySessionAndTestTakerId(testTakerId);
	}





//	@Override
//	public String getCandidateStatus(int candid) {
//		
//		return candidateRepo.getCandidateStatus(candid);
//	}
	
	
	
}

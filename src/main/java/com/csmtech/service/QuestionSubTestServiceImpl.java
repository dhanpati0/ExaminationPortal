package com.csmtech.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.bean.QuestionBean;
import com.csmtech.model.Question;
import com.csmtech.model.QuestionSubTest;
import com.csmtech.repository.QuestionSubTestRepository;

@Service
public class QuestionSubTestServiceImpl implements QuestionSubTestService {

	@Autowired
	QuestionSubTestRepository questionSubTestRepository;

	@Override
	public List<QuestionSubTest> findAll() {
		return questionSubTestRepository.findAll();
	}

	@Override
	public List<QuestionSubTest> findQuestionByQuestionId(Integer x) {
		return questionSubTestRepository.findByQuestionId(x);
	}

	@Override
	public void saveQS(QuestionSubTest questionSubTest) {
		questionSubTest.setQStId(null);
		questionSubTestRepository.save(questionSubTest);
	}

	@Override
	public List<QuestionBean> findAllQuestionBySubTest(Integer sId) {

		QuestionBean obj = null;
		List<QuestionBean> objlist = new ArrayList<>();
		try {
			List<QuestionSubTest> questionList = questionSubTestRepository.findAllQuestionBySubTestId(sId);
			
			for (QuestionSubTest q : questionList) {
				obj = new QuestionBean();
				obj.setQuestionId(q.getQuestion().getQuestionId());
				
				obj.setQuestionText(q.getQuestion().getQuestionText());
				obj.setOption1(q.getQuestion().getOption1());
				obj.setOption2(q.getQuestion().getOption2());
				obj.setOption3(q.getQuestion().getOption3());
				obj.setOption4(q.getQuestion().getOption4());
				//obj.setCorrectAns(q.getQuestion().getCorrectAns());
				obj.setItem(q.getQuestion().getItem());
				obj.setSubItem(q.getQuestion().getSubItem());
				obj.setQuestionType(q.getQuestion().getQuestionType());
				System.out.println("///"+obj+"//////");

				objlist.add(obj);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return objlist;
	}

	@Override
	public List<QuestionSubTest> saveQuestionIfNotPresent(Integer sId) {

		return questionSubTestRepository.findAllQuestionBySubTestId(sId);
	}

	@Override
	public void deleteall(List<QuestionSubTest> saveQuestionIfNotPresent) {
		questionSubTestRepository.deleteAll(saveQuestionIfNotPresent);
		
	}

	@Override
	public void saveAll(List<QuestionSubTest> saveQuestionIfNotPresent) {
		questionSubTestRepository.saveAll(saveQuestionIfNotPresent);
	}

	@Override
	public List<QuestionSubTest> findAllQuestionBySubTest1(Integer sId) {
		return questionSubTestRepository.findAllQuestionBySubTestId(sId);
	}

	@Override
	public List<QuestionBean> findQuestionsRandomlyByGivingAdminInput(Integer noQuestion, Integer sId) {
		System.out.println(sId);
		System.out.println(noQuestion);
		QuestionBean obj1 = null;
		List<QuestionBean> objlist1 = new ArrayList<>();
		try {
			List<QuestionSubTest> questionList = questionSubTestRepository.findQuestionsRandomlyByGivingAdminInput(noQuestion,sId);
			for (QuestionSubTest q : questionList) {
				obj1 = new QuestionBean();
				obj1.setQuestionId(q.getQuestion().getQuestionId());
				obj1.setQuestionText(q.getQuestion().getQuestionText());
				obj1.setOption1(q.getQuestion().getOption1());
				obj1.setOption2(q.getQuestion().getOption2());
				obj1.setOption3(q.getQuestion().getOption3());
				obj1.setOption4(q.getQuestion().getOption4());
				//obj1.setCorrectAns(q.getQuestion().getCorrectAns());
				obj1.setItem(q.getQuestion().getItem());
				obj1.setSubItem(q.getQuestion().getSubItem());
				obj1.setQuestionType(q.getQuestion().getQuestionType());


				objlist1.add(obj1);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return objlist1;
	}

	@Override
	public Integer countAllQuestionBySubtestId(Integer sId) {
		return questionSubTestRepository.countAllQuestionSubtestId(sId);
	}

	@Override
	public List<QuestionBean> getAllRemainingQuestions(Integer remainingCount, Integer sId,Integer cId) {
		System.out.println(sId);
		System.out.println(remainingCount);
		QuestionBean remain = null;
		List<QuestionBean> remainList = new ArrayList<>();
		try {
			List<QuestionSubTest> questionList = questionSubTestRepository.findRemainingQuestionsRandomly(remainingCount,sId,cId);
			for (QuestionSubTest q : questionList) {
				remain = new QuestionBean();
				remain.setQuestionId(q.getQuestion().getQuestionId());
				remain.setQuestionText(q.getQuestion().getQuestionText());
				remain.setOption1(q.getQuestion().getOption1());
				remain.setOption2(q.getQuestion().getOption2());
				remain.setOption3(q.getQuestion().getOption3());
				remain.setOption4(q.getQuestion().getOption4());
				//obj1.setCorrectAns(q.getQuestion().getCorrectAns());
				remain.setItem(q.getQuestion().getItem());
				remain.setSubItem(q.getQuestion().getSubItem());
				remain.setQuestionType(q.getQuestion().getQuestionType());


				remainList.add(remain);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return remainList;
	}

	@Override
	public List<QuestionBean> findQuestionsForIndividualCandidate(Integer subTestId, Integer cId,
			Integer enterNoQuestion) {
		QuestionBean obj2 = null;
		List<QuestionBean> QuestionsList = new ArrayList<>();
		System.out.println(":::::::::::::"+questionSubTestRepository.findQuestionsForIndividualCandidate(subTestId,cId,enterNoQuestion));
		
		try {
			List<QuestionSubTest> questionList = questionSubTestRepository.findQuestionsForIndividualCandidate(subTestId,cId,enterNoQuestion);
			for (QuestionSubTest q : questionList) {
				obj2 = new QuestionBean();
				obj2.setQuestionId(q.getQuestion().getQuestionId());
				obj2.setQuestionText(q.getQuestion().getQuestionText());
				obj2.setOption1(q.getQuestion().getOption1());
				obj2.setOption2(q.getQuestion().getOption2());
				obj2.setOption3(q.getQuestion().getOption3());
				obj2.setOption4(q.getQuestion().getOption4());
				obj2.setOption5(q.getQuestion().getOption5());
				//obj1.setCorrectAns(q.getQuestion().getCorrectAns());
				obj2.setItem(q.getQuestion().getItem());
				obj2.setSubItem(q.getQuestion().getSubItem());
				obj2.setQuestionType(q.getQuestion().getQuestionType());


				QuestionsList.add(obj2);
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
	
		System.out.println(QuestionsList+"question list for individual candidates");
		
		return QuestionsList;
	}

	
}

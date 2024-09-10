package com.csmtech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.bean.QuestionBean;
import com.csmtech.model.Items;
import com.csmtech.model.Question;
import com.csmtech.model.QuestionSubTest;
import com.csmtech.model.QuestionType;
import com.csmtech.model.SubItem;
import com.csmtech.model.SubTest;
import com.csmtech.model.Test;
import com.csmtech.model.User;
import com.csmtech.repository.QuestionSubTestRepository;

@SpringBootTest
public class QuestionSubtestServiceImplTest {

	@Mock
	private QuestionSubTestRepository questionsubTestRepository;
	
	@InjectMocks
	private QuestionSubTestService questionSubtestService = new QuestionSubTestServiceImpl();
	
	QuestionSubTest questionSubtest = createQuestionSubTest(1,1,1, 1, 1,1,"SubTest-1",1,"Test-1","byTest");
	
	List<QuestionSubTest> listquestionSubtest = Stream.of(createQuestionSubTest(1,1,1, 1, 1,1,"SubTest-1",1,"Test-1","byTest"),
			createQuestionSubTest(2,1,1, 1, 1,1,"SubTest-1",1,"Test-1","byTest")).collect(Collectors.toList());

	@BeforeEach
    void setQuestionSubtestMockOutput() {
		
		when(questionsubTestRepository.countAllQuestionSubtestId(1)).thenReturn(2);
		when(questionsubTestRepository.findAllQuestionBySubTestId(1)).thenReturn(listquestionSubtest);
		when(questionsubTestRepository.findByQuestionId(1)).thenReturn(listquestionSubtest);
		when(questionsubTestRepository.findQuestionsRandomlyByGivingAdminInput(1, 1)).thenReturn(listquestionSubtest);
		when(questionsubTestRepository.findAll()).thenReturn(listquestionSubtest);
		when(questionsubTestRepository.save(Mockito.any(QuestionSubTest.class))).thenAnswer(i -> i.getArguments()[0]);
		
		
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Test to find All Question")
	void givenIdSubTest_WhenSave_ThenReturnQuestion() {
		
		List<QuestionBean> findQuestionSubTest = questionSubtestService.findAllQuestionBySubTest(1);
		assertThat(findQuestionSubTest).isNotNull();
		assertEquals(2, findQuestionSubTest.size());
	
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Test to find Question by questionId")
	void givenQuestionId_WhenFind_ThenReturnQuestion() {
		
		List<QuestionSubTest> getQuestion =  questionSubtestService.findQuestionByQuestionId(1);
		assertThat(getQuestion).isNotNull();
		assertEquals(2, getQuestion.size());
		
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Test to find random Questions")
	void givenQuestionId_whenFind_thenReturnQuestion() {
		List<QuestionBean> listQuestion = questionSubtestService.findQuestionsRandomlyByGivingAdminInput(1, 1);
		assertThat(listQuestion).isNotNull();
		assertEquals(2, listQuestion.size());
	}
	
	
	@org.junit.jupiter.api.Test
	@DisplayName("Test to find all questionsubtest")
	void givenQuestionSubTest_WhenFoundAll_ThenReturnAll() {
		
		List<QuestionSubTest> listQuestionSubtest = questionSubtestService.findAll();
		assertThat(listQuestionSubtest).isNotNull();
		assertEquals(2, listQuestionSubtest.size());
		
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Test to find all no of questions")
	void givenSubtestId_whenfound_ThenReturnCountQuestion() {
		
		Integer countQuestion = questionSubtestService.countAllQuestionBySubtestId(1);
		assertThat(countQuestion).isNotNull();
		assertEquals(2, countQuestion);

	}
	
	
	private QuestionSubTest createQuestionSubTest(int questionSubtestId,int questionId, int itemId, int subItemId, int questionTypeId, int subtestId, String subtestName, int testId,
			String testName, String propertyName) {
		
		SubTest subTest = new SubTest();
		Test test = new Test();
		subTest.setSubTestId(1);
    	subTest.setSubTestName("Subtest-y");
    	subTest.setPropertyType("byItem");
    	test.setTestId(1);
    	test.setTestName("Test-y");
    	subTest.setTest(test);
		
		Items items = new Items();
        items.setItemId(itemId);

        SubItem subItem = new SubItem();
        subItem.setSubItemId(subItemId);

        QuestionType questionType = new QuestionType();
        questionType.setQuestionTypeId(questionTypeId);

        Question question = new Question();
        question.setQuestionId(questionId);
        question.setItem(items);
        question.setSubItem(subItem);
        question.setQuestionType(questionType);
		
		QuestionSubTest qSubTest = new QuestionSubTest();
		qSubTest.setQStId(questionSubtestId);
		qSubTest.setQuestion(question);
		qSubTest.setSubTest(subTest);
		
		return qSubTest;
	}

	
}

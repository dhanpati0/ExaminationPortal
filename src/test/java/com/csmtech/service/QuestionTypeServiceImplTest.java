package com.csmtech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.jasper.tagplugins.jstl.core.When;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.model.QuestionType;
import com.csmtech.model.Role;
import com.csmtech.model.SubTestTaker;
import com.csmtech.repository.QuestionTypeRepository;

@SpringBootTest
public class QuestionTypeServiceImplTest {
	
	@Mock
	private QuestionTypeRepository questionTypeRepository;
	
	@InjectMocks
	private QuestionTypeService questionTypeService = new QuestionTypeServiceImpl();

	QuestionType questionType = createQuestionType(1,"Subjective");
	
	List<QuestionType> listQuestionType = Stream.of(createQuestionType(1,"Objective"),
			createQuestionType(2,"Subjective")).collect(Collectors.toList());
	
	@BeforeEach
    void setQuestionTypeMockOutput() {
		
		when(questionTypeRepository.findQuestionTypeByQuestionTypeId(questionType.getQuestionTypeId())).thenReturn(questionType);
		when(questionTypeRepository.findAll()).thenReturn(listQuestionType);
		when(questionTypeRepository.save(Mockito.any(QuestionType.class))).thenAnswer(i -> i.getArguments()[0]);
		
	}
	
	@Test
	@DisplayName("Test To save QuestionType")
	void givenQuestionTypeId_WhenFind_ThenReturnQuestionType() {
		
		QuestionType qType = questionTypeService.findQuestionTypeByQuestionTypeId(questionType.getQuestionTypeId());
		assertThat(qType).isNotNull();
		assertEquals(1, qType.getQuestionTypeId());
	}
	
	@Test 
	@DisplayName("Test to get all QuestionType")
	void givenQuestionType_WhenFoundAll_ThenReturnAll() {
		
		List<QuestionType> listQuestionType = questionTypeService.getAllQuestionType();
		assertThat(listQuestionType).isNotNull();
		assertEquals(2, listQuestionType.size());
		
	}

	private QuestionType createQuestionType(int questionTypeId, String questionTypeName) {
		
		QuestionType questionType = new QuestionType();
		questionType.setQuestionTypeId(questionTypeId);
		questionType.setQuestionTypeName(questionTypeName);
		
		return questionType;
	}
	

}

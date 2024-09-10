package com.csmtech.repository;

import com.csmtech.model.Items;
import com.csmtech.model.Question;
import com.csmtech.model.QuestionSubTest;
import com.csmtech.model.QuestionType;
import com.csmtech.model.SubItem;
import com.csmtech.model.SubTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuestionSubTestRepositoryTest {

    @Autowired
    QuestionSubTestRepository questionSubTestRepository;
    
    @Test
    @Order(1)
    @DisplayName("for save Question Test")
    public void givenQuestionSubTestObject_whenSaveQuestionSubTest_ThenReturnQuestionSubTest() {
    	
    	QuestionType questionType= new QuestionType();
    	questionType.setQuestionTypeId(1);
    	questionType.setQuestionTypeName("Objective");
    	
    	Items item = new Items();
    	item.setItemId(1);
    	item.setItemName("Java");
    	
    	SubItem subItem = new SubItem();
    	subItem.setSubItemId(1);
    	subItem.setItem(item);
    	subItem.setQuestionType(questionType);
    	subItem.setSubItemName("Core Java");
    	
    	Question question = new Question();
    	question.setQuestionId(1);
    	question.setOption1("abc");
    	question.setOption2("123");
    	question.setOption3("pqr");
    	question.setOption4("456");
    	question.setOption5("xyz");
    	question.setQuestionText("What is Alphabet");
    	question.setQuestionType(questionType);
    	question.setQuestionStatus("No");
    	question.setItem(item);
    	question.setSubItem(subItem);
    	
    	com.csmtech.model.Test test = new com.csmtech.model.Test();
    	test.setTestId(1);
    	test.setTestName("SET-2");
    	
    	SubTest subtest= new SubTest();
    	
    	subtest.setSubTestId(1);
		subtest.setTest(test);
		subtest.setSubTestName("sub-set-1");
    	
    	QuestionSubTest questionSubtest = new QuestionSubTest();
    	questionSubtest.setQStId(1);
    	questionSubtest.setQuestion(question);
    	questionSubtest.setSubTest(subtest);
    	questionSubtest.setStatus(null);
    	System.out.println(questionSubtest+"///////////////////////");
    	QuestionSubTest savequestionSubtest = questionSubTestRepository.save(questionSubtest);
    	System.out.println(savequestionSubtest+"{{{{{{{{{{{{{{{{{");
    	
    	assertThat(savequestionSubtest).isNotNull();
    	assertThat(savequestionSubtest.getQStId()).isGreaterThan(0);
    	
    }

    @Order(2)
    @DisplayName("Test to find Question subTest by id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenSubTestId_WhenFound_ThenCountReturn(int id){

        //Given  Id as ValueSource

        //When find the  by Id
        int questionSubTestCount = questionSubTestRepository.countAllQuestionSubtestId(id);

        //Then verify found Question sub test
        assertNotEquals(0,questionSubTestCount);
    }

    @Order(3)
    @DisplayName("Test to find Question subTest by question id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenQuestionId_WhenFound_ThenReturn(int id){

        //Given Question Id as ValueSource

        //When find the  by Id
        List<QuestionSubTest> questionSubTests =questionSubTestRepository.findByQuestionId(id);

        //Then verify found Question sub test
        assertNotNull(questionSubTests);
        assertFalse(questionSubTests.isEmpty());
        assertTrue(questionSubTests.stream().allMatch(question
                -> question.getQuestion().getQuestionId().equals(1)));
    }

    @Order(4)
    @DisplayName("Test to find Question subTest by sub test id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenSubTestId_WhenFound_ThenReturn(int id){

        //Given sub test Id as ValueSource

        //When find the  by Id
        List<QuestionSubTest> questionSubTests =questionSubTestRepository
                .findAllQuestionBySubTestId(id);

        //Then verify found sub test id
        assertNotNull(questionSubTests);
        assertFalse(questionSubTests.isEmpty());
        assertTrue(questionSubTests.stream().allMatch(question
                -> question.getSubTest().getSubTestId().equals(1)));
    }

    @Order(5)
    @DisplayName("Test to find N number of Question subTest by sub test id")
    @ParameterizedTest
    @MethodSource("getNoQuestionAndSubTestId")
    public void givenNoQsSubTestId_WhenFound_ThenReturn(int noQs,int id){

        //Given sub test Id as ValueSource

        //When find the  by Id
        List<QuestionSubTest> questionSubTests =questionSubTestRepository
                .findQuestionsRandomlyByGivingAdminInput(noQs,id);

        //Then verify found sub test id
        assertNotNull(questionSubTests);
        assertFalse(questionSubTests.isEmpty());
        assertEquals(1,questionSubTests.size());
        assertTrue(questionSubTests.stream().allMatch(question
                -> question.getSubTest().getSubTestId().equals(1)));
    }

    static Stream<Arguments> getNoQuestionAndSubTestId() {
        return Stream.of(Arguments.of(5, 1));
    }

}

package com.csmtech.repository;

import com.csmtech.model.Items;
import com.csmtech.model.Question;
import com.csmtech.model.QuestionType;
import com.csmtech.model.SubItem;
import com.csmtech.model.TestTaker;

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
public class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;
    
    @DisplayName("To save Test")
    @Order(1)
    @Test
	public void givenQuestionObject_whenSave_thenReturnSavedQuestion() {
    	
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
    	
    	Question saveQuestion = questionRepository.save(question);
        System.out.println(saveQuestion);
		assertThat(saveQuestion).isNotNull();
	    assertThat(saveQuestion.getQuestionId()).isGreaterThan(0);
    }
    

    @Order(2)
    @DisplayName("Test to find Question by Id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenQuestionId_WhenFoundQuestion_ThenReturnQuestion(int id){

        //Given  Id as ValueSource

        //When find the  by Id
        Question question = questionRepository.getQuestionById(id);

        //Then verify found Item
        assertNotNull(question);
        assertNotNull(question.getQuestionId());
        assertEquals(1,question.getQuestionId());
    }

    @Order(3)
    @DisplayName("Test to find All Question")
    @Test
    public void givenQuestion_WhenFoundQuestions_ThenReturnQuestionList(){

        //Given  Question

        //When find the  by Id
        List<Question> questions = questionRepository.findAll();

        //Then verify found Item
        assertNotNull(questions);
        assertFalse(questions.isEmpty());
    }

    @Order(4)
    @DisplayName("Test to find Question details by Id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenQuestionId_WhenFoundDetails_ThenReturnQuestion(int id){

        //Given  Id as ValueSource

        //When find the  by Id
        Question question = questionRepository.getDetailsByQuestionId(id);

        //Then verify found Item
        assertNotNull(question);
        assertNotNull(question.getQuestionId());
        assertEquals(1,question.getQuestionId());
    }

    @Order(5)
    @DisplayName("Test to find All Question by sub item id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenSubItemId_WhenFoundQuestions_ThenReturnQuestionList(int subItemId){

        //Given  Question sub Item id

        //When find the question by sub item Id
        List<Question> questions = questionRepository.getAllQuestionBySubItemId(subItemId);

        //Then verify found question
        assertNotNull(questions);
        assertFalse(questions.isEmpty());
        assertTrue(questions.stream().allMatch(question
                -> question.getSubItem().getSubItemId().equals(1)));

    }

    @Order(6)
    @DisplayName("Test to find All Questions by question type and by sub item id")
    @ParameterizedTest
    @MethodSource("getQuestionIdAndSubItemId")
    public void givenQuestionTyepSubItemId_WhenFoundQuestions_ThenReturnQuestionList
            (int questionTypeId,int subItemId){

        //Given  Question type id and sub Item id

        //When find the questions by question type id and by sub item Id
        List<Question> questions = questionRepository
                .getAllQuestionBySubItemIdForTest(questionTypeId,subItemId);

        //Then verify found question
        assertNotNull(questions);
        assertFalse(questions.isEmpty());
        assertTrue(questions.stream().allMatch(question
                -> question.getSubItem().getSubItemId().equals(1)));
        assertTrue(questions.stream().allMatch(question
                -> question.getQuestionType().getQuestionTypeId().equals(1)));

    }
    static Stream<Arguments> getQuestionIdAndSubItemId() {
        return Stream.of(Arguments.of(1, 1));
    }

    @Order(7)
    @DisplayName("Test to find All Questions by question type and by item id")
    @ParameterizedTest
    @MethodSource("getQuestionIdAndItemId")
    public void givenQuestionTyepItemId_WhenFoundQuestions_ThenReturnQuestionList
            (int questionTypeId,int itemId){

        //Given  Question type id and sub Item id

        //When find the questions by question type id and by sub item Id
        List<Question> questions = questionRepository
                .getAllQuestionBySubItemIdForTest(questionTypeId,itemId);

        //Then verify found question
        assertNotNull(questions);
        assertFalse(questions.isEmpty());
        assertTrue(questions.stream().allMatch(question
                -> question.getItem().getItemId().equals(1)));
        assertTrue(questions.stream().allMatch(question
                -> question.getQuestionType().getQuestionTypeId().equals(1)));

    }
    static Stream<Arguments> getQuestionIdAndItemId() {
        return Stream.of(Arguments.of(1, 1));
    }
}

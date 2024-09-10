package com.csmtech.repository;

import com.csmtech.model.CorrectAnswer;
import com.csmtech.model.Items;
import com.csmtech.model.Question;
import com.csmtech.model.QuestionType;
import com.csmtech.model.SubItem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CorrectAnswerRepoTest {
	
	 @Autowired
	 private CorrectAnswerRepo correctAnswerRepo;

	
	@Test
    @Order(1)
    @DisplayName("for save CorrectAnswer")
    public void givenCorrectAnswerObject_whenSaveCorrectAnswer_ThenReturnCorrectAnswer() {
		
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
    	
		CorrectAnswer correctAns = new CorrectAnswer();
		correctAns.setAnsId(1);
		correctAns.setCorrectAns(null);
		correctAns.setQuestionId(question);
    	
		CorrectAnswer savecorrectAns = correctAnswerRepo.save(correctAns);
    	
    	assertThat(savecorrectAns).isNotNull();
    	assertThat(savecorrectAns.getAnsId()).isGreaterThan(0);
    	
    }

   
	@Order(2)
    @DisplayName("Getting Answers list by question Id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenQuestionId_WhenFoundAnsWithId_ThenReturnAnsList(int id){
        //Given candidate Id as ValueSource

        //When find the Answers by given question Id
        List<CorrectAnswer> correctAnswers =correctAnswerRepo.getAllAnswerByQuestionId(id);

        //Then verify Answers list not empty and not null
        assertNotNull(correctAnswers);
        assertFalse(correctAnswers.isEmpty());
        assertNotEquals(0, (int) correctAnswers.size());
    }
}

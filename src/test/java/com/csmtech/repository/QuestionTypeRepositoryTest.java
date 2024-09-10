package com.csmtech.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.model.QuestionType;
import com.csmtech.model.Test;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuestionTypeRepositoryTest {


    @Autowired
    QuestionTypeRepository questionTypeRepository;
    
    @DisplayName("To save QuestionType")
    @Order(1)
    @org.junit.jupiter.api.Test
	public void givenQuestionTypeObject_whenSave_thenReturnSavedQuestionType() {
    	
    	QuestionType qType = new QuestionType();
    	qType.setQuestionTypeId(1);
    	qType.setQuestionTypeName("Subjective");
    	
    	QuestionType saveQtype = questionTypeRepository.save(qType);
        System.out.println(saveQtype);
		assertThat(saveQtype).isNotNull();
	    assertThat(saveQtype.getQuestionTypeId()).isGreaterThan(0);
    }

    @Order(2)
    @DisplayName("QuestionTypeName to Find By Id")
    @org.junit.jupiter.api.Test
    public void givenId_WhenFound_ThenReturn(){
        //Given id as value source
    	
        //When found
    	QuestionType getQtype = questionTypeRepository.findQuestionTypeByQuestionTypeId(1);
        System.out.println("///"+getQtype);
        //Then validate
        assertThat(getQtype).isNotNull();
        assertEquals(1,getQtype.getQuestionTypeId());
    }
	
}

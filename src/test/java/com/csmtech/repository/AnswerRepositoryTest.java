package com.csmtech.repository;

import com.csmtech.model.Answer;
import com.csmtech.model.Candidate;
import com.csmtech.model.Items;
import com.csmtech.model.Question;
import com.csmtech.model.QuestionType;
import com.csmtech.model.SubItem;
import com.csmtech.model.SubTestTaker;
import com.csmtech.model.TestTaker;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AnswerRepositoryTest {

    @Autowired
    AnswerRepository answerRepository;
    
    @Test
    @Order(1)
    @DisplayName("for save Answer")
    public void givenAnswerObject_whenSaveAnswer_ThenReturnAnswer() {
    	
    	Answer ans = new Answer();
    	ans.setAnsId(1);
    	ans.setCandidate(1);
    	ans.setMark(60);
    	ans.setOptChoose("abc");
    	ans.setQuestion(1);
    	ans.setStatus("wrong");
    	
    	
    	Answer saveans = answerRepository.save(ans);
    	
    	assertThat(saveans).isNotNull();
    	assertThat(saveans.getAnsId()).isGreaterThan(0);
    	
    }

    @Order(2)
    @DisplayName("Test for finding marks by candidate Id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenCandidateId_WhenCandidateId_ThenReturnMarksByCandidateId(int candidateId){
        //Given candidate Id as ValueSource

        //When candidate Id exists in records find marks
        List<Integer> marks = answerRepository.getMark(candidateId);

        //Then verify candidate id from DB
        assertNotNull(marks);
        assertTrue(marks.size()>=1);
    }

    @Order(3)
    @DisplayName("Test for finding Answer by Id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenId_WhenId_ThenReturnAnswer(int id){
        //Given Id as ValueSource

        //When  Id exists in records find answer
        Answer answer = answerRepository.getReferenceById(id);

        //Then verify id from DB
        assertNotNull(answer);
        assertEquals(1, (int) answer.getAnsId());
    }
}

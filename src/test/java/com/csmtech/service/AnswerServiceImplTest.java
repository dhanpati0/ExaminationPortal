package com.csmtech.service;

import com.csmtech.model.Answer;
import com.csmtech.repository.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AnswerServiceImplTest {

    @Mock
    AnswerRepository answerRepository;

    @InjectMocks
    AnswerServiceImpl answerService;

    Answer answer = generateAnswer(1,1,2,"2","correct",2);
    List<Answer> answersList = Stream.of(
            generateAnswer(1,1,1,"1","correct",2),
            generateAnswer(2,1,2,"2","correct",2),
            generateAnswer(3,2,1,"1","wrong",0),
            generateAnswer(4,2,1,"2","correct",1)
    ).collect(Collectors.toList());

    @BeforeEach
    void setMockOutput() {
        List<Integer> marksListCandId = answersList.stream()
                .filter(x->x.getCandidate().equals(answer.getCandidate()))
                .map(Answer::getMark)
                .collect(Collectors.toList());

        List<Integer> marksListQusId = answersList.stream()
                .filter(x->x.getQuestion().equals(answer.getQuestion()))
                .map(Answer::getMark)
                .collect(Collectors.toList());

        when(answerRepository.save(Mockito.any(Answer.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(answerRepository.getMark(answer.getCandidate())).thenReturn(marksListCandId);
        when(answerRepository.getMarkById(answer.getQuestion())).thenReturn(marksListQusId);
    }

    @DisplayName("Test to save Answer")
    @Test
    void givenAnswer_WhenSave_ThenReturnSaved() {
        Answer savedAnswer = answerService.save(answer);
        assertNotNull(savedAnswer);
        assertEquals(answer.getAnsId(),savedAnswer.getAnsId());
        assertEquals(answer.getCandidate(),savedAnswer.getCandidate());
    }

    @DisplayName("Test to get marks by candidate id")
    @Test
    void givenCandId_WhenFound_ThenReturnMarksList() {
        List<Integer> marksList = answerService.getMark(answer.getCandidate());
        assertNotNull(marksList);
        assertFalse(marksList.isEmpty());
        assertEquals(2,marksList.size());
    }

    @DisplayName("Test to get marks by question id")
    @Test
    void givenQueId_WhenFound_ThenReturnMarksList() {
        List<Integer> marksList = answerService.getMarksById(answer.getQuestion());
        assertNotNull(marksList);
        assertFalse(marksList.isEmpty());
        assertEquals(1,marksList.size());
    }

    private Answer generateAnswer(int ansId,int candId,
                                  int questionId,String opSelected,String status,int marks){
        Answer answer = new Answer();
        answer.setAnsId(ansId);
        answer.setCandidate(candId);
        answer.setQuestion(questionId);
        answer.setOptChoose(opSelected);
        answer.setStatus(status);
        answer.setMark(marks);
        return answer;
    }
}

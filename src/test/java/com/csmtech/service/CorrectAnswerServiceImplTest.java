package com.csmtech.service;

import com.csmtech.model.CorrectAnswer;
import com.csmtech.model.Question;
import com.csmtech.repository.CorrectAnswerRepo;
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
public class CorrectAnswerServiceImplTest {

    @Mock
    CorrectAnswerRepo correctAnswerRepo;

    @InjectMocks
    CorrectAnswerServiceImpl correctAnswerService;

    List<CorrectAnswer> correctAnswerList = Stream.of(
                    createCAnswer(1,"my Answer1",1),
                    createCAnswer(2,"my Answer2",1),
                    createCAnswer(3,"my Answer3",3)
            ).collect(Collectors.toList());

    @BeforeEach
    void setMockOutput() {
        when(correctAnswerRepo.save(Mockito.any(CorrectAnswer.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(correctAnswerRepo.getAllAnswerByQuestionId(1)).thenReturn(correctAnswerList.stream()
                        .filter(a->a.getQuestionId().getQuestionId().equals(1)).collect(Collectors.toList()));
    }

    @DisplayName("Test to save Correct Answer")
    @Test
    void givenCorrectAnswer_WhenSave_ThenReturnSaved() {
        CorrectAnswer correctAnswer = createCAnswer(1,"My Answer",1);
        CorrectAnswer savedCorrectAnswer = correctAnswerService.saveCrAns(correctAnswer);
        assertNotNull(savedCorrectAnswer);
        assertEquals(1,savedCorrectAnswer.getQuestionId().getQuestionId());
    }

    @DisplayName("Test to find Correct Answers by Qus Id")
    @Test
    void givenQId_WhenFound_ThenReturnList() {

        List<CorrectAnswer> correctAnswers = correctAnswerService.getAllAnswerByQuestionId(1);
        System.out.println(correctAnswers);
        assertNotNull(correctAnswers);
        assertTrue(correctAnswers.stream().allMatch(a -> a.getQuestionId().getQuestionId().equals(1)));
    }

    private CorrectAnswer createCAnswer(int id, String myAnswer, int qId) {

        Question question1 = new Question();
        question1.setQuestionId(qId);

        CorrectAnswer correctAnswer = new CorrectAnswer();
        correctAnswer.setQuestionId(question1);
        correctAnswer.setAnsId(id);
        correctAnswer.setCorrectAns(myAnswer);
        return correctAnswer;
    }

}

package com.csmtech.service;

import com.csmtech.model.*;
import com.csmtech.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
public class QuestionServiceImplTest {

    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    QuestionServiceImpl questionService;

    Question question = createQuestion(1,1, 1, 1);
    List<Question> questionList = Stream.of(
            createQuestion(1,1, 1, 1),
            createQuestion(2,1, 1, 2),
            createQuestion(3,2, 2, 1)
    ).collect(Collectors.toList());
    List<Integer> ids = questionList.stream()
            .map(Question::getQuestionId).collect(Collectors.toList());
    @BeforeEach
    void setMockOutput() {

        when(questionRepository.findQuestionByQuestionId(1)).thenReturn(question);
        when(questionRepository.findAll()).thenReturn(questionList);
        when(questionRepository.save(Mockito.any(Question.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(questionRepository.getAllQuestionBySubItemId(1)).thenReturn(questionList.stream()
                        .filter(q -> q.getSubItem().getSubItemId().equals(1)).collect(Collectors.toList()));
        when(questionRepository.getAllQuestionBySubItemIdForTest(1,1))
                .thenReturn(questionList.stream().filter(q -> q.getQuestionType().getQuestionTypeId()
                        .equals(1)).collect(Collectors.toList()));
        when(questionRepository.findAllById(ids)).thenReturn(questionList.stream()
                .filter(q->ids.contains(q.getQuestionId())).collect(Collectors.toList()));
        when(questionRepository.saveAll(questionList)).thenReturn(null);
    }

    @DisplayName("Test to save Question")
    @Test
    void givenQuestion_WhenSave_ThenReturnSaved() {

        Question savedQuestion = questionService.saveQuestion(question);
        assertNotNull(savedQuestion);
        assertEquals(1,savedQuestion.getQuestionId());
    }

    @DisplayName("Test to find all Questions")
    @Test
    void givenQuestion_WhenFindAll_ThenReturnList() {

        List<Question> questionList1 = questionService.findAllQuestion();
        assertNotNull(questionList1);
        assertEquals(3,questionList1.size());
    }

    @DisplayName("Test to find by Question id")
    @Test
    void givenQuestionId_WhenFind_ThenReturn() {

        Question question = questionService.findQuestionByQuestionId(1);
        assertNotNull(question);
        assertEquals(1,question.getQuestionId());
    }

    @DisplayName("Test to find by Question sub item id")
    @Test
    void givenQuestionSubItemId_WhenFind_ThenReturn() {

        List<Question> questions = questionService.getAllQuestionBySubItemId(1);
        assertNotNull(questions);
        assertTrue(questions.stream().allMatch(q->q.getSubItem().getSubItemId().equals(1)));
    }

    @DisplayName("Test to find by Question sub item id")
    @Test
    void givenQuestionTypeId_WhenFind_ThenReturn() {

        List<Question> questions = questionService.getAllQuestionByItemId(1,1);
        assertNotNull(questions);
        assertTrue(questions.stream().allMatch(q->q.getQuestionType().getQuestionTypeId().equals(1)));
    }

    @DisplayName("Test to find by Question all id")
    @Test
    void givenAllQuestionId_WhenFind_ThenReturnList() {

        List<Question> questions = questionService.getAllQuestionbyQuestionId(ids);
        assertNotNull(questions);
        assertTrue(questions.stream().anyMatch(q->ids.contains(q.getQuestionId())));
    }

    @Disabled("saveall return void")
    @DisplayName("Test to save all Questions")
    @Test
    void givenAllQuestions_WhenSaved_ThenReturnList() {

        //assertNull(questionService.saveAll(questionList));
       // assertNotNull(questions);
       // assertTrue(questions.stream().anyMatch(q->ids.contains(q.getQuestionId())));
    }

    private Question createQuestion(int id, int itemId, int subItemId, int questionTypeId) {
        Items items = new Items();
        items.setItemId(itemId);

        SubItem subItem = new SubItem();
        subItem.setSubItemId(subItemId);

        QuestionType questionType = new QuestionType();
        questionType.setQuestionTypeId(questionTypeId);

        Question question = new Question();
        question.setQuestionId(id);
        question.setItem(items);
        question.setSubItem(subItem);
        question.setQuestionType(questionType);
        return question;
    }
}

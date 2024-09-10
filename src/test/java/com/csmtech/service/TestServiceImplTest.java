package com.csmtech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.model.Test;
import com.csmtech.repository.TestRepository;

@SpringBootTest
public class TestServiceImplTest {

	@Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestServiceImpl testService = new TestServiceImpl();

    Test test = createTest(1,"Set-1");
    
    List<Test> testList = Stream.of(createTest(1,"Set-one"),
                                createTest(2,"Set-Two")).collect(Collectors.toList());
    
    @BeforeEach
    void setTestMockOutput() {
    	
 
        when(testRepository.findTestNameByTestId(1)).thenReturn(test);
        when(testRepository.save(Mockito.any(Test.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(testRepository.findAll()).thenReturn(testList);
    }
    
    @DisplayName("Test to save Test")
    @org.junit.jupiter.api.Test
    void givenTest_WhenSave_ThenReturnSaved() {
        Test savedTest = testService.saveTest(test);
        assertNotNull(savedTest);
        assertEquals(1,savedTest.getTestId());
    }

    @DisplayName("Test to find Test by TestId")
    @org.junit.jupiter.api.Test
    void givenTestId_WhenFind_ThenReturn() {
        Test foundTest = testService.findTestNameByTestId(1);
        assertNotNull(foundTest);
        assertEquals(1,foundTest.getTestId());
    }

    @DisplayName("Test to find all Items")
    @org.junit.jupiter.api.Test
    void givenAllItem_WhenFound_ThenReturnList() {
        List<Test> testListFound = testService.getAllTest();
        assertNotNull(testListFound);
        assertEquals(2,testListFound.size());
    }
    
	private Test createTest(int testId, String testName) {
		Test test = new Test();
		test.setTestId(testId);
		test.setTestName(testName);
		return test;
	}
	
}

package com.csmtech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.csmtech.model.SubTest;
import com.csmtech.model.Test;
import com.csmtech.repository.SubTestRepository;

@SpringBootTest
public class SubtestServiceImplTest {
	
	@Mock
	private SubTestRepository subTestRepository;
	
	@InjectMocks
    private SubTestServiceImpl subTestService = new SubTestServiceImpl();
	
	SubTest subTest = new SubTest();
	Test test = new Test();
	
	
	SubTest subTests = createSubTest(1,"SubTest-1",1,"Test-1","byTest");
    List<SubTest> subTestList = Stream.of(createSubTest(2,".Net Core",2,".Net","Objective"),
    		createSubTest(3,"Adv Java",2,".Net","Objective")).collect(Collectors.toList());
	
    @BeforeEach
    void setSubTestMockOutput() {
    	
    	subTest.setSubTestId(1);
    	subTest.setSubTestName("Subtest-y");
    	subTest.setPropertyType("byItem");
    	test.setTestId(1);
    	test.setTestName("Test-y");
    	subTest.setTest(test);
    	
        when(subTestRepository.getAllSubTestByTestId(1)).thenReturn(subTestList);
        when(subTestRepository.findBySubTestId(1)).thenReturn(subTest);
        when(subTestRepository.findAll()).thenReturn(subTestList);
        when(subTestRepository.save(Mockito.any(SubTest.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }


	@DisplayName("Test to save SubTest")
    @org.junit.jupiter.api.Test
    void givenSubTest_WhenSave_ThenReturnSaved() {
		SubTest savedsubtest = subTestService.saveSubTest(subTests);
        assertNotNull(savedsubtest);
        assertEquals(1,savedsubtest.getSubTestId());
    }
    
    @DisplayName("Test to Find SubTest bytestId")
    @org.junit.jupiter.api.Test
    void givenTestId_WhenFound_ThenReturnSubTest() {
    	
    	List<SubTest> subTestList = subTestService.getAllSubTestByTestId(1);
    	assertNotNull(subTestList);
        assertFalse(subTestList.isEmpty());
        assertEquals(2,subTestList.size());
    	
    }
    
    @DisplayName("Test to Find SubTest by subTestId")
    @org.junit.jupiter.api.Test
    void givenSubTestId_WhenFound_ThenReturnSubTest() {
    	SubTest foundSubTest = subTestService.findSubTest(1);
    	assertNotNull(foundSubTest);
    	assertEquals(1,foundSubTest.getSubTestId());
    }
   
    @org.junit.jupiter.api.Test 
    void FoundAllSubItem_ReturnSubItem() {
    	List<SubTest> allSubtest = subTestService.findAll();
    	assertNotNull(allSubtest);
        assertFalse(allSubtest.isEmpty());
        assertEquals(2,allSubtest.size());
    	
    }
    
    private SubTest createSubTest(int subtestId, String subtestName, int testId, String testName, String propertyName) {
		SubTest sTest = new SubTest();
		sTest.setSubTestId(subtestId);
		sTest.setSubTestName(subtestName);
		
		Test t = new Test();
		t.setTestId(testId);
		t.setTestName(testName);
		sTest.setTest(t);
		
		sTest.setPropertyType(propertyName);
		
		return sTest;
	}

}

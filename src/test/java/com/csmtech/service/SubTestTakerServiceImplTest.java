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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.model.SubTestTaker;
import com.csmtech.model.TestTaker;
import com.csmtech.repository.SubTestTakerRepository;

@SpringBootTest
public class SubTestTakerServiceImplTest {
	
	@Mock
    private SubTestTakerRepository subTestTakerRepository;

    @InjectMocks
    private SubTestTakerService subTestTakerService = new SubTestTakerServiceImpl();
    
    SubTestTaker subtestTaker = createSubtestTaker(1,"Batch-1",1,"Trident College","bbsr","Saroj Mohanty","7657657657","sm12@gmail.com","No");

    List<SubTestTaker> listSubTestTaker = Stream.of(createSubtestTaker(1,"Batch-1",1,"Trident College","bbsr","Saroj Mohanty","7657657657","sm12@gmail.com","No"),
    createSubtestTaker(2,"Batch-1",2,"GIET College","bbsr","Suman Mohanty","7657657657","suman12@gmail.com","No")).collect(Collectors.toList());
    
	
    @BeforeEach
    void setTestTakerMockOutput() {

        when(subTestTakerRepository.getSubTestTaker(subtestTaker.getSubTestTakerId())).thenReturn(subtestTaker);
        when(subTestTakerRepository.getAllSubTestTakerByTestTakerId(1)).thenReturn(listSubTestTaker);
        when(subTestTakerRepository.findAll()).thenReturn(listSubTestTaker);
        when(subTestTakerRepository.findSubTestTakerNameBySubTestTakerId(subtestTaker.getSubTestTakerId())).thenReturn(subtestTaker);
        //when(userRepository.save(any(User.class))).thenReturn(user);
        when(subTestTakerRepository.save(Mockito.any(SubTestTaker.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }
    
    @Test
    @DisplayName("Test to Save TestTaker")
    void givenSubTestTaker_WhenSave_ThenReturnSaved() {
    	
    	SubTestTaker subTestTakerSaved  = subTestTakerService.save(subtestTaker);
    	 assertNotNull(subTestTakerSaved);
         assertEquals(1,subTestTakerSaved.getSubTestTakerId());

    	
    }
    
    @Test
    @DisplayName("Test to get All SubtestTaker by testTakerId")
    void givenTestTakerId_whenFound_ThenReturnSubtestTaker() {
    	
    	List<SubTestTaker> listOfSubtestTaker = subTestTakerService.getAllSubTestTakerByTestTakerId(1);
    	assertNotNull(listOfSubtestTaker);
        assertEquals(2,listOfSubtestTaker.size());
    }
    
    @Test
    @DisplayName("Test to find SubTestTaker by subtestTakerId")
    void givenSubtestTakerId_whenFound_ThenReturnSubTestTaker() {
    	
    	SubTestTaker findSubTestTaker = subTestTakerService.getSubTestTaker(subtestTaker.getSubTestTakerId());
    	assertNotNull(findSubTestTaker);
        assertEquals(1,findSubTestTaker.getSubTestTakerId());
    }
    
    @Test
    @DisplayName("Test to get All SubtestTaker")
    void givenSubTestTaker_WhenFoundAll_ThenReturnAll() {
    	 List<SubTestTaker> subtestTaker = subTestTakerService.getAllSubTestTaker();
         assertNotNull(subtestTaker);
         assertEquals(2,subtestTaker.size());
         assertFalse(subtestTaker.stream().anyMatch(x->x.getSubTestTakerName().isEmpty()));
    }
    
    private SubTestTaker createSubtestTaker(int subTestTakerId, String subTestTakerName, int testTakerId, String testTakerName, String collegeAddress,String placementOfficer , String phoneNumber,
			String officerEmail, String isDeleted) {
		
		SubTestTaker subtestTaker = new SubTestTaker();
		TestTaker testTaker = new TestTaker();
		
		testTaker.setTestTakerId(testTakerId);
		testTaker.setTestTakerName(testTakerName);
		testTaker.setCollegeAddress(collegeAddress);
		testTaker.setPlacementOfficer(placementOfficer);
		testTaker.setPhoneNumber(phoneNumber);
		testTaker.setOfficerEmail(officerEmail);
		testTaker.setIsDeleted(isDeleted);
		
		subtestTaker.setSubTestTakerId(subTestTakerId);
		subtestTaker.setSubTestTakerName(subTestTakerName);

		return subtestTaker;
	}

}

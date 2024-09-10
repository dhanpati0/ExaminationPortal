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

import com.csmtech.model.TestTaker;
import com.csmtech.model.User;
import com.csmtech.repository.TestTakerRepository;

@SpringBootTest
public class TestTakerServiceImplTest {
	
	@Mock
	private TestTakerRepository testTakerRepository;
	
	@InjectMocks
    private TestTakerService testTakerService = new TestTakerServiceImpl();
	
	TestTaker testTaker = createTestTaker(1,"Trident College","bbsr","Saroj Mohanty","7657657657","sm12@gmail.com","No");
	
	List<TestTaker> testTakerList = Stream.of(createTestTaker(1,"Trident College","bbsr","Saroj Mohanty","7657657657","sm12@gmail.com","No"),
			createTestTaker(2,"GIET College","bbsr","Suman Mohanty","7657657657","suman12@gmail.com","No")).collect(Collectors.toList());
	
	@BeforeEach
    void setTestTakerMockOutput() {

        when(testTakerRepository.findTestTakerById(1)).thenReturn(testTaker);
        when(testTakerRepository.findTestTakerNameByTestTakerId(1)).thenReturn(testTaker);
        when(testTakerRepository.getById(1)).thenReturn(testTaker);
        when(testTakerRepository.getEmailIdByTestTakerId(1)).thenReturn(testTaker.getOfficerEmail());
        when(testTakerRepository.getAllCollege()).thenReturn(testTakerList);
        when(testTakerRepository.getIdByName("Trident College")).thenReturn(testTaker.getTestTakerId());
        
        //when(userRepository.save(any(User.class))).thenReturn(user);
        when(testTakerRepository.save(Mockito.any(TestTaker.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }
	
	@Test
	@DisplayName("test To save TestTaker")
	void givenTestTaker_WhenSaved_ThenReturnTestTaker() {
		
		TestTaker saveTestTaker = testTakerService.save(testTaker);
		assertNotNull(saveTestTaker);
        assertEquals(1,saveTestTaker.getTestTakerId());
		
	}
	
	@Test
	@DisplayName("test To find TestTaker by its Id")
	void givenTestTakerId_WhenFound_ThenReturnTestTaker() {
		TestTaker foundTestTaker = testTakerService.getTestTakerName(testTaker.getTestTakerId());
		assertNotNull(foundTestTaker);
        assertEquals(1,foundTestTaker.getTestTakerId());
	}
		
	@Test
	@DisplayName("test To find officer email by its Id")
	void givenTestTakerId_WhenFound_ThenReturnOfficerEmail() {
		String findTestTaker = testTakerService.getEmailIdByTestTakerId(testTaker.getTestTakerId());
		assertNotNull(findTestTaker);
        assertEquals("sm12@gmail.com",findTestTaker);
	}
	
	
	@DisplayName("Test to get all testTakers")
    @Test
    void givenTestTaker_WhenFoundAll_ThenReturnAll() {
        List<TestTaker> testTakers = testTakerService.getAllCollege();
        assertNotNull(testTakers);
        assertEquals(2,testTakers.size());
        assertFalse(testTakers.stream().anyMatch(x->x.getTestTakerName().isEmpty()));
        assertFalse(testTakers.stream().anyMatch(x->x.getOfficerEmail().isEmpty()));
        assertFalse(testTakers.stream().anyMatch(x->x.getPhoneNumber().isEmpty()));
    } 
	
	@DisplayName("Test to get testTakerId by testTakerName")
    @Test
	void givenTestTakerName_WhenFound_ThenReturnTestTakerId() {
		Integer testTakerId = testTakerService.getIdByName(testTaker.getTestTakerName());
		assertNotNull(testTakerId);
		assertEquals(1,testTakerId);
	}
	private TestTaker createTestTaker(int testTakerId, String testTakerName, String collegeAddress,String placementOfficer , String phoneNumber,
			String officerEmail, String isDeleted) {
		
		TestTaker testTaker = new TestTaker();
		testTaker.setTestTakerId(testTakerId);
		testTaker.setTestTakerName(testTakerName);
		testTaker.setCollegeAddress(collegeAddress);
		testTaker.setPlacementOfficer(placementOfficer);
		testTaker.setPhoneNumber(phoneNumber);
		testTaker.setOfficerEmail(officerEmail);
		testTaker.setIsDeleted(isDeleted);
		
		return testTaker;
	}

}

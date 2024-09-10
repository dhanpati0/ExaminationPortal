package com.csmtech.repository;

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
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTakerRepositoryTest {

    @Autowired
    TestTakerRepository testTakerRepository;
    
    @DisplayName("To save Test")
    @Order(1)
    @Test
	public void givenTestTakerObject_whenSave_thenReturnSavedTestTaker() {
    	
    	TestTaker testTaker = new TestTaker();
    	testTaker.setTestTakerId(1);
    	testTaker.setTestTakerName("Gita Campus");
    	testTaker.setPhoneNumber("7898328973");
    	testTaker.setOfficerEmail("ki23@gmail.com");
    	testTaker.setPlacementOfficer("pk singh");
    	testTaker.setIsDeleted(null);
    	testTaker.setCollegeAddress("bbsr");
    	
    	TestTaker savetestTaker = testTakerRepository.save(testTaker);
        System.out.println(savetestTaker);
		assertThat(savetestTaker).isNotNull();
	    assertThat(savetestTaker.getTestTakerId()).isGreaterThan(0);
    }

    @Order(2)
    @DisplayName("Test to find Test taker by Id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenId_WhenFound_Return(int id){
        //given Id as value parameter

        //When found record with Id
        TestTaker testTaker = testTakerRepository.findTestTakerNameByTestTakerId(id);

        //Then Verify Id
        assertNotNull(testTaker);
        assertEquals(1,testTaker.getTestTakerId());
    }

    @Order(3)
    @DisplayName("Test to find all Test taker")
    @Test
    public void givenTestTaker_WhenFound_ThenReturnAll(){
        //given Test Taker

        //When found record with Id
        List<TestTaker> testTakers = testTakerRepository.getAllCollege();

        //Then Verify Id
        assertNotNull(testTakers);
        assertThat(testTakers.isEmpty());
    }

    @Order(4)
    @DisplayName("Test to find Officer email from Test taker")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenTestTakerId_WhenFound_ThenReturnOfficerEmail(int id){
        //given Test Taker Id

        //When found record with Id
        String officerEmail = testTakerRepository.getEmailIdByTestTakerId(id);

        //Then Verify Id
        assertNotNull(officerEmail);
        assertFalse(officerEmail.isEmpty());
        assertEquals("ki23@gmail.com",officerEmail);
    }

    @Order(5)
    @DisplayName("Test to find id of Test taker by name")
    @ParameterizedTest
    @ValueSource(strings =  { "Gita Campus" })
    public void givenName_WhenFound_ReturnId(String name){

        //given Test Taker Name

        //When found record with Name
        int id = testTakerRepository.getIdByName(name);

        //Then Verify
        assertNotEquals(0, id);

    }


}

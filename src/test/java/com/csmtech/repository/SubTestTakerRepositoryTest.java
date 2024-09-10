package com.csmtech.repository;

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
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubTestTakerRepositoryTest {

    @Autowired
    SubTestTakerRepository subTestTakerRepository;
    
    @DisplayName("To save SubtestTaker")
    @Order(1)
    @Test
	public void givenSubTestTakerObject_whenSave_thenReturnSavedSubTestTaker() {
    	
    	TestTaker testTaker = new TestTaker();
    	testTaker.setTestTakerId(1);
    	testTaker.setTestTakerName("hig");
    	testTaker.setPhoneNumber("7898328973");
    	testTaker.setOfficerEmail("ki23@gmail.com");
    	testTaker.setPlacementOfficer("pk singh");
    	testTaker.setIsDeleted(null);
    	testTaker.setCollegeAddress("bbsr");
    	
    	SubTestTaker subTestTaker= new SubTestTaker();
    	subTestTaker.setSubTestTakerId(1);
    	subTestTaker.setSubTestTakerName("batch-1");
    	subTestTaker.setTestTaker(testTaker);
    	
    	SubTestTaker savesubTestTaker = subTestTakerRepository.save(subTestTaker);
        System.out.println(savesubTestTaker);
		assertThat(savesubTestTaker).isNotNull();
	    assertThat(savesubTestTaker.getSubTestTakerId()).isGreaterThan(0);
    }

    @Order(2)
    @DisplayName("Test to find Sub test taker By Id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenId_WhenFound_ThenReturn(int id){
        //Given sub test taker Id as ValueSource

        //When find the sub test taker by Id
        //SubTestTaker subTestTaker =subTestTakerRepository.getSubTestTaker(id);
        SubTestTaker subTestTaker =subTestTakerRepository
                .findSubTestTakerNameBySubTestTakerId(id);

        //Then verify
        assertNotNull(subTestTaker);
        assertEquals(1,subTestTaker.getSubTestTakerId());
    }

    @Order(3)
    @DisplayName("Test to find All Sub test taker")
    @Test
    public void givenAll_WhenFound_ThenReturnList(){
        //Given sub test taker

        //When find the sub test taker by Id
        List<SubTestTaker> subTestTakers = subTestTakerRepository.findAll();

        //Then verify
        assertNotNull(subTestTakers);
        assertFalse(subTestTakers.isEmpty());
    }

    @Order(4)
    @DisplayName("Test to find All Sub test taker by test taker id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenTestTakerId_WhenFound_ThenReturnList(int id){
        //Given test taker id

        //When find the sub test taker by Test taker Id
        List<SubTestTaker> subTestTakers = subTestTakerRepository
                .getAllSubTestTakerByTestTakerId(id);

        //Then verify Test Taker Id
        assertNotNull(subTestTakers);
        assertFalse(subTestTakers.isEmpty());
        assertEquals(1, subTestTakers.get(0).getTestTaker().getTestTakerId());
    }

}

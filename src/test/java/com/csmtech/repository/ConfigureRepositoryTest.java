package com.csmtech.repository;

import com.csmtech.model.Configure;
import com.csmtech.model.Items;
import com.csmtech.model.SubTest;
import com.csmtech.model.SubTestTaker;
import com.csmtech.model.TestTaker;

import org.junit.jupiter.api.Disabled;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConfigureRepositoryTest {

    @Autowired
    ConfigureRepository configureRepository;
    
    
    @Test
    @Order(1)
    @DisplayName("for save Configure")
    public void givenConfigureObject_whenSaveConfigure_ThenReturnConfigure() {
    	
        com.csmtech.model.Test test= new com.csmtech.model.Test();
		
		SubTest subtest = new SubTest();
		subtest.setSubTestId(1);
		subtest.setTest(test);
		subtest.setSubTestName("sub-set-1");
		
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
		
    	
    	Configure config = new Configure();
    	
    	config.setConfigId(1); 
    	config.setEndTime(null);
    	config.setStartTime(null);
    	config.setLoginTime(null);
    	config.setEnterNoQuestion(9);
    	config.setSubTest(subtest);
    	config.setSubTestTaker(subTestTaker);
    	config.setTestDate(null);
    	config.setTestDate(null);
    	Configure saveconfig = configureRepository.save(config);
    	
    	assertThat(saveconfig).isNotNull();
    	assertThat(saveconfig.getConfigId()).isGreaterThan(0);
    	
    }

    // TODO: 08-11-2023 getting query Exception
    @Order(2)
    @Disabled
    @DisplayName("Finding config by subtest tacker id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenSubTestTakerId_WhenFound_ThenReturnConfig(int id){
        //Given sub test taker id as ValueSource

        //When found confifure by taker id
        Configure configure = configureRepository.findConfigureBySubTestTakerId(id);

        //Then verify config
        assertNotNull(configure);
        assertNotEquals(0, (int) configure.getConfigId());
        assertEquals(1,configure.getConfigId());
    }

    @Order(3)
    @DisplayName("Find details by sub test taker id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenSubTestTakerId_WhenFoundConfig_ThenReturnDetails(int id){

        //Given sub test taker Id as ValueSource

        //when id found in records
        System.out.println("findDetailsTest");
        Configure configure = configureRepository.findDetails(id);

        //Then verify
        assertEquals(1,configure.getSubTestTaker().getSubTestTakerId());
        assertNotNull(configure);
    }
}

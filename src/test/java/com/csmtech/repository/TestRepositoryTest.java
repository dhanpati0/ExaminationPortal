package com.csmtech.repository;

import com.csmtech.model.Test;
import com.csmtech.model.User;

import org.hibernate.internal.build.AllowSysOut;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
public class TestRepositoryTest {

    @Autowired
    TestRepository testRepository;
    
    @DisplayName("To save testTaker")
    @Order(1)
    @org.junit.jupiter.api.Test
	public void givenUserObject_whenSave_thenReturnSavedUser() {
    	
    	Test test = new Test();
    	test.setTestId(2);
    	test.setTestName("SET-2");
    	
        Test savetest = testRepository.save(test);
        System.out.println(savetest);
		assertThat(savetest).isNotNull();
	    assertThat(savetest.getTestId()).isGreaterThan(0);
    }

    @Order(2)
    @DisplayName("Test to Find Test By Id")
    @org.junit.jupiter.api.Test
    public void givenId_WhenFound_ThenReturn(){
        //Given id as value source
    	
        //When found
        Test test = testRepository.findTestNameByTestId(1);
        System.out.println("///"+test);
        //Then validate
        assertThat(test).isNotNull();
        assertEquals(1,test.getTestId());
    }

    @Order(3)
    @DisplayName("Test to Find All Test")
    @org.junit.jupiter.api.Test
    public void givenTest_WhenFound_ThenReturnList(){
        //Given All

        //When found
        List<Test> test = testRepository.findAll();

        //Then validate
        assertNotNull(test);
        assertFalse(test.isEmpty());
    }
}

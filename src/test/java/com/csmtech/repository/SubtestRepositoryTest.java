package com.csmtech.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.model.SubTest;
import com.csmtech.model.Test;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubtestRepositoryTest {
	
	@Autowired
	private SubTestRepository subtestRepository;
	
	@org.junit.jupiter.api.Test
	@DisplayName("save subtest")
	@Order(1)
	public void givenSubtestObject_whensave_ThenReturn() {
		
		Test test= new Test();
		
		SubTest subtest = new SubTest();
		subtest.setSubTestId(1);
		subtest.setTest(test);
		subtest.setSubTestName("sub-set-1");
		
		SubTest subt = subtestRepository.save(subtest);
		
		assertThat(subt).isNotNull();
	    assertThat(subt.getSubTestId()).isGreaterThan(0);
	}

	@Order(2)
	@org.junit.jupiter.api.Test
	@DisplayName("Find SubtestName by subtestId")
	public void givenSubtest_whensubtestId_thenreturnSubtestName() {
		
		 SubTest subtest = subtestRepository.findBySubTestId(1);
	        System.out.println("///"+subtest);
	        //Then validate
	        assertThat(subtest).isNotNull();
	        assertEquals(1,subtest.getSubTestId());
	}
	
	@Order(3)
	@org.junit.jupiter.api.Test
	@DisplayName("getALL Subtest BytestId")
	public void givenByTestId_whenFindSubtest_thengetAllSubtest() {
		List<SubTest> stest = subtestRepository.getAllSubTestByTestId(1);
		
		assertThat(stest).isNotNull();
		assertThat(stest.size()).isGreaterThan(0);
	}
}

package com.csmtech.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.model.Role;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class RoleRepositoryTests {
	
	@Autowired
	private RoleRepository roleRepository;
	
	Role role;
	
	
	@BeforeEach
	void setUp(){
		role = new Role(); 
		role.setRoleId(2);
		role.setRoleName("Proctor");
		
		roleRepository.save(role);
	}
	
	@AfterEach
	void tearDown() {
		role = null;
		roleRepository.deleteAll();
	}

	
	
	//Junit test for findUserByUsernameAndPassword operation
	@DisplayName("Junit test for role save operation")
	@Test
	public void saveRole() {

		// given - precondition or setup 
		Role role = new Role(); 
		role.setRoleId(2);
		role.setRoleName("Proctor");

		// when - action or the behaviour that we are going to test //Candidate
		//saveCandidate = candidateRepository.save(candidate);
		Role saveRole = roleRepository.save(role);

		// then - varify the output

		assertThat(saveRole).isNotNull();
		assertThat(saveRole.getRoleId()).isGreaterThan(0);

	}
	

}

package com.csmtech.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.model.Role;
import com.csmtech.model.User;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Order(1)
	@DisplayName("Junit test for save user operation")
	@Test
	public void givenUserObject_whenSave_thenReturnSavedUser() {
		
		Role role = new Role();
		role.setRoleId(2);
		role.setRoleName("Proctor");
		//given - precondition or setup
		User user = new User();
		user.setUserId(1);
		user.setName("Sunil");
		user.setGender("male");
		user.setEmail("s123@gmail.com");
		user.setMobileNo("897899271");
		user.setIsDelete("No");
		user.setPassword("Sunil@#");
		user.setRole(role);
		user.setUserAddress("bbsr");
		user.setUsername("Proctor");
		user.setStatus("0");
		
		//when - action or the behaviour that we are going to test
		User saveUser = userRepository.save(user);
		
		//then - varify the output
		assertThat(saveUser).isNotNull();
	    assertThat(saveUser.getUserId()).isGreaterThan(0);
		
	}
	
	//Junit test for findUserByUsernameAndPassword operation
	@Order(2)
	@DisplayName("Junit test for findUserByUsernameAndPassword operation")
	@Test
	public void givenUsernameWhenfindUserByUsernameAndPasswordThenCheck(){
		
		User foundUser =userRepository.findUserByUsernameAndPassword("Proctor","Sunil@#");
		
		assertThat(foundUser).isNotNull();
		assertThat(foundUser.getUsername()).isEqualTo("Proctor");
        assertThat(foundUser.getPassword()).isEqualTo("Sunil@#");
	}

	@Order(3)
	@DisplayName("Junit test for getRoleIdByUsernameAndPassword operation")
	@Test
	public void givenRoleIdWhengetRoleIdByUsernameAndPasswordThenCheck() {
		int userRoleId = userRepository.getRoleIdByUsernameAndPassword("Proctor","Sunil@#");
		System.out.println(userRoleId+"/////////////");
		assertThat(userRoleId).isNotNull();
		assertThat(userRoleId).isEqualTo(1);
		
	}
	
	@Order(4)
	@DisplayName("Junit test for isNotDelated operation")
	@Test
	public void givenIsDeletedWhengetAllUserNotDeletedThenCheck() {
		List<User> users = userRepository.getAllUserNotDeleted();
		
		assertThat(users).isNotNull();
		assertThat(users).hasSizeGreaterThan(0);
		assertEquals(1, users.size());
	}
	
	@Order(5)
	@DisplayName("Junit test for findUser operation")
	@Test
	public void givenUserIdWhenFindUserThenDetailsById() {
		
		User userDetails = userRepository.findUserDetailsById(1);
		
		assertThat(userDetails).isNotNull();
		assertEquals(1, userDetails.getUserId());
	}
	
}


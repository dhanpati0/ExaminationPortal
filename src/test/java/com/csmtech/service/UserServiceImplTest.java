package com.csmtech.service;

import com.csmtech.model.Role;
import com.csmtech.model.User;
import com.csmtech.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();
    User user = new User();
    Role role = new Role();
    
    List<User> users = Stream.of(
                    createUser(1,"Ram1","ram1231","pass1","1231231","Male",
                            "asd1@qwe.com","active","Bhubnsr1",1,"Admin"),
                    createUser(2,"Ram2","ram1232","pass2","1231232","Male",
                            "asd2@qwe.com","active","Bhubnsr2",2,"Dev"),
                    createUser(3,"Ram3","ram1233","pass3","1231233","Male",
                            "asd3@qwe.com","active","Bhubnsr3",3,"HR"))
            .collect(Collectors.toList());
    //user.setName("santhosh");

    @BeforeEach
    void setUserMockOutput() {
        user.setUserId(Integer.parseInt("5"));
        user.setName("Alwala");
        user.setUsername("santhosh");
        user.setPassword("userName" + "@#");
        user.setEmail("email");
        user.setMobileNo("122321323");
        user.setGender("Male");
        user.setUserAddress("OCAC");
            role.setRoleId(1);
            role.setRoleName("Admin");
        user.setRole(role);
        user.setIsDelete("No");
        user.setStatus("0");

        when(userRepository.findUserDetailsById(5)).thenReturn(user);
        when(userRepository.getRoleIdByUsernameAndPassword("santhosh","userName@#"))
                .thenReturn(user.getRole().getRoleId());
        when(userRepository.findUserByUsernameAndPasswordForCheck("santhosh","userName@#"))
                .thenReturn(user);
        when(userRepository.getAllUserNotDeleted()).thenReturn(users);
        //when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @DisplayName("Test to save User")
    @Test
    void givenUser_WhenSave_ThenReturnSaved() {
        User savedUser = userService.saveDetailsOfUser(user);
        assertNotNull(savedUser);
        assertEquals(5,savedUser.getUserId());
    }

    @DisplayName("Test to find role id")
    @Test
    void givenUserNameAndPass_WhenFind_ThenReturnRoleId() {
        int id = userService.findRoleIdByUsernameAndPassword(user.getUsername(),user.getPassword());
        assertEquals(1,id);
    }

    @DisplayName("Test to find user by user name and password")
    @Test
    void givenUsernameAndPassword_WhenFind_thenReturnUser() {
            User user = userService.findUserByUsernameAndPasswordForCheck("santhosh","userName@#");
        assertNotNull(user);
        assertTrue(user.getUsername().equalsIgnoreCase("santhosh"));
        assertTrue(user.getPassword().equalsIgnoreCase("userName@#"));
    }

    @DisplayName("Test to get all users")
    @Test
    void givenUser_WhenFoundAll_ThenReturnAll() {
        List<User> users = userService.getAllUser();
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(3,users.size());
        assertFalse(users.stream().anyMatch(x->x.getUsername().isEmpty()));
        assertFalse(users.stream().anyMatch(x->x.getEmail().isEmpty()));
        assertFalse(users.stream().anyMatch(x->x.getMobileNo().isEmpty()));
    }

    private User createUser(int id,String name,String un,String pass,String mob,
                            String gen,String email,String status,String adr,int roleId,String roleName){
        User userCreated = new User();
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        userCreated.setUserId(id);
        userCreated.setName(name);
        userCreated.setUsername(un);
        userCreated.setPassword(pass);
        userCreated.setMobileNo(mob);
        userCreated.setGender(gen);
        userCreated.setEmail(email);
        userCreated.setStatus(status);
        userCreated.setUserAddress(adr);
        userCreated.setRole(role);
        return userCreated;
    }
}
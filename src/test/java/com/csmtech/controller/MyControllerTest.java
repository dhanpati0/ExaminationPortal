package com.csmtech.controller;

import com.csmtech.model.*;
import com.csmtech.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyControllerTest {
    @InjectMocks
    MyController myController;

    @Mock
    UserService userService;

    @Mock
    private HttpSession httpSession;

    @Mock
    private RedirectAttributes mockRedirectAttributes;

    

    @Mock
    RoleService roleService;

    @Mock
    private CandidateService candService;

    @Mock
    private SubTestTakerService subTestTakerService;

    @Mock
    private TestTakerService testTakerService;

    User user = createUser(1,"Ram","ram123","pass","123123","Male",
            "asd@qwe.com","active","Bhubnsr",1,"Admin");

    List<User> users = Stream.of(
                    createUser(1,"Ram1","ram1231","pass1","1231231","Male",
                            "asd1@qwe.com","active","Bhubnsr1",1,"Admin"),
                    createUser(2,"Ram2","ram1232","pass2","1231232","Male",
                            "asd2@qwe.com","active","Bhubnsr2",2,"Dev"),
                    createUser(3,"Ram3","ram1233","pass3","1231233","Male",
                            "asd3@qwe.com","active","Bhubnsr3",3,"HR"),
                    createUser(4, "John", "john123", "pass4", "1231234", "Male",
                                       "john@example.com", "active", "New York", 4, "Manager"),
                    createUser(5, "Jane", "jane123", "pass5", "1231235", "Female",
                                       "jane@example.com", "active", "Los Angeles", 5, "Engineer"),
                    createUser(6, "Alice", "alice123", "pass6", "1231236", "Female",
                                       "alice@example.com", "active", "London", 6, "Analyst"),
                    createUser(7, "Bob", "bob123", "pass7", "1231237", "Male",
                                       "bob@example.com", "active", "Berlin", 7, "Developer"),
                    createUser(8, "Eva", "eva123", "pass8", "1231238", "Female",
                                       "eva@example.com", "active", "Paris", 8, "Tester"),
                    createUser(9, "Mike", "mike123", "pass9", "1231239", "Male",
                                       "mike@example.com", "active", "Tokyo", 9, "Support"))
            .collect(Collectors.toList());

    @Test
    public void getUserTest()
    {
        when(userService.findUserDetailsById(1)).thenReturn(user);

        User userFound = myController.getUser(1);

        assertEquals(1,user.getUserId());
        assertEquals(user.getUsername(),userFound.getUsername());
    }

    @Test
    public void addCandPageTest(){
        when(httpSession.getAttribute("sessionData")).thenReturn(user);
       
        List<Candidate> mockCandidates = new ArrayList<>();
        when(candService.findAllCandidate()).thenReturn(mockCandidates);

        Model mockModel = mock(Model.class);

        String result = myController.addCandPage(mockModel);

        verify(mockModel).addAttribute("username", user.getName());
        
        verify(mockModel).addAttribute("allCandidate", mockCandidates);

        assertEquals("admin/addCandidate", result);
    }

    @Test
    public void getLogoutTest(){
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        String result = myController.getLogout(mockResponse);

        verify(httpSession).removeAttribute("sessionData");
        verify(httpSession).removeAttribute("savesubItem");

        assertEquals("no-cache, no-store, must-revalidate", mockResponse.getHeader("Cache-Control"));
        assertEquals("no-cache", mockResponse.getHeader("Pragma"));
        assertEquals("0", mockResponse.getHeader("Expires"));

        assertEquals("redirect:./login?logout=true", result);
    }
    @Test
    public void getLogout1Test(){
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        String result = myController.getLogoutForCompletion(mockResponse);

        verify(httpSession).removeAttribute("sessionData");
        verify(httpSession).removeAttribute("savesubItem");

        assertEquals("no-cache, no-store, must-revalidate", mockResponse.getHeader("Cache-Control"));
        assertEquals("no-cache", mockResponse.getHeader("Pragma"));
        assertEquals("0", mockResponse.getHeader("Expires"));

        assertEquals("redirect:./login?logout=true", result);
    }

    @Test
    public void adminDashboardTest(){
        when(httpSession.getAttribute("sessionData")).thenReturn(user);

        Model mockModel = mock(Model.class);

        RedirectAttributes mockRedirectAttributes = mock(RedirectAttributes.class);

        String result = myController.adminDashboard(mockModel, mockRedirectAttributes);

        verify(mockModel).addAttribute("username", user.getName());

        assertEquals("admin/adminDashboard", result);
    }

    @Test
    public void updateStudentFormTest(){
        Integer candid = 1;

        Candidate mockCandidate = new Candidate();
        when(candService.updateCandidateById(candid)).thenReturn(mockCandidate);

        Model mockModel = mock(Model.class);

        String result = myController.updateStudentForm(mockModel, candid);

        verify(mockModel).addAttribute("cand", mockCandidate);

        assertEquals("forward:/exam/addCandidate", result);
    }

    @Test
    public void manageUserTest(){
        List<Role> mockRoles = new ArrayList<>();
        when(roleService.findAllRole()).thenReturn(mockRoles);

        when(httpSession.getAttribute("sessionData")).thenReturn(user);

        List<User> mockUsers = new ArrayList<>();
        when(userService.getAllUser()).thenReturn(mockUsers);

        Model mockModel = mock(Model.class);

        String result = myController.manageUser(mockModel);

        verify(mockModel).addAttribute("roleList", mockRoles);
        verify(mockModel).addAttribute("username", user.getName());
        verify(mockModel).addAttribute("allUser", mockUsers);

        assertEquals("admin/userManage", result);
    }

    @Test
    public void addUserTest() throws Exception{
        Integer userId = 1;
        String name = "Santhosh";
        String userName = "SanthoshAlwala";
        String email = "santu@one.com";
        String mobileNo = "1234567890";
        String gender = "Male";
        String address = "Rajvatika";
        Role userRole = new Role();

        when(userService.getAllUser()).thenReturn(new ArrayList<>());
        Model mockModel = mock(Model.class);
        RedirectAttributes mockRedirectAttributes = mock(RedirectAttributes.class);
        String result = myController.addUser(userId, name, userName, email, mobileNo, gender, address, userRole, mockModel, mockRedirectAttributes);

        verify(userService).saveDetailsOfUser(Mockito.any(User.class));

        verify(mockRedirectAttributes).addFlashAttribute("userAdded", userName);

        assertEquals("redirect:./manageUsers", result);
    }

    @Test
    public void deleteUserById(){
        Integer userId = 10;

        String result = myController.deleteUserById(userId, mockRedirectAttributes);

        verify(userService).deleteUserById(userId);

        verify(mockRedirectAttributes).addFlashAttribute("delete", "delete");

        assertEquals("redirect:./manageUsers", result);
    }

    @Test
    public void updateUserTest() {
        Integer userId = 1;

        when(userService.findUserDetailsById(userId)).thenReturn(user);

        String result = myController.updateUser(userId, mockRedirectAttributes, mock(Model.class));

        verify(userService).findUserDetailsById(userId);

        verify(mockRedirectAttributes).addFlashAttribute("us", user);

        assertEquals("redirect:./manageUsers", result);
    }

    @Test
    void StartExamTest() {

        String result = myController.startExam();

        assertEquals("candidate/questions", result);
    }

    @Test
    void getSubByIdTest() throws IOException {
        SubTestTaker subTestTaker = new SubTestTaker();
        subTestTaker.setSubTestTakerId(1);
        subTestTaker.setSubTestTakerName("SubTestTaker1");
        when(subTestTakerService.getAllSubTestTakerByTestTakerId(1))
                .thenReturn(Collections.singletonList(subTestTaker));

        //myController.setSubTestTakerService(subTestTakerService);

        MockHttpServletResponse response = new MockHttpServletResponse();
        myController.searchTest(1, response);

        String expectedResponse = "<option value='0'>--select--</option><option value=1>SubTestTaker1</option>";
        assertEquals(expectedResponse, response.getContentAsString());
    }

    @Test
    void SearchTestTest() {

        Model model = mock(Model.class);
        RedirectAttributes rd = mock(RedirectAttributes.class);

        String result = myController.searchTest(1, 2, model, rd);

        assertEquals("redirect:./result", result);
    }

    @Test
    void viewResultTest() {
        Model model = mock(Model.class);
        RedirectAttributes rd = mock(RedirectAttributes.class);
        when(candService.findAllCandidate()).thenReturn(Collections.emptyList());
        when(testTakerService.getAll()).thenReturn(Collections.emptyList());

        String result = myController.viewResult(model, rd);

        verify(rd).addFlashAttribute("resultList", Collections.emptyList());
        verify(model).addAttribute("testTakerList", Collections.emptyList());
        assertEquals("admin/result", result);
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

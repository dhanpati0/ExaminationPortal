package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpSession;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.csmtech.model.Candidate;
import com.csmtech.model.Configure;
import com.csmtech.model.Items;
import com.csmtech.model.SubTestTaker;
import com.csmtech.model.User;
import com.csmtech.repository.CandidateRepository;
import com.csmtech.service.CandidateService;
import com.csmtech.service.ConfigureService;

import com.csmtech.service.ItemService;
import com.csmtech.service.QuestionService;
import com.csmtech.service.SubTestTakerService;
import com.csmtech.service.UserService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProctorControllerTest {
	 
	@InjectMocks
	private ProctorController proctorController;
	
    @Autowired
    private MockMvc mockMvc;
    
    @Mock
    private SimpMessagingTemplate template;
    
    @Mock
    ConfigureService configureService;
    
 
    
    @Mock
    private ItemService itemService;
    
    @Mock
    private HttpSession httpSession;

    @Mock
    private UserService userService;

    @Mock
	private CandidateService candidateService;
    @Mock
    private QuestionService questionService;
    
    @Mock
    private CandidateRepository candidateRepository;
    
    @Mock
	private SubTestTakerService subTestTakerService;
    
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(proctorController).build();
    }
    
    @Test
    public void testForgetPassword() throws Exception {
        
        User user = new User();
        user.setName("TestUser");
        when(httpSession.getAttribute("sessionData")).thenReturn(user);

       
        mockMvc.perform(get("/exam/forgetPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("proctor/resetPassword"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attribute("username", user.getName()));

        
        verify(httpSession, times(1)).getAttribute("sessionData");
    }
	
	  @Test public void testChangePassword() throws Exception {
	  
	  User user = new User(); user.setName("TestUser"); user.setUserId(123);
	  when(httpSession.getAttribute("sessionData")).thenReturn(user);
	  
	  mockMvc.perform(post("/exam/changepassword") .param("newpassword",
	  "newPassword123") .param("cpassword", "newPassword123") .param("userid",
	  "123")) .andExpect(status().isOk())
	  .andExpect(view().name("proctor/resetPassword"))
	  .andExpect(model().attributeExists("username"))
	  .andExpect(model().attribute("username", user.getName()))
	  .andExpect(model().attributeExists("userid"))
	  .andExpect(model().attribute("userid", user.getUserId()));
	  
	  verify(httpSession, times(1)).getAttribute("sessionData");
	  
	  verify(userService, times(1)).saveDetailsOfUser(user); 
	  }
	  @Test
	    public void testGetManageProfile() throws Exception {
	        
	        User user = new User();
	        user.setName("TestUser");

	        when(httpSession.getAttribute("sessionData")).thenReturn(user);

	        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(proctorController).build();

	        mockMvc.perform(get("/exam/manageProfile"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("proctor/manageProfile"))
	                .andExpect(model().attributeExists("username"))
	                .andExpect(model().attribute("username", user.getName()));

	        verify(httpSession, times(1)).getAttribute("sessionData");
	    }
	  @Test //calling findallitem multiple times
	  public void testGetQuestion() throws Exception {
	      
	     
	      List<Items> mockItemList = Arrays.asList(new Items(), new Items());
 
	      

	      when(itemService.findAllItem()).thenReturn(mockItemList);


	      MockMvc mockMvc = MockMvcBuilders.standaloneSetup(proctorController).build();

	      mockMvc.perform(get("/exam/getQuestion"))
	              .andExpect(status().isOk())
	              .andExpect(view().name("proctor/addQuestion"))
	              .andExpect(model().attributeExists("codelist", "allListItem"))
	              .andExpect(model().attribute("allListItem", mockItemList));

	      //verify(examService, times(1)).findAllExamCode();

	      verify(itemService, times(1)).findAllItem();
	  }

	  @Test //server error 500
	    public void testAddQuestion() throws Exception {
	   
	        Items mockItem = new Items();
	        String questText = "Sample question text";
	        String questType = "Sample question type";

	        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(proctorController).build();

	        mockMvc.perform(MockMvcRequestBuilders.post("/exam/questionAdd")
	                .param("examCode", "code1")
	                .param("itemName", "1") 
	                .param("questionText", questText)
	                .param("questionType", questType))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/exam/getQuestion"));

	        
	       
	        verify(questionService, times(1)).saveQuestion(argThat(question -> {
	        
	            return question.getQuestionText().equals(questText)
	                    && question.getQuestionType().equals(questType)
	                    && question.getItem().equals(mockItem)
	                    && question.getQuestionStatus().equals("0");
	        }));
	    }
	  @Test
	    public void testFindProctor() {
	      
	        Integer userId = 1;
	        User mockUser = new User(); 

	        when(userService.findUserDetailsById(userId)).thenReturn(mockUser);

	        ResponseEntity<User> responseEntity = proctorController.findProctor(userId);

	        verify(userService, times(1)).findUserDetailsById(userId);

	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(mockUser, responseEntity.getBody());
	    }

	    @Test
	    public void testGetAllSubTestTaker() {
	       
	        List<SubTestTaker> mockSubTestTakers = Arrays.asList(new SubTestTaker(), new SubTestTaker());

	        when(subTestTakerService.getAllSubTestTaker()).thenReturn(mockSubTestTakers);

	        ResponseEntity<List<SubTestTaker>> responseEntity = proctorController.getAllSubTestTaker();

	        verify(subTestTakerService, times(1)).getAllSubTestTaker();

	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(mockSubTestTakers, responseEntity.getBody());
	    }
	    @Test
	    public void testCandidateList() {
	        Integer subTestTakerId = 1;
	        List<Candidate> mockCandidateList = Arrays.asList(new Candidate(), new Candidate());
	        when(candidateService.findAllCandidates(subTestTakerId)).thenReturn(mockCandidateList);

	        ResponseEntity<List<Candidate>> responseEntity = proctorController.candidateList(subTestTakerId);

	        verify(candidateService, times(1)).findAllCandidates(subTestTakerId);

	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(mockCandidateList, responseEntity.getBody());
	    }

	    @Test
	    public void testCandidateListBySession() {
	        Integer subTestTakerId = 1;
	        List<Candidate> mockCandidateList = Arrays.asList(new Candidate(), new Candidate());
	        when(candidateService.findAllCandidateByStatusAndSubTestTaker(subTestTakerId)).thenReturn(mockCandidateList);

	        ResponseEntity<List<Candidate>> responseEntity = proctorController.candidateListBySession(subTestTakerId);

	        verify(candidateService, times(1)).findAllCandidateByStatusAndSubTestTaker(subTestTakerId);

	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(mockCandidateList, responseEntity.getBody());
	    }

	    @Test
	    public void testExtraTime() throws Exception {
	      
	        String candId = "1";
	        String extraTime = "10";
	        Candidate mockCandidate = new Candidate();
	        mockCandidate.setCandEndTime(LocalTime.now()); 
	        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(proctorController).build();
	        when(candidateService.findDetailsById(anyInt())).thenReturn(mockCandidate);
	       // when(candidateService.saveCandidate(any())).thenReturn(mockCandidate);

	        mockMvc.perform(MockMvcRequestBuilders.post("/exam/extraTime")
	                .param("candId", candId)
	                .param("extraTime", extraTime))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().string("Success"));

	        verify(candidateService, times(1)).findDetailsById(anyInt());
	        //verify(candidateService, times(1)).saveCandidate(any());
	    }
	    @Test //stubbing error
	    public void testGetListOfExams() throws Exception {
	        SubTestTaker mockSubTestTaker = new SubTestTaker();
	        mockSubTestTaker.setSubTestTakerId(1);

	        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(proctorController).build();

	        when(subTestTakerService.getAllSubTestTaker()).thenReturn(Collections.singletonList(mockSubTestTaker));
	        when(configureService.getAllConfDetails(anyInt())).thenReturn(new Configure());
	        when(configureService.getAllConfDetails(isNull())).thenReturn(new Configure());

	        mockMvc.perform(MockMvcRequestBuilders.get("/exam/getListOfExams"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.view().name("proctor/proctorDashboard"))
	                .andExpect(MockMvcResultMatchers.model().attributeExists("examList", "examTimeList"));

	        verify(subTestTakerService, times(1)).getAllSubTestTaker();
	        verify(configureService, times(1)).getAllConfDetails(anyInt());
	        verify(configureService, times(1)).getAllConfDetails(isNull());
	    }
	    
	    @Test
	    public void testFetchAllCands() throws Exception {
	      
	        Candidate candidate1 = mock(Candidate.class);
	        when(candidate1.getStatus()).thenReturn("1");

	        Candidate candidate2 = mock(Candidate.class);
	        when(candidate2.getStatus()).thenReturn("inactive");

	        List<Candidate> candidates = Arrays.asList(candidate1, candidate2);

	        Mockito.lenient().when(candidateService.findAllCandidate()).thenReturn(candidates);
	        for (Candidate c : candidates) {
	            SubTestTaker subTestTaker = mock(SubTestTaker.class);
	            when(c.getSubTestTaker()).thenReturn(subTestTaker);
	            Configure configure = new Configure();
	            configure.setSubTestTaker(subTestTaker);
	            when(configureService.getAllConfDetails(subTestTaker.getSubTestTakerId())).thenReturn(configure);
	        }
	        System.out.println("Candidate List Size: " + candidates.size());
	    
	        mockMvc.perform(get("/exam/monitor"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("proctor/monitor"))
	                .andExpect(model().attributeExists("configList", "candidates"))
	                .andExpect(model().attribute("candidates", Matchers.hasSize(2)))
	                .andExpect(model().attribute("candidates", Matchers.containsInAnyOrder(
	                        Matchers.hasProperty("status", Matchers.equalTo("inactive")),
	                        Matchers.hasProperty("status", Matchers.equalTo("1"))
	                )))
	                .andExpect(model().attribute("configList", Matchers.hasSize(1))); 
	    }
	    
	    @Test
	    public void testUpdateCandidateStatus() throws Exception {
	        
	        Mockito.when(candidateService.updateStatus(Mockito.anyInt())).thenReturn(true);

	       
	        mockMvc.perform(MockMvcRequestBuilders.get("/updateStatus").param("id", "123"))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/exam/monitor"))
	                .andExpect(MockMvcResultMatchers.view().name("redirect:/exam/monitor"));

	        Mockito.verify(candidateService, Mockito.times(1)).updateStatus(Mockito.eq(123));
	    }
	    @Test
	    void testSendDataViaWebSocket() {
	   
	        Candidate mockCandidate1 = mock(Candidate.class);
	        when(mockCandidate1.getStatus()).thenReturn("1");

	        Candidate mockCandidate2 = mock(Candidate.class);
	        when(mockCandidate2.getStatus()).thenReturn("inactive");

	        List<Candidate> candidates = Arrays.asList(mockCandidate1, mockCandidate2);

	        Configure mockConfigure = mock(Configure.class);
	     
	        List<Configure> configureList = Arrays.asList(mockConfigure);

	
	        when(candidateService.findAllCandidate()).thenReturn(candidates);
	        when(configureService.getAllConfDetails(any())).thenReturn(mockConfigure);


	        verify(template).convertAndSend(eq("/topic/combinedData"), anyMap());

	        verify(candidates, times(1)).stream();
	        verify(candidates, times(1)).sort(any());
	        ((Stream<Candidate>) verify(candidates, times(1))).collect(any());
	    }
	    
}

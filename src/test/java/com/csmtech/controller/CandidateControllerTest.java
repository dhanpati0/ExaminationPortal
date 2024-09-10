package com.csmtech.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.csmtech.bean.QuestionBean;
import com.csmtech.dto.MyDto;
import com.csmtech.model.Candidate;
import com.csmtech.model.Configure;
import com.csmtech.model.CorrectAnswer;
import com.csmtech.model.SubTest;
import com.csmtech.model.SubTestTaker;
import com.csmtech.model.TestTaker;
import com.csmtech.model.User;
import com.csmtech.repository.CandidateRepository;
import com.csmtech.service.AnswerService;
import com.csmtech.service.CandidateService;
import com.csmtech.service.ConfigureService;
import com.csmtech.service.CorrectAnswerService;
import com.csmtech.service.QuestionService;
import com.csmtech.service.QuestionSubTestService;
import com.csmtech.service.SubTestService;
import com.csmtech.service.SubTestTakerService;
import com.csmtech.service.TestService;
import com.csmtech.service.TestTakerService;
import com.csmtech.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@ExtendWith(MockitoExtension.class)

public class CandidateControllerTest {

    @InjectMocks
    private CandidateController candidateController;


    @Mock
    private HttpSession httpSession;

    @Mock
    private CandidateService candidateService;

    @Mock
    private TestTakerService testTakerService;

    @Mock
    private SubTestTakerService subTestTakerService;

    @Mock
    private TestService testService;
    
    @Mock
    UserService userService;
    
    @Mock
    private SubTestService subTestService;

    @Mock
    private ConfigureService configureService;

    @Mock
    private QuestionSubTestService questionSubTestService;

    @Mock
    private QuestionService questionService;

    @Mock
    private AnswerService answerService;

    @Mock
    private CorrectAnswerService correctAnswerService;

    @Mock
    private CandidateRepository candRepo;

    @Mock
    private Model mockModel;

    @Mock
    private RedirectAttributes mockRedirectAttributes;
    private MockMvc mockMvc;

    
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(candidateController).build();
    }

    @Test
    public void testTakersPage() throws Exception {
    	 User user = new User(); 
         user.setName("TestUser");  
         
         when(httpSession.getAttribute("sessionData")).thenReturn(user);
   
         List<TestTaker> mockTestTakerList = new ArrayList<>();
         List<com.csmtech.model.Test> mockTestList = new ArrayList<>();
        
         when(testTakerService.getAllCollege()).thenReturn(mockTestTakerList);
         when(testService.getAllTest()).thenReturn(mockTestList);
  
         when(httpSession.getAttribute("colValid")).thenReturn(42); 
        
         when(httpSession.getAttribute("invalideColumnNames")).thenReturn(null);
        
         Model mockModel = Mockito.mock(Model.class);
        
         String result = candidateController.testTakers(mockModel);
         
         verify(mockModel).addAttribute("username", user.getName());
         verify(mockModel).addAttribute("saveBox", "yes");
         verify(mockModel).addAttribute("invalidCol", 42); 
         verify(mockModel).addAttribute("invalideColumnNames", null); 
         verify(mockModel).addAttribute("testTakerList", mockTestTakerList);
         verify(mockModel).addAttribute("testList", mockTestList);

         assertEquals("admin/testTakerPage", result);
     }
    @Test
    public void addTestTakerTest() {
        
        String testTakerName = "TestTaker1";

        TestTaker mockedTestTaker = new TestTaker();
        mockedTestTaker.setTestTakerName(testTakerName);

        when(testTakerService.save(Mockito.any(TestTaker.class))).thenReturn(mockedTestTaker);
        when(testTakerService.getAll()).thenReturn(new ArrayList<>()); 

        String result = candidateController.addTestTaker(testTakerName, mockModel, mockRedirectAttributes);

        verify(testTakerService).save(Mockito.any(TestTaker.class));

        verify(mockModel).addAttribute("testtakerLists", testTakerService.getAll());

        assertEquals("admin/testTakerPage", result);
    }
    private TestTaker createTestTaker(String name, int id) {
        TestTaker testTaker = new TestTaker();
      
        testTaker.setTestTakerName(name);
        testTaker.setTestTakerId(id);

        return testTaker;
    }
    @Test
    public void getAllTestTakerListTest() throws IOException {
        List<TestTaker> testTakerList = new ArrayList<>();
        testTakerList.add(createTestTaker("TestTaker1", 1));
        testTakerList.add(createTestTaker("TestTaker2", 2));

        when(testTakerService.getAllCollege()).thenReturn(testTakerList);

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        candidateController.getAllTestTakerList(mockHttpServletResponse);

        String expectedResponse = "TestTaker1_1,TestTaker2_2";
        assertEquals(expectedResponse, mockHttpServletResponse.getContentAsString());
    }

    @Test
    public void testAddSubTestTaker() throws Exception {
        TestTaker testTaker = new TestTaker();
        testTaker.setTestTakerId(1);
        testTaker.setTestTakerName("TestTaker");
        String subTestTakerName = "SubTestTaker1";

        List<SubTestTaker> subTestTakerList = new ArrayList<>();
        when(subTestTakerService.getAllSubTestTakerByTestTakerId(1)).thenReturn(subTestTakerList);
        when(subTestTakerService.save(any(SubTestTaker.class))).thenReturn(new SubTestTaker());

        Model model = Mockito.mock(Model.class);

        MockHttpServletResponse response = new MockHttpServletResponse();

        candidateController.addSubTestTaker(testTaker, subTestTakerName, null, response, model);

        String expectedResponse = "notExists";
        assertEquals(expectedResponse, response.getContentAsString());

        verify(model).addAttribute(eq("addressNav"), eq("yes"));
        verify(model).addAttribute(eq("testTakerList"), any());
    }
    @Test
    public void testGetAllSubTestTakerList() throws Exception {
        Integer testTakerId = 123;
        List<SubTestTaker> subTestTakerList = Arrays.asList(
                //new SubTestTaker(1, "SubTestTaker1", null),
                //new SubTestTaker(2, "SubTestTaker2", null)
        );

        when(subTestTakerService.getAllSubTestTakerByTestTakerId(testTakerId)).thenReturn(subTestTakerList);

        mockMvc.perform(MockMvcRequestBuilders.get("/exam/getSubtestTakerByTestTaker")
                .param("testTakerId", String.valueOf(testTakerId)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("SubTestTaker1_1,SubTestTaker2_2"));
    }
  
   //addTestTakerByExcel test 
    @Test
    public void testShowTestAndAddQuestionSet() throws IOException {
        
        Integer subtestTakerId = 123;
        MockHttpServletResponse response = new MockHttpServletResponse();
               Model model = mock(Model.class);

        
        candidateController.showTestAndAddQuestionSet(subtestTakerId, response, model);

        verify(httpSession).setAttribute("stestDataId", subtestTakerId);
        verify(model).addAttribute("saveBoxExcel", "no");
        
        assertThat(response.getContentAsString()).isEqualTo("no");
    }
    @Test
    public void testGetSubItemListByQuestionTypeId() throws Exception {
      
        Integer testId = 123;
        List<SubTest> subTestList = Arrays.asList(new SubTest());
        when(subTestService.getAllSubTestByTestId(testId)).thenReturn(subTestList);

       
        MockHttpServletResponse response = new MockHttpServletResponse();

        candidateController.getSubItemListByQuestionTypeId(testId, response);

        verify(subTestService).getAllSubTestByTestId(testId);

        String expectedContent = "<option value='select'>--select--</option>";
        for (SubTest subTest : subTestList) {
            expectedContent += "<option value='" + subTest.getSubTestId() + "'>" + subTest.getSubTestName() + "</option>";
        }
        assertEquals(expectedContent, response.getContentAsString());
    }

   
   
    @Test
    void testSaveCandidatesByExcel() throws Exception {

        String content = " First Name, Last Name, Email,MobileNo\n" +
                "SS,SS,SS.s@example.com,1234567890";
        MultipartFile file = new MockMultipartFile("excelfile", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", content.getBytes());

        mockMvc.perform(fileUpload("/exam/getCandidatesByExcel")
                .file((MockMultipartFile) file))
                .andExpect(redirectedUrl("./testTakers"));
    }
  
    @Test
    void testExportToExcel() throws Exception {
       

        mockMvc.perform(get("/exam/candidate/export/excel")
                .contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk());
    }
    @Test
    void testSaveCandidate() {
     
        Integer subTestTakerId = 1;
        SubTestTaker subTestTaker = new SubTestTaker();
        subTestTaker.setSubTestTakerId(subTestTakerId);

        when(httpSession.getAttribute("subTestTakerData")).thenReturn(subTestTakerId);
        when(subTestTakerService.getSubTestTaker(subTestTakerId)).thenReturn(subTestTaker);

        String candFirstname = "John";
        String candLastname = "Doe";
        String candidateemail = "john.doe@example.com";
        String candMobile = "1234567890";

        when(candidateService.findAllCandidate()).thenReturn(Collections.emptyList());
        RedirectAttributes rd = new RedirectAttributesModelMap();
        String result = candidateController.saveCandidate(candFirstname, candLastname, candidateemail, candMobile, rd);

       
        verify(httpSession).getAttribute("subTestTakerData");
        verify(subTestTakerService).getSubTestTaker(subTestTakerId);

       
        if (result.equals("redirect:./testTakers")) {
        
            ArgumentCaptor<Candidate> candidateCaptor = ArgumentCaptor.forClass(Candidate.class);
            verify(candidateService).saveCandidate(candidateCaptor.capture());
    
            Candidate savedCandidate = candidateCaptor.getValue();
            assertEquals(candFirstname, savedCandidate.getCandFirstname());
            assertEquals(candLastname, savedCandidate.getCandLastname());
            assertEquals(candidateemail, savedCandidate.getCandidateemail());
            assertEquals(candMobile, savedCandidate.getCandMobile());
            assertEquals(subTestTaker, savedCandidate.getSubTestTaker());
            
        } else {
           
            verify(candidateService).findAllCandidate();
            
        }
    }
    private MockHttpSession mockHttpSession = new MockHttpSession();
    @Test
    void testSaveConfigureTime() throws Exception {
       

        Integer subTestTakerId = 1;
        SubTestTaker subTestTaker = new SubTestTaker();
        subTestTaker.setSubTestTakerId(subTestTakerId);

        when(subTestTakerService.getSubTestTaker(subTestTakerId)).thenReturn(subTestTaker);

        ResultActions result = mockMvc.perform(post("/exam/saveConfigure")
                .param("subtestName", "YourSubTestName")
                .param("noOfQuestion", "10")
                .param("testDate", "2023-12-01")
                .param("loginTime", "12:00:00")
                .param("startTime", "13:00:00")
                .param("endTime", "14:00:00")
                .session(mockHttpSession));

        result.andExpect(redirectedUrl("./testTakers"))
                .andExpect(flash().attribute("config", "yes"));

        verify(subTestTakerService).getSubTestTaker(subTestTakerId);
        verify(configureService).saveConfigure(any());

    }
    @Test //nomodelandview found as no modeland view in return type
    void testCheckQuestionAvailability() throws Exception {
       
        Integer testId = 1;
        Integer subTestId = 2;
        ResultActions result = mockMvc.perform(get("/exam/checkQuestionAviableOrNot")
                .param("testId", testId.toString())
                .param("sId", subTestId.toString()));
        Integer totalQuestions = questionSubTestService.countAllQuestionBySubtestId(subTestId);
        result.andExpect(status().isOk())
                .andExpect(model().attribute("totalQuestionsinSet", totalQuestions)); 
        String responseContent = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        verify(questionSubTestService).countAllQuestionBySubtestId(subTestId);
    }
    @Test
    void testGetCandidateBySubTestTakerId() throws Exception {
       
        Integer subTestTakerId = 1; 
        List<Candidate> candidateList = candidateService.findAllCandidate();
        Configure config = new Configure(); 

        when(httpSession.getAttribute("subTestTakerData")).thenReturn(subTestTakerId);
        when(candidateService.findCandidateBySubTestTakerId(subTestTakerId)).thenReturn(candidateList);
        when(configureService.findConfigureBySubTestTakerId(subTestTakerId)).thenReturn(config);

        mockMvc.perform(MockMvcRequestBuilders.get("/exam/sendEmailToCandidates"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("./testTakers"))
                .andExpect(MockMvcResultMatchers.flash().attribute("emailSent", "yes"));

    }
    @Test //time null
    void testGetQuestion() throws Exception {
       
        Candidate candidate = new Candidate();
        SubTestTaker subTestTaker = new SubTestTaker();
        subTestTaker.setSubTestTakerId(1);
        candidate.setSubTestTaker(subTestTaker);

        Configure configure = new Configure();
        configure.setTestDate(LocalDate.now()); 
        configure.setLoginTime(LocalTime.now()); 

        when(httpSession.getAttribute("sessionData1")).thenReturn(candidate);
        when(configureService.findConfigureBySubTestTakerId(subTestTaker.getSubTestTakerId())).thenReturn(configure);

        int subTestId = 1;
        int noQuestion = 1;
        List<QuestionBean> questions = new ArrayList<>();
        when(questionSubTestService.findQuestionsRandomlyByGivingAdminInput(noQuestion, subTestId)).thenReturn(questions);

        int questionId = 1;
        List<CorrectAnswer> correctAnswers = new ArrayList<>();
        when(correctAnswerService.getAllAnswerByQuestionId(questionId)).thenReturn(correctAnswers);

        mockMvc.perform(MockMvcRequestBuilders.post("/exam/candidateQuestion"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("candidate/questions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentTime", "startExam", "endExam", "examLogin"));

    }
    @Test
    void testGetAns() {
        
        Integer qId = 1; 
        CorrectAnswer correctAnswer1 = new CorrectAnswer();
        correctAnswer1.setCorrectAns("A");
        CorrectAnswer correctAnswer2 = new CorrectAnswer();
        correctAnswer2.setCorrectAns("B");
        List<CorrectAnswer> mockAnswerList = Arrays.asList(correctAnswer1, correctAnswer2);

        when(correctAnswerService.getAllAnswerByQuestionId(qId)).thenReturn(mockAnswerList);

        String result = candidateController.getAns(qId);

        verify(correctAnswerService, times(1)).getAllAnswerByQuestionId(qId);

        String expectedConcatenatedString = "AB"; 
        assertEquals(expectedConcatenatedString, result);
    }
    @Test
    void testSaveExamEndpoint() throws Exception {
      
        when(httpSession.getAttribute("sessionData1")).thenReturn(new Candidate());
        when(httpSession.getAttribute("quests")).thenReturn(Collections.emptyList());
        when(correctAnswerService.getAllAnswerByQuestionId(1)).thenReturn(Collections.emptyList());
        when(answerService.getMarksById(1)).thenReturn(Collections.singletonList(2));

        String requestBody = "[{\"questionId\": 1, \"option\": [\"A\"]}]";

        mockMvc.perform(MockMvcRequestBuilders.post("/exam/saveExam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Exam saved successfully."));
    }
    @Test
    void testGetAllCandidatesBySubTestTakerId() throws Exception {
      
        mockMvc.perform(MockMvcRequestBuilders.get("/exam/getAllCandidatesByStId")
                .param("subtestTakerId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
          
        verify(candidateService).findCandidateBySubTestTakerId(1);
    }
    @Test
    void testUpdatePauseState() throws Exception {
        when(candidateService.findDetailsById(anyInt())).thenReturn(new Candidate());
        when(candidateService.saveCandidate(any(Candidate.class))).thenReturn(new Candidate());

        mockMvc.perform(MockMvcRequestBuilders.post("/exam/updatePauseState")
                .param("candidate_id", "1")
                .param("newState", "PAUSED"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Updated successfully"));

        verify(candidateService, times(1)).findDetailsById(anyInt());
        verify(candidateService, times(1)).saveCandidate(any(Candidate.class));
    }

    @Test
    void testUpdateCandEndTime() throws Exception {
        MyDto dto = new MyDto();
        dto.setCandid(1);
        dto.setCandEndTime(LocalTime.now());
        when(candRepo.findById(anyInt())).thenReturn(Optional.of(new Candidate()));
        when(candRepo.save(any(Candidate.class))).thenReturn(new Candidate());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(MockMvcRequestBuilders.post("/exam/updateCandEndTime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))) 
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Candend_time updated successfully"));

        verify(candRepo, times(1)).findById(anyInt());
        verify(candRepo, times(1)).save(any(Candidate.class));
    }
}
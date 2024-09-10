package com.csmtech.controller;

import com.csmtech.bean.QuestionBean;
import com.csmtech.model.*;
import com.csmtech.repository.QuestionRepository;
import com.csmtech.repository.SubTestRepository;
import com.csmtech.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TestControllerTest {

    @InjectMocks
    private TestController testController;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private SubTestRepository subTestRepository;

    @Mock
    private TestService testService;

    @Mock
    private SubTestService subTestService;

    @Mock
    private HttpSession httpSession;

    @Mock
    private SubItemService subItemService;

    @Mock
    private QuestionTypeService questionTypeService;

    @Mock
    private ItemService itemService;

    @Mock
    private QuestionService questionService;

    @Mock
    private QuestionSubTestService questionSubTestService;

    @Mock
    private Model model;

    private MockHttpServletRequest request;

    @Mock
    private HttpServletResponse httpServletResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();

    }

    @org.junit.jupiter.api.Test
    void getTestTest() {
        Model model = mock(Model.class);
        String view = testController.getTest(model);
        assertEquals("admin/testPage", view);
        verify(model).addAttribute(eq("testList"), anyList());
        verify(model).addAttribute(eq("test"), eq("yes"));
        verify(model).addAttribute(eq("SetDiv"), eq("yes"));
    }

    @org.junit.jupiter.api.Test
    void getAllItemListTest() throws Exception {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(httpServletResponse.getWriter()).thenReturn(writer);

        List<Test> testList = new ArrayList<>();
        when(testService.getAllTest()).thenReturn(testList);

        testController.getAllItemList(httpServletResponse);

        writer.flush();
        assertEquals("", stringWriter.toString());

        verify(httpServletResponse).getWriter();
    }

    @org.junit.jupiter.api.Test
    void getAllTestTest() {
        List<Test> mockTestList = Arrays.asList(new Test(), new Test());
        when(testService.getAllTest()).thenReturn(mockTestList);

        String viewName = testController.getAllTest(model);

        assertEquals("admin/testPage", viewName);

        verify(model).addAttribute("testList", mockTestList);
        verify(model).addAttribute("test", "yes");
        verify(model).addAttribute("testNav", "yes");

        verify(testService).getAllTest();
    }

    @org.junit.jupiter.api.Test
    void testGetAllSubTest() {
        when(testService.findTestNameByTestId(anyInt())).thenReturn(new com.csmtech.model.Test());

        when(subTestService.getAllSubTestByTestId(anyInt())).thenReturn(Collections.emptyList());

        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setName("Santhosh");
        request.setSession(httpSession);
        httpSession.setAttribute("sessionData", mockUser);

        String viewName = testController.getAllSubTest(1, model, request);

        assertEquals("admin/testPage", viewName);

        verify(model).addAttribute("username", mockUser.getName());
        verify(model).addAttribute("test", "yes");
        verify(model).addAttribute("testNav", "yes");
        verify(model).addAttribute("Tname", "yes");

        verify(testService).findTestNameByTestId(1);

        verify(subTestService).getAllSubTestByTestId(1);

        verify(request.getSession()).getAttribute("sessionData");

        verify(request.getSession()).setAttribute("testNavName", any());
        verify(request.getSession()).setAttribute("testData", 1);
    }

    @org.junit.jupiter.api.Test
    void SaveTestTest() {
        String viewName = testController.saveTest("SampleTest", model);

        assertEquals("admin/testPage", viewName);

        verify(testService).saveTest(argThat(test -> "SampleTest".equals(test.getTestName())));

        verify(model).addAttribute("testList", testService.getAllTest());
    }

    @org.junit.jupiter.api.Test
    void checkTestNameExistsTest() {
        Test test = new Test();
        test.setTestName("ExistingTest");
        when(testService.getAllTest()).thenReturn(Stream.of(test).collect(Collectors.toList()));

        String result = testController.checkTestName("ExistingTest");

        assertEquals("exists", result);

        verify(testService).getAllTest();
    }

    @org.junit.jupiter.api.Test
    void saveSubTestTest() {
        Test test = new Test();
        test.setTestName("ExistingTest");
        when(testService.getAllTest()).thenReturn(Stream.of(test).collect(Collectors.toList()));

        SubTest subTest = new SubTest();
        when(subTestService.saveSubTest(any())).thenReturn(subTest);

        String result = testController.saveSubTest(test, "SubTestName", model);

        assertEquals("admin/testPage", result);

        verify(testService).getAllTest();

        verify(subTestService).saveSubTest(argThat(subTest1 -> "SubTestName".equals(subTest1.getSubTestName())));
    }

    @org.junit.jupiter.api.Test
    void checkSubTestNameTest() {
        Test test = new Test();
        test.setTestName("ExistingTest");
        test.setTestId(1);
        SubTest subTest = new SubTest();
        subTest.setSubTestName("SubTestName");
        subTest.setTest(test);
        when(subTestService.findAll()).thenReturn(Collections.singletonList(subTest));

        String result = testController.checkSubTestName(1, "SubTestName");

        assertEquals("exists", result);

        verify(subTestService).findAll();
    }

    @org.junit.jupiter.api.Test
    void showTestAndAddQuestionSetTest() throws IOException {
        PrintWriter printWriter = mock(PrintWriter.class);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        testController.showTestAndAddQuestionSet(1, httpServletResponse, mock(Model.class));

        verify(printWriter).print("no");

        verify(httpSession).setAttribute("stestData", 1);
    }

    //Todo byItem(),byTest()

    @org.junit.jupiter.api.Test
    void getAllSubtestByTestTest() throws IOException {
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        PrintWriter printWriter = mock(PrintWriter.class);
        when(printWriter.toString()).thenReturn("");
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        List<SubTest> subTestList = Arrays.asList(
                createSubTest(1, "SubTest1"),
                createSubTest(2, "SubTest2")
        );
        when(subTestService.getAllSubTestByTestId(1)).thenReturn(subTestList);

        testController.getAllSubtestByTest(1, mockHttpServletResponse, mock(Model.class));

        //verify(printWriter).print("SubTest1_1,SubTest2_2");
        verify(subTestService).getAllSubTestByTestId(1);

    }

    @org.junit.jupiter.api.Test
    public void getAllQuestionBySubTestIdTest() throws Exception {
        Integer sId = 123;
        PrintWriter printWriter = mock(PrintWriter.class);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        when(questionSubTestService.findAllQuestionBySubTest(sId)).thenReturn(someListOfQuestionBeans());

        model.addAttribute(someListOfQuestionBeans());
        testController.getAllQuestionBySubTestId(sId, httpServletResponse, model);

        verify(questionSubTestService).findAllQuestionBySubTest(sId);
    }

    private List<QuestionBean> someListOfQuestionBeans() {

        QuestionType questionType = new QuestionType();
        Items items = new Items();
        items.setItemId(1);
        items.setItemName("item");

        QuestionBean questionBean = new QuestionBean();
        questionBean.setQuestionId(123);
        questionBean.setQuestionType(questionType);
        questionBean.setItem(items);

        return Stream.of(questionBean).collect(Collectors.toList());
    }

    @org.junit.jupiter.api.Test
    public void showQuestionTypeByIdTest() throws Exception {
        Integer itemId = 123;
        PrintWriter mockWriter = mock(PrintWriter.class);
        QuestionType questionType = new QuestionType();

        List<QuestionType> mockQuestionTypes = Stream.of(questionType).collect(Collectors.toList());
        when(questionTypeService.getAllQuestionType()).thenReturn(mockQuestionTypes);

        when(httpServletResponse.getWriter()).thenReturn(mockWriter);

        testController.showQuestionTypeById(itemId, httpServletResponse, model);

        verify(questionTypeService).getAllQuestionType();
        verify(model).addAttribute("test", "yes");
        verify(model).addAttribute("Tname", "yes");
        verify(model).addAttribute("testNav", "yes");
        verify(model).addAttribute("byTestLink", "byTestLink");
    }

    @org.junit.jupiter.api.Test
    void getSubitemListTest() throws IOException {
        Integer itemId = 1;
        Integer questionTypeId = 1;

        Items items = new Items();
        items.setItemName("item");
        items.setItemId(1);

        QuestionType questionType = new QuestionType();
        questionType.setQuestionTypeName("java");

        SubItem subItem = new SubItem();
        Question question = new Question();
        question.setQuestionType(questionType);
        question.setItem(items);

        List<SubItem> mockSubList = Stream.of(subItem).collect(Collectors.toList());
        List<Question> mockQList = Stream.of(question).collect(Collectors.toList());

        PrintWriter printWriter = mock(PrintWriter.class);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        when(subItemService.getAllSubItemsByQuestionTypeId(questionTypeId, itemId)).thenReturn(mockSubList);
        when(questionService.getAllQuestionByItemId(itemId, questionTypeId)).thenReturn(mockQList);

        testController.getSubitemList(itemId, questionTypeId, httpServletResponse);


        verify(subItemService, times(1)).getAllSubItemsByQuestionTypeId(questionTypeId, itemId);
        verify(questionService, times(1)).getAllQuestionByItemId(itemId, questionTypeId);
    }

    @org.junit.jupiter.api.Test
    void getQuestionBySubIdTest() throws IOException {
        // Arrange
        Integer subItemId = 1;
        Integer questionTypeId = 1;

        Items items = new Items();
        items.setItemName("item");
        items.setItemId(1);

        QuestionType questionType = new QuestionType();
        questionType.setQuestionTypeName("java");

        SubItem subItem = new SubItem();
        subItem.setSubItemId(1);
        subItem.setSubItemName("subItem");

        Question question = new Question();
        question.setQuestionType(questionType);
        question.setItem(items);
        question.setSubItem(subItem);

        List<Question> mockQuestionList = Stream.of(question).collect(Collectors.toList());

        PrintWriter printWriter = mock(PrintWriter.class);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);
        when(questionService.getAllQuestionBySubItemIdForTest(subItemId, questionTypeId)).thenReturn(mockQuestionList);

        testController.getQuestionBySubId(httpServletResponse, subItemId, questionTypeId, model);

        verify(questionService, times(1)).getAllQuestionBySubItemIdForTest(subItemId, questionTypeId);
    }

    @org.junit.jupiter.api.Test
    void getQuestionBodyByIdTest() throws IOException {
        Integer questionId = 1;
        Model model = mock(Model.class);

        Question mockQuestion = new Question();
        PrintWriter printWriter = mock(PrintWriter.class);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        when(questionService.getAllQuestionsByQuestionId(questionId)).thenReturn(mockQuestion);

        testController.getQuestionBodyById(httpServletResponse, questionId, model);

        verify(questionService, times(1)).getAllQuestionsByQuestionId(questionId);
    }

    @org.junit.jupiter.api.Test
    void saveSelectedQuestionBySubtestTest() {
        // Arrange

        Integer sId = 1;
        String questionIds = "1,2";
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        QuestionSubTestService questionSubTestService = mock(QuestionSubTestService.class);
        QuestionService questionService = mock(QuestionService.class);
        SubTestService subTestService = mock(SubTestService.class);

        List<QuestionSubTest> mockQuestionSubTestList = Arrays.asList(new QuestionSubTest(), new QuestionSubTest());
        when(questionSubTestService.saveQuestionIfNotPresent(sId)).thenReturn(mockQuestionSubTestList);

        List<Question> mockQuestionList = Arrays.asList(new Question(), new Question());
        when(questionService.getAllQuestionbyQuestionId(anyList())).thenReturn(mockQuestionList);

        SubTest mockSubTest = new SubTest(); // Replace with your actual mock SubTest
        when(subTestService.getSubTestById(sId)).thenReturn(mockSubTest);

        String result = testController.saveSelectedQuestionBySubtest(questionIds, redirectAttributes, sId);

        assertTrue(result.startsWith("redirect:./byTestLink?sId=" + sId));
    }

    private SubTest createSubTest(int id,String name){
        SubTest subTest = new SubTest();
        subTest.setSubTestName(name);
        subTest.setSubTestId(id);
        return subTest;
    }
}

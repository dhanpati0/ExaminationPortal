package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csmtech.model.Items;
import com.csmtech.model.QuestionType;
import com.csmtech.model.SubItem;
import com.csmtech.model.User;
import com.csmtech.service.CorrectAnswerService;
import com.csmtech.service.ItemService;
import com.csmtech.service.QuestionService;
import com.csmtech.service.QuestionTypeService;
import com.csmtech.service.SubItemService;

@ExtendWith(MockitoExtension.class)
public class FolderControllerTest {


    @InjectMocks
    private FolderController folderController;

    @Mock
    private ItemService itemService;

    @Mock
    private QuestionTypeService questionTypeService;
    
    @Mock 
    QuestionService questionService;
    
    @Mock
    SubItemService subItemService;
    
    @Mock
    private HttpSession httpSession;

    @Mock
    private Model model;
    
    @Mock
    private RedirectAttributes redirectAttributes;
    
    @Mock
    CorrectAnswerService correctAnswerService;
    
    Items items = createItems(1,"ItemOne");
    List<Items> itemsList = Stream.of(createItems(1,"ItemOne"),
                                createItems(2,"ItemTwo")).collect(Collectors.toList());
    
    
    private Items createItems(int id, String name){
        Items items = new Items();
        items.setItemId(id);
        items.setItemName(name);
        return items;
    }
    
    QuestionType questionType = createQuestionType(1,"Subjective");
	
	List<QuestionType> listQuestionType = Stream.of(createQuestionType(1,"Objective"),
			createQuestionType(2,"Subjective")).collect(Collectors.toList());
    
	private QuestionType createQuestionType(int questionTypeId, String questionTypeName) {
		
		QuestionType questionType = new QuestionType();
		questionType.setQuestionTypeId(questionTypeId);
		questionType.setQuestionTypeName(questionTypeName);
		
		return questionType;
	}
	
    @Test
    public void testCreateFolder() {
    
        User user = new User();
        user.setName("TestUser");

        when(httpSession.getAttribute("sessionData")).thenReturn(user);
        when(itemService.findAllItem()).thenReturn(Collections.emptyList());
        when(questionTypeService.getAllQuestionType()).thenReturn(Collections.emptyList());
        

        String viewName = folderController.createFolder(model, redirectAttributes);

        verify(httpSession, times(1)).getAttribute("sessionData");
        verify(itemService, times(1)).findAllItem();
        verify(questionTypeService, times(1)).getAllQuestionType();
        verify(model, times(1)).addAttribute("itemListss", itemsList);
        verify(model, times(1)).addAttribute("username", "TestUser");
        verify(model, times(1)).addAttribute("questionTypeList", listQuestionType);

        assertEquals("admin/itemsFolder", viewName);
    }
    @Test
    public void testGetItemList() {
     
        User user = new User();
        user.setName("TestUser");

        when(httpSession.getAttribute("sessionData")).thenReturn(user);
        when(itemService.findAllItem()).thenReturn(Collections.emptyList());
        when(questionTypeService.getAllQuestionType()).thenReturn(Collections.emptyList());

        String viewName = folderController.getItemList(model);

        verify(httpSession, times(1)).getAttribute("sessionData");
        verify(itemService, times(2)).findAllItem(); 
        verify(questionTypeService, times(1)).getAllQuestionType();
        verify(model, times(1)).addAttribute("adressNav", "yes");
        verify(model, times(1)).addAttribute("username", "TestUser");
        verify(model, times(1)).addAttribute("itemListsss", Collections.emptyList());
        verify(model, times(1)).addAttribute("itemList", Collections.emptyList());
        verify(model, times(1)).addAttribute("questionTypeList", Collections.emptyList());

        assertEquals("admin/itemsFolder", viewName);
    }

    @Test
    public void testShowAllQuestionType() {
        
        User user = new User();
        user.setName("TestUser");

        Items mockedItem = new Items();
        mockedItem.setItemId(1); 

        when(httpSession.getAttribute("sessionData")).thenReturn(user);
        when(itemService.findItemNameByItemId(1)).thenReturn(new Items());
        when(itemService.findAllItem()).thenReturn(Collections.emptyList());
        when(questionTypeService.getAllQuestionType()).thenReturn(Collections.emptyList());

        MockHttpServletRequest request = new MockHttpServletRequest();

        String viewName = folderController.showAllQuestionType(mockedItem, model, request);

        verify(httpSession, times(1)).getAttribute("sessionData");
        verify(itemService, times(1)).findItemNameByItemId(1);
        verify(itemService, times(2)).findAllItem(); // called twice for "itemListsss" and "itemList"
        verify(questionTypeService, times(1)).getAllQuestionType();
        verify(httpSession, times(1)).setAttribute("itemIdData", 1);
        verify(model, times(1)).addAttribute("username", "TestUser");
        verify(model, times(1)).addAttribute("itemNav", "yes");
        verify(model, times(1)).addAttribute("adressNav", "yes");
        verify(model, times(1)).addAttribute("itemListsss", Collections.emptyList());
        verify(model, times(1)).addAttribute("itemList", Collections.emptyList());
        verify(model, times(1)).addAttribute("questionTypeList", Collections.emptyList());
        verify(model, times(1)).addAttribute("ivalue", 1);

        assertEquals("admin/itemsFolder", viewName);
    }
    
    @Test 
    public void testShowSubItem() {
       
        User user = new User();
        user.setName("TestUser");

        QuestionType mockedQuestionType = new QuestionType();
        mockedQuestionType.setQuestionTypeId(1); 

        when(httpSession.getAttribute("sessionData")).thenReturn(user);
        when(questionTypeService.findQuestionTypeByQuestionTypeId(1)).thenReturn(mockedQuestionType);
        when(subItemService.getAllSubItemsByQuestionTypeId(1, 1)).thenReturn(Collections.emptyList());
        when(httpSession.getAttribute("itemIdData")).thenReturn(1);
        when(questionService.getAllQuestionByItemId(1, 1)).thenReturn(Collections.emptyList()); // Mock the method call

        MockHttpServletRequest request = new MockHttpServletRequest();

        String viewName = folderController.showSubItem(mockedQuestionType, model, request);

        assertEquals("admin/itemsFolder", viewName);
    }
    @Test
    public void testGetAllQuestion() {
        
        User user = new User();
        user.setName("TestUser");

        SubItem mockedSubItem = new SubItem();
        mockedSubItem.setSubItemId(1); 

        when(httpSession.getAttribute("sessionData")).thenReturn(user);
        when(subItemService.findSubItemNameById(1)).thenReturn("MockedSubItem");
        when(subItemService.getAllSubItemsByQuestionTypeId(1, 1)).thenReturn(Collections.emptyList());
        when(httpSession.getAttribute("itemIdData")).thenReturn(1);
        when(httpSession.getAttribute("qTypeIdData")).thenReturn(1);
        when(questionService.getAllQuestionBySubItemId(1)).thenReturn(Collections.emptyList());

        MockHttpServletRequest request = new MockHttpServletRequest();

        String viewName = folderController.getAllQuestion(mockedSubItem, model, request);

        verify(httpSession, times(1)).getAttribute("sessionData");
        verify(httpSession, times(1)).setAttribute("subItemData", mockedSubItem);
        verify(model, times(1)).addAttribute("username", "TestUser");
        verify(model, times(1)).addAttribute("itemNav", "yes");
        verify(model, times(1)).addAttribute("type", "yes");
        verify(model, times(1)).addAttribute("question", "yes");
        verify(model, times(1)).addAttribute("adressNav", "yes");
        verify(model, times(1)).addAttribute("firstOneChar", "M"); // Replace with the expected first character

        assertEquals("admin/itemsFolder", viewName);
    }

    @Test
    public void testAddItem() throws IOException {

        Items item = new Items();
        item.setItemName("TestItem");

        List<Items> savedItems = new ArrayList<>();
        when(itemService.findAllItem()).thenReturn(savedItems);

        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        folderController.addItem(item, mockResponse);

        verify(itemService, times(1)).findAllItem();
        verify(itemService, times(1)).saveItems(item);

        assertEquals("success", mockResponse.getContentAsString());
    }
    @Test
    public void testAddSubItem() throws IOException {
        Items item = new Items();
        item.setItemId(1);

        QuestionType questionType = new QuestionType();
        questionType.setQuestionTypeId(1);

        when(subItemService.findAll()).thenReturn(Collections.emptyList());
        when(subItemService.saveSubItem(any(SubItem.class))).thenReturn(new SubItem());

        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        folderController.addSubItem(item, "TestSubItem", questionType, model, null, mockResponse);

        verify(subItemService, times(1)).findAll();
        verify(subItemService, times(1)).saveSubItem(any(SubItem.class));

        // Example: Verify that the model is being used properly
        verify(model, times(1)).addAttribute(eq("itemList"), anyList());

        assertEquals("success", mockResponse.getContentAsString());
    }

    @Test
    public void testGetSubItemListByQuestionTypeId() throws IOException {
        int questionTypeId = 1;
        int itemId = 2;

        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        PrintWriter mockWriter = mock(PrintWriter.class);
        when(mockResponse.getWriter()).thenReturn(mockWriter);

        List<SubItem> subItemList = new ArrayList<>();
        SubItem subItem1 = new SubItem();
        subItem1.setSubItemId(1);
        subItem1.setSubItemName("SubItem1");
        subItemList.add(subItem1);

        SubItem subItem2 = new SubItem();
        subItem2.setSubItemId(2);
        subItem2.setSubItemName("SubItem2");
        subItemList.add(subItem2);

        when(subItemService.getAllSubItemsByQuestionTypeId(questionTypeId, itemId)).thenReturn(subItemList);

        folderController.getSubItemListByQuestionTypeId(questionTypeId, itemId, mockResponse);

        verify(mockResponse, times(1)).getWriter();

        verify(mockWriter).print("<option value='0'>--select--</option><option value='1'>SubItem1</option><option value='2'>SubItem2</option>");
    }
    @Test //index outof bount line 260 in controller
    public void testSaveQuestion() throws IOException {
        
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        PrintWriter mockWriter = mock(PrintWriter.class);
        when(mockResponse.getWriter()).thenReturn(mockWriter);

        Items itemId = new Items();
        itemId.setItemId(1);

        QuestionType questionTypeId = new QuestionType();
        questionTypeId.setQuestionTypeId(2);

        SubItem sName = new SubItem();
        sName.setSubItemId(3);

        itemId.setItemName("ItemName");
      
        questionTypeId.setQuestionTypeName("QuestionType");

        when(itemService.findItemNameByItemId(itemId.getItemId())).thenReturn(itemId);
        when(questionTypeService.findQuestionTypeByQuestionTypeId(questionTypeId.getQuestionTypeId())).thenReturn(questionTypeId);
        
        when(subItemService.findAll()).thenReturn(Collections.singletonList(new SubItem()));

        folderController.saveQuestion("Option1#Option2#Option3#Option4#Option5", "QuestionText",
                new String[]{"correctAns1", "correctAns2"}, itemId, questionTypeId, sName, mockResponse);

        verify(mockResponse, times(1)).getWriter();
        verify(mockWriter, times(1)).print("success");

        verify(questionService, times(1)).saveQuestion(any());
        verify(correctAnswerService, times(2)).saveCrAns(any());
      }

    @Test //user null
    public void testGetQuestionByQuestionId() {
        Model mockModel = mock(Model.class);
        MockHttpSession mockHttpSession = mock(MockHttpSession.class);

        Integer questionId = 1;

        String result = folderController.getQuestionByQuestionId(questionId, mockModel);

        verify(mockHttpSession, times(1)).getAttribute("sessionData");
        verify(mockModel, times(1)).addAttribute(eq("username"), anyString());
        verify(mockModel, times(1)).addAttribute(eq("question"), any());

        assertEquals("admin/previewQuestion", result);
    }

    @Test
    public void testGoBack() {
  
        String result = folderController.backToItem();

        assertEquals("redirect:/exam/createFolder", result);
    }

    @Test
    public void testAddQuestionsByExcel() throws IOException {
   
        MultipartFile mockMultipartFile = new MockMultipartFile("excelQuestionfile", "test.xlsx", "application/vnd.ms-excel", "test data".getBytes());
        Items mockItemId = new Items();
        QuestionType mockQuestionType = new QuestionType();
        SubItem mockSubItem = new SubItem();
        HttpServletResponse mockResponse = new MockHttpServletResponse();
        RedirectAttributes mockRedirectAttributes = mock(RedirectAttributes.class);

        folderController.addQuestionsByExcel(mockMultipartFile, mockItemId, mockQuestionType, mockSubItem, mockResponse, mockRedirectAttributes);

    }
}

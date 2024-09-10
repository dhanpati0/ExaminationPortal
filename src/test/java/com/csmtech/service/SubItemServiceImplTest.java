package com.csmtech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.model.Items;
import com.csmtech.model.QuestionType;
import com.csmtech.model.SubItem;
import com.csmtech.model.User;
import com.csmtech.repository.SubItemRepository;

@SpringBootTest
public class SubItemServiceImplTest {

	@Mock
	private SubItemRepository subItemRepository;
	
	@InjectMocks
    private SubItemServiceImpl subitemService = new SubItemServiceImpl();
	
	SubItem sitem = new SubItem();
	Items item = new Items();
	QuestionType qt = new QuestionType();
	
	
	SubItem subitems = createSubItem(1, "core java", 1, "Java", 1, "Objective");
    List<SubItem> subItemList = Stream.of(createSubItem(2,".Net Core",2,".Net",1,"Objective"),
    		createSubItem(3,"Adv Java",2,".Java",1,"Objective")).collect(Collectors.toList());
	
    @BeforeEach
    void setSubItemMockOutput() {
    	sitem.setSubItemId(4);
    	sitem.setSubItemName("Javascript");
    	qt.setQuestionTypeId(1);
    	qt.setQuestionTypeName("Objective");
    	sitem.setQuestionType(qt);
    	item.setItemId(1);
    	item.setItemName("Java");
    	sitem.setItem(item);
    	
        when(subItemRepository.findSubItemNameById(4)).thenReturn(sitem.getSubItemName());
        when(subItemRepository.getAllSubItemsByQuestionTypeId(1, 1)).thenReturn(subItemList);
        when(subItemRepository.findAll()).thenReturn(subItemList);
        when(subItemRepository.getSubIdByName("Javascript")).thenReturn(sitem.getSubItemId());
        //when(userRepository.save(any(User.class))).thenReturn(user);
        when(subItemRepository.save(Mockito.any(SubItem.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }
    
    
    @DisplayName("Test to save SubItem")
    @Test
    void givenSubItem_WhenSave_ThenReturnSaved() {
    	SubItem savedsubitem = subitemService.saveSubItem(sitem);
        assertNotNull(savedsubitem);
        assertEquals(4,savedsubitem.getSubItemId());
    }
    
    @DisplayName("Test to Find Subitem by QuetsionTypeId and itemId")
    @Test
    void givenQuestionTypeIdAndItemId_WhenFound_ThenReturnSubItem() {
    	
    	List<SubItem> subItemList = subitemService.getAllSubItemsByQuestionTypeId(sitem.getQuestionType().getQuestionTypeId(), sitem.getItem().getItemId());
    	assertNotNull(subItemList);
        assertFalse(subItemList.isEmpty());
        assertEquals(2,subItemList.size());
    	
    }
    
    @DisplayName("Test to Find SubitemName")
    @Test
    void givenSubItemId_WhenFound_ThenReturnSubItemName() {
    	String subItemName = subitemService.findSubItemNameById(sitem.getSubItemId());
    	assertNotNull(subItemName);
    	assertEquals("Javascript", subItemName);
    	
    }
    
    @DisplayName("Test to Find SubItemId by SubItremName")
    @Test
    void givenSubName_WhenFound_thenReturnSubItemId() {
    	
    	Integer sId = subitemService.getSubIdByName(sitem.getSubItemName());
    	assertNotNull(sId);
    	assertEquals(4, sId);
    }
     
    
    @Test 
    void FoundAllSubItem_ReturnSubItem() {
    	List<SubItem> sItems = subitemService.findAll();
    	assertNotNull(sItems);
        assertFalse(sItems.isEmpty());
        assertEquals(2,sItems.size());
    	
    }
    
    private SubItem createSubItem(int subItemid, String subItemName,int itemId,String itemName,int QId,String QtypeName) {
		
    	QuestionType questionType = new QuestionType();
    	questionType.setQuestionTypeId(QId);
    	questionType.setQuestionTypeName(QtypeName);
    	Items item = new Items();
    	item.setItemId(itemId);
    	item.setItemName(itemName);
    	
		SubItem subitem = new SubItem();
        subitem.setSubItemId(subItemid);
        subitem.setSubItemName(subItemName);
        subitem.setQuestionType(questionType);
        subitem.setItem(item);
        
        return subitem;
	}
	
	
}

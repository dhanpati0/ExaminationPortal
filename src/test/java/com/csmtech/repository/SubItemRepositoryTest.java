package com.csmtech.repository;

import com.csmtech.model.Items;
import com.csmtech.model.QuestionType;
import com.csmtech.model.SubItem;
import com.csmtech.model.Test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubItemRepositoryTest {

    @Autowired
    SubItemRepository subItemRepository;
    
    @DisplayName("To save Test")
    @Order(1)
    @org.junit.jupiter.api.Test
	public void givenSubItemObject_whenSave_thenReturnSavedSubItem() {
    	
    	QuestionType qType = new QuestionType();
    	qType.setQuestionTypeId(1);
    	qType.setQuestionTypeName("Subjective");
    	Items i = new Items();
    	i.setItemId(1);
    	i.setItemName("java");
    	
    	SubItem subItem = new SubItem();
    	subItem.setSubItemId(1);
    	subItem.setItem(i);
    	subItem.setQuestionType(qType);
    	subItem.setSubItemName("Core Java");
    	
    	SubItem savesubItem = subItemRepository.save(subItem);
        System.out.println(savesubItem);
		assertThat(savesubItem).isNotNull();
	    assertThat(savesubItem.getSubItemId()).isGreaterThan(0);
    }

    @DisplayName("Test to find sub Item By SubItemId")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    @Order(2)
    public void givenSubItemId_WhenFound_ThenReturn(int id){

        //given Sub Item Id

        //When found sub Item with given ID
        SubItem subItem = subItemRepository.getReferenceById(id);

        //Then verify sub item
        assertNotNull(subItem);
        assertEquals(1,subItem.getSubItemId());
    }

    @DisplayName("Test to find Sub Item By question type Id")
    @ParameterizedTest
    @MethodSource("getQuestionIdAndItemId")
    @Order(3)
    public void givenQuestionTypeId_WhenFound_ThenReturn(){

        //given Sub Item Id

        //When found sub Item with given ID
        List<SubItem> subItems = subItemRepository.getAllSubItemsByQuestionTypeId(1,1);

        //Then verify sub item
        assertNotNull(subItems);
        assertFalse(subItems.isEmpty());
        assertEquals(1,subItems.get(0).getSubItemId());
    }

    static Stream<Arguments> getQuestionIdAndItemId() {
        return Stream.of(Arguments.of(1, 1));
    }

    @DisplayName("Test to find sub Item Name By SubItemId")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    @Order(4)
    public void givenSubItemId_WhenFoundName_ThenReturn(int id){

        //given Sub Item Id

        //When found sub Item with given ID
        String subItemName = subItemRepository.findSubItemNameById(id);

        //Then verify sub item
        assertNotNull(subItemName);
    }

    @DisplayName("Test to find sub Item Id By SubItemName")
    @ParameterizedTest
    @ValueSource(strings = { "Core Java" })
    @Order(5)
    public void givenSubItemName_WhenFound_ThenReturnId(String name){

        //given Sub Item Id

        //When found sub Item with given ID
        int id = subItemRepository.getSubIdByName(name);

        //Then verify sub item
        assertEquals(1,id);
    }

}

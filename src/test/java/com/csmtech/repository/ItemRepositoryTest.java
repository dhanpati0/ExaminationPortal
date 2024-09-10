package com.csmtech.repository;

import com.csmtech.model.Items;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    
    @Test
    @Order(1)
    @DisplayName("for save Item")
    public void givenItemObject_whenSaveItem_ThenReturnItem() {
    	
    	Items item = new Items();
    	item.setItemId(1);
    	item.setItemName("Java");
    	
    	Items saveItem = itemRepository.save(item);
    	
    	assertThat(saveItem).isNotNull();
    	assertThat(saveItem.getItemId()).isGreaterThan(0);
    	
    }

    @Order(2)
    @DisplayName("Test to find Item By ItemId")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenItemId_WhenItemIdFound_ThenReturnItem(int id){

        //Given Item Id as ValueSource

        //When find the Item by Id
        Items foundItem =itemRepository.findItemNameByItemId(id);

        //Then verify found Item
        assertNotNull(foundItem);
        assertTrue(foundItem.getItemId()>0);
        assertEquals(1,foundItem.getItemId());
    }

}

package com.csmtech.service;

import com.csmtech.model.Items;
import com.csmtech.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService = new ItemServiceImpl();

    Items items = createItems(1,"ItemOne");
    List<Items> itemsList = Stream.of(createItems(1,"ItemOne"),
                                createItems(2,"ItemTwo")).collect(Collectors.toList());

    @BeforeEach
    void setMockOutput() {
    	
        when(itemRepository.findItemNameByItemId(items.getItemId())).thenReturn(items);
        when(itemRepository.save(Mockito.any(Items.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(itemRepository.findAll()).thenReturn(itemsList);
    }

    @DisplayName("Test to save Items")
    @Test
    void givenItem_WhenSave_ThenReturnSaved() {
        Items savedItems = itemService.saveItems(items);
        assertNotNull(savedItems);
        assertEquals(1,savedItems.getItemId());
    }

    @DisplayName("Test to find Items by Id")
    @Test
    void givenId_WhenFind_ThenReturn() {
        Items foundItems = itemService.findItemNameByItemId(1);
        assertNotNull(foundItems);
        assertEquals(1,foundItems.getItemId());
    }

    @DisplayName("Test to find all Items")
    @Test
    void givenAllItem_WhenFound_ThenReturnList() {
        List<Items> itemsListFound = itemService.findAllItem();
        assertNotNull(itemsListFound);
        assertEquals(2,itemsListFound.size());
    }

    private Items createItems(int id, String name){
        Items items = new Items();
        items.setItemId(id);
        items.setItemName(name);
        return items;
    }
}

package com.thinkpalm.thinkfood.servicetest;


import com.thinkpalm.thinkfood.entity.Category;
import com.thinkpalm.thinkfood.entity.Item;
import com.thinkpalm.thinkfood.exception.CategoryNotFoundException;
import com.thinkpalm.thinkfood.exception.ItemDetailsMissingException;
import com.thinkpalm.thinkfood.exception.ItemNotFoundException;
import com.thinkpalm.thinkfood.repository.CategoryRepository;
import com.thinkpalm.thinkfood.repository.ItemRepository;
import com.thinkpalm.thinkfood.service.ItemServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplementationTest {

    @InjectMocks
    private ItemServiceImplementation itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateItemSuccess() throws ItemDetailsMissingException, CategoryNotFoundException {

        Long categoryId = 1L;
        String itemName = "Test Item";

        com.thinkpalm.thinkfood.model.Item itemModel = new com.thinkpalm.thinkfood.model.Item();
        itemModel.setItemName(itemName);

        Category categoryEntity = new Category();
        categoryEntity.setId(categoryId);
        categoryEntity.setIsActive(true);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));

        Item itemEntity = new Item();
        itemEntity.setId(1L);
        itemEntity.setItemName(itemName);
        itemEntity.setCategory(categoryEntity);
        itemEntity.setIsActive(true);

        when(itemRepository.save(any(Item.class))).thenReturn(itemEntity);


        com.thinkpalm.thinkfood.model.Item createdItem = itemService.createItem(itemModel, categoryId);


        assertNotNull(createdItem);
        assertEquals(itemEntity.getId(), createdItem.getId());
        assertEquals(itemEntity.getItemName(), createdItem.getItemName());


        verify(categoryRepository, times(1)).findById(categoryId);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    public void testCreateItemMissingItemDetails() {

        Long categoryId = 1L;

        com.thinkpalm.thinkfood.model.Item newItem = new com.thinkpalm.thinkfood.model.Item();

        assertThrows(ItemDetailsMissingException.class, () -> itemService.createItem(newItem, categoryId));

        verify(categoryRepository, never()).findById(categoryId);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    public void testGetAllItemsByCategorySuccess() {

        Long categoryId = 1L;

        Category categoryEntity = new Category();
        categoryEntity.setId(categoryId);
        categoryEntity.setIsActive(true);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));

        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setId(1L);
        item1.setItemName("Item 1");
        item1.setCategory(categoryEntity);
        item1.setIsActive(true);
        items.add(item1);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setItemName("Item 2");
        item2.setCategory(categoryEntity);
        item2.setIsActive(true);
        items.add(item2);

        when(itemRepository.findByCategory(categoryEntity)).thenReturn(items);


        List<com.thinkpalm.thinkfood.model.Item> itemModels = itemService.getAllItemsByCategory(categoryId);


        assertNotNull(itemModels);
        assertEquals(2, itemModels.size());

        com.thinkpalm.thinkfood.model.Item model1 = itemModels.get(0);
        assertEquals(item1.getId(), model1.getId());
        assertEquals(item1.getItemName(), model1.getItemName());

        com.thinkpalm.thinkfood.model.Item model2 = itemModels.get(1);
        assertEquals(item2.getId(), model2.getId());
        assertEquals(item2.getItemName(), model2.getItemName());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(itemRepository, times(1)).findByCategory(categoryEntity);
    }

    @Test
    public void testGetAllItemsByCategoryInactiveCategory() {

        Long categoryId = 1L;

        Category inactiveCategory = new Category();
        inactiveCategory.setId(categoryId);
        inactiveCategory.setIsActive(false);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(inactiveCategory));

        assertThrows(CategoryNotFoundException.class, () -> itemService.getAllItemsByCategory(categoryId));

        verify(itemRepository, never()).findByCategory(any(Category.class));
    }

    @Test
    public void testGetAllItemsByCategoryPaginationSuccess() {

        Long categoryId = 1L;
        Integer page = 0;
        Integer pageSize = 10;

        Category categoryEntity = new Category();
        categoryEntity.setId(categoryId);
        categoryEntity.setIsActive(true);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));

        List<Item> items = new ArrayList<>();

        Item item1 = new Item();
        item1.setId(1L);
        item1.setItemName("Item 1");
        item1.setCategory(categoryEntity);
        item1.setIsActive(true);
        items.add(item1);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setItemName("Item 2");
        item2.setCategory(categoryEntity);
        item2.setIsActive(true);
        items.add(item2);

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Item> itemPage = new PageImpl<>(items, pageRequest, items.size());

        when(itemRepository.findByCategory(categoryEntity, pageRequest)).thenReturn(itemPage);


        Page<com.thinkpalm.thinkfood.model.Item> itemModels = itemService.getAllItemsByCategoryPagination(categoryId, page, pageSize);


        assertNotNull(itemModels);
        assertEquals(2, itemModels.getContent().size());

        com.thinkpalm.thinkfood.model.Item model1 = itemModels.getContent().get(0);
        assertEquals(item1.getId(), model1.getId());
        assertEquals(item1.getItemName(), model1.getItemName());

        com.thinkpalm.thinkfood.model.Item model2 = itemModels.getContent().get(1);
        assertEquals(item2.getId(), model2.getId());
        assertEquals(item2.getItemName(), model2.getItemName());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(itemRepository, times(1)).findByCategory(categoryEntity, pageRequest);
    }

    @Test
    public void testGetAllItemsByCategoryPaginationInactiveCategory() {

        Long categoryId = 1L;
        Integer page = 0;
        Integer pageSize = 10;

        Category inactiveCategory = new Category();
        inactiveCategory.setId(categoryId);
        inactiveCategory.setIsActive(false);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(inactiveCategory));

        assertThrows(CategoryNotFoundException.class, () -> itemService.getAllItemsByCategoryPagination(categoryId, page, pageSize));

        verify(itemRepository, never()).findByCategory(any(Category.class), any(PageRequest.class));
    }

    @Test
    public void testSoftDeleteItemByIdSuccess() throws ItemNotFoundException {
        // Given
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);
        item.setIsActive(true);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));


        String result = itemService.softDeleteItemById(itemId);


        assertEquals("Item With Id " + itemId + " deleted successfully!", result);
        assertFalse(item.getIsActive());

        verify(itemRepository, times(1)).findById(itemId);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    public void testSoftDeleteItemByIdItemNotFound() {

        Long itemId = 1L;

        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.softDeleteItemById(itemId));

        verify(itemRepository, times(1)).findById(itemId);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    public void testUpdateItemByIdSuccess() throws ItemNotFoundException {

        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);
        item.setItemName("Old Item");
        item.setIsActive(true);

        com.thinkpalm.thinkfood.model.Item updatedItem = new com.thinkpalm.thinkfood.model.Item();
        updatedItem.setItemName("Updated Item");

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(itemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        com.thinkpalm.thinkfood.model.Item result = itemService.updateItemById(itemId, updatedItem);

        assertNotNull(result);
        assertEquals("Updated Item", result.getItemName());


        verify(itemRepository, times(1)).findById(itemId);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    public void testUpdateItemByIdItemNotFound() {

        Long itemId = 1L;
        com.thinkpalm.thinkfood.model.Item updatedItem = new com.thinkpalm.thinkfood.model.Item();
        updatedItem.setItemName("Updated Item");

        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.updateItemById(itemId, updatedItem));

        verify(itemRepository, times(1)).findById(itemId);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    public void testDeleteItemByIdSuccess() throws ItemNotFoundException {

        Long itemId = 1L;

        when(itemRepository.existsById(itemId)).thenReturn(true);

        itemService.deleteItemById(itemId);

        verify(itemRepository, times(1)).existsById(itemId);
        verify(itemRepository, times(1)).deleteById(itemId);
    }

    @Test
    public void testDeleteItemByIdItemNotFound() {

        Long itemId = 1L;

        when(itemRepository.existsById(itemId)).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> itemService.deleteItemById(itemId));

        verify(itemRepository, times(1)).existsById(itemId);
        verify(itemRepository, never()).deleteById(anyLong());
    }

}


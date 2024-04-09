//package com.thinkpalm.thinkfood.servicetest;
//
//import com.thinkpalm.thinkfood.entity.Category;
//import com.thinkpalm.thinkfood.exception.CategoryNotFoundException;
//import com.thinkpalm.thinkfood.repository.CategoryRepository;
//import com.thinkpalm.thinkfood.service.CategoryServiceImplementation;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.BeanUtils;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class CategoryServiceImplementationTest {
//
//    @InjectMocks
//    private CategoryServiceImplementation categoryService;
//
//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @Test
//    public void testCreateCategory() throws CategoryNotFoundException {
//
//        com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
//        categoryModel.setCategoryName("Biriyani");
//
//        Category categoryEntity = new Category();
//        BeanUtils.copyProperties(categoryModel, categoryEntity);
//        categoryEntity.setIsActive(true);
//
//        when(categoryRepository.save(any(Category.class))).thenReturn(categoryEntity);
//
//        com.thinkpalm.thinkfood.model.Category createdCategory = categoryService.createCategory(categoryModel);
//
//        assertNotNull(createdCategory);
//        assertEquals(categoryModel.getCategoryName(), createdCategory.getCategoryName());
//        //assertTrue(createdCategory.getIsActive());
//    }
//
////    @Test
////    public void testCreateCategoryWithNullName() {
////        com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
////        categoryModel.setCategoryName(null);
////
////        assertThrows(CategoryNotFoundException.class, () -> categoryService.createCategory(categoryModel));
////    }
//
//    @Test
//    public void testCreateCategoryWithEmptyName() {
//
//        com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
//        categoryModel.setCategoryName("");
//
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.createCategory(categoryModel));
//    }
//
//    @Test
//    public void testGetCategoryWithId() throws CategoryNotFoundException {
//
//        Long categoryId = 1L;
//        Category categoryEntity = new Category();
//        categoryEntity.setId(categoryId);
//        categoryEntity.setCategoryName("Biriyani");
//        categoryEntity.setIsActive(true);
//
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
//
//        com.thinkpalm.thinkfood.model.Category retrievedCategory = categoryService.getCategoryWithId(categoryId);
//
//        assertNotNull(retrievedCategory);
//        assertEquals(categoryEntity.getId(), retrievedCategory.getId());
//        assertEquals(categoryEntity.getCategoryName(), retrievedCategory.getCategoryName());
//        //assertTrue(retrievedCategory.getIsActive());
//    }
//
//    @Test
//    public void testGetCategoryWithIdNotFound() {
//
//        Long categoryId = 1L;
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
//
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryWithId(categoryId));
//    }
//
//    @Test
//    public void testGetCategoryWithIdNotActive() {
//
//        Long categoryId = 1L;
//        Category categoryEntity = new Category();
//        categoryEntity.setId(categoryId);
//        categoryEntity.setCategoryName("Biriyani");
//        categoryEntity.setIsActive(false);
//
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
//
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryWithId(categoryId));
//    }
//
//    @Test
//    public void testGetAllCategory() {
//
//        Category category1 = new Category();
//        category1.setId(1L);
//        category1.setCategoryName("Biriyani");
//        category1.setIsActive(true);
//
//        Category category2 = new Category();
//        category2.setId(2L);
//        category2.setCategoryName("Ice Cream");
//        category2.setIsActive(false);
//
//        List<Category> categories = List.of(category1, category2);
//
//        when(categoryRepository.findAll()).thenReturn(categories);
//
//        List<com.thinkpalm.thinkfood.model.Category> categoryModels = categoryService.getAllCategory();
//
//        assertNotNull(categoryModels);
//        assertEquals(1, categoryModels.size()); // Only one category should be returned since the second one is not active
//        assertEquals(category1.getId(), categoryModels.get(0).getId());
//        assertEquals(category1.getCategoryName(), categoryModels.get(0).getCategoryName());
//
//    }
//
//    @Test
//    public void testGetAllCategoryNoActiveCategories() {
//
//        Category category1 = new Category();
//        category1.setId(1L);
//        category1.setCategoryName("Biriyani");
//        category1.setIsActive(false);
//
//        Category category2 = new Category();
//        category2.setId(2L);
//        category2.setCategoryName("Ice Cream");
//        category2.setIsActive(false);
//
//        List<Category> categories = List.of(category1, category2);
//
//        when(categoryRepository.findAll()).thenReturn(categories);
//
//        List<com.thinkpalm.thinkfood.model.Category> categoryModels = categoryService.getAllCategory();
//
//        assertNotNull(categoryModels);
//        assertTrue(categoryModels.isEmpty()); // No active categories should be returned
//    }
//
//    @Test
//    public void testUpdateCategoryById() throws CategoryNotFoundException {
//
//        Long categoryId = 1L;
//        Category categoryEntity = new Category();
//        categoryEntity.setId(categoryId);
//        categoryEntity.setCategoryName("Biriyani");
//        categoryEntity.setIsActive(true);
//
//        com.thinkpalm.thinkfood.model.Category updatedCategory = new com.thinkpalm.thinkfood.model.Category();
//        updatedCategory.setCategoryName("Biriyani updated");
//
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
//        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//
//        com.thinkpalm.thinkfood.model.Category result = categoryService.updateCategoryById(categoryId, updatedCategory);
//
//
//        assertNotNull(result);
//        assertEquals(categoryEntity.getId(), result.getId());
//        assertEquals(updatedCategory.getCategoryName(), result.getCategoryName());
//    }
//
//    @Test
//    public void testUpdateCategoryByIdNotFound() {
//
//        Long categoryId = 1L;
//        com.thinkpalm.thinkfood.model.Category updatedCategory = new com.thinkpalm.thinkfood.model.Category();
//
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
//
//
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategoryById(categoryId, updatedCategory));
//    }
//
//    @Test
//    public void testUpdateCategoryByIdInactiveCategory() {
//
//        Long categoryId = 1L;
//        Category categoryEntity = new Category();
//        categoryEntity.setId(categoryId);
//        categoryEntity.setCategoryName("Biriyani");
//        categoryEntity.setIsActive(false);
//
//        com.thinkpalm.thinkfood.model.Category updatedCategory = new com.thinkpalm.thinkfood.model.Category();
//
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
//
//
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategoryById(categoryId, updatedCategory));
//    }
//
//    @Test
//    public void testSoftDeleteCategoryById() throws CategoryNotFoundException {
//
//        Long categoryId = 1L;
//        Category categoryEntity = new Category();
//        categoryEntity.setId(categoryId);
//        categoryEntity.setIsActive(true);
//
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
//        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        String result = categoryService.softDeleteCategoryById(categoryId);
//
//        assertNotNull(result);
//        assertEquals("Category With Id 1 deleted successfully!", result);
//        assertFalse(categoryEntity.getIsActive());
//    }
//
//    @Test
//    public void testSoftDeleteCategoryByIdNotFound() {
//
//        Long categoryId = 1L;
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
//
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.softDeleteCategoryById(categoryId));
//    }
//
//    @Test
//    public void testGetAllCategoryByPagination() {
//
//        Integer page = 0;
//        Integer size = 10;
//
//        Pageable pageable = PageRequest.of(page, size);
//
//        // Create a list of active categories
//        Category category1 = new Category();
//        category1.setId(1L);
//        category1.setCategoryName("Category 1");
//        category1.setIsActive(true);
//
//        Category category2 = new Category();
//        category2.setId(2L);
//        category2.setCategoryName("Category 2");
//        category2.setIsActive(true);
//
//        Category category3 = new Category();
//        category2.setId(3L);
//        category2.setCategoryName("Category 3");
//        category2.setIsActive(true);
//
//        List<Category> activeCategories = List.of(category1, category2);
//
//        // Create a Page object for active categories
//        Page<Category> activeCategoryPage = new PageImpl<>(activeCategories, pageable, activeCategories.size());
//
//        when(categoryRepository.findAll(pageable)).thenReturn(activeCategoryPage);
//
//
//        Page<com.thinkpalm.thinkfood.model.Category> categoryModelPage = categoryService.getAllCategoryByPagination(page, size);
//
//
//        assertNotNull(categoryModelPage);
//        assertEquals(activeCategories.size(), categoryModelPage.getContent().size());
//
//        for (int i = 0; i < activeCategories.size(); i++) {
//            assertEquals(activeCategories.get(i).getId(), categoryModelPage.getContent().get(i).getId());
//            assertEquals(activeCategories.get(i).getCategoryName(), categoryModelPage.getContent().get(i).getCategoryName());
//        }
//    }
//
//    @Test
//    public void testGetAllCategoryByPaginationNoActiveCategories() {
//
//        Integer page = 0;
//        Integer size = 10;
//
//        Pageable pageable = PageRequest.of(page, size);
//
//        // Create an empty list of categories
//        List<Category> noActiveCategories = List.of();
//
//        // Create a Page object for no active categories
//        Page<Category> noActiveCategoryPage = new PageImpl<>(noActiveCategories, pageable, noActiveCategories.size());
//
//        when(categoryRepository.findAll(pageable)).thenReturn(noActiveCategoryPage);
//
//        Page<com.thinkpalm.thinkfood.model.Category> categoryModelPage = categoryService.getAllCategoryByPagination(page, size);
//
//        assertNotNull(categoryModelPage);
//        assertTrue(categoryModelPage.getContent().isEmpty());
//    }
//}

/**
 * Service interface for managing categories.
 * This interface declares methods for CRUD operations on category entities.
 *
 * @author agrah.mv
 * @since 26 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.CategoryNotFoundException;
import com.thinkpalm.thinkfood.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


/**
 * Service interface for managing categories.
 */
public interface CategoryService {





    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return The Category object representing the category with the given ID.
     * @throws CategoryNotFoundException If the category with the specified ID is not found.
     */
    Category getCategoryWithId(Long id) throws CategoryNotFoundException;

    /**
     * Retrieves a list of all categories.
     *
     * @return List of Category objects representing all categories.
     */
    List<Category> getAllCategory();

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     * @return The Category object representing the deleted category.
     * @throws CategoryNotFoundException If the category with the specified ID is not found.
     */
    Category deleteCategoryById(Long id) throws CategoryNotFoundException;

    /**
     * Deletes all categories.
     */
    void deleteAllCategory();

    /**
     * Retrieves a paginated list of all categories.
     *
     * @param page The page number.
     * @param size The number of categories per page.
     * @return Paginated list of Category objects representing all categories.
     */
    Page<Category> getAllCategoryByPagination(Integer page, Integer size);

    /**
     * Creates a new category.
     *
     * @param category The Category object containing details of the new category.
     * @return The created Category object.
     * @throws CategoryNotFoundException If the category cannot be created.
     */
    Category createCategory(Category category) throws CategoryNotFoundException;

    /**
     * Updates the details of an existing category identified by its ID.
     *
     * @param id             The ID of the category to update.
     * @param updatedCategory The updated category object with new details.
     * @return The updated Category object representing the modified category.
     * @throws CategoryNotFoundException If the category with the specified ID is not found.
     */
    Category updateCategoryById(Long id, Category updatedCategory) throws CategoryNotFoundException;

    /**
     * Soft deletes a category by marking it as inactive.
     *
     * @param id The ID of the category to soft delete.
     * @return A message indicating the success of the soft deletion.
     * @throws CategoryNotFoundException If the category with the specified ID is not found.
     */
    String softDeleteCategoryById(Long id) throws CategoryNotFoundException;

//    Category createCategoryByImageUpload(Category category, MultipartFile imageFile) throws CategoryNotFoundException;
//
//    byte[] getCategoryWithImage(Long id) throws CategoryNotFoundException;
}

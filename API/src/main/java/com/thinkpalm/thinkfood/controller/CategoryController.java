/**
 * Controller for managing category operations.
 *
 * This controller handles various operations related to categories, including creating, retrieving,
 * updating, and deleting categories. It also provides methods for retrieving all categories,
 * paginated category retrieval, and both soft and permanent deletion of categories.
 *
 * @author agrah.mv
 * @since 26 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkpalm.thinkfood.exception.CategoryNotFoundException;
import com.thinkpalm.thinkfood.model.Category;
import com.thinkpalm.thinkfood.repository.CategoryRepository;
import com.thinkpalm.thinkfood.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/think-food/category")
@SecurityRequirement(name="thinkfood")
public class CategoryController {

    @Autowired private CategoryService categoryService;
    @Autowired private CategoryRepository categoryRepository;



    /**
     * Create a new category.
     *
     * @param category The category details to create.
     * @return A response entity containing the created category details.
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {
        try {
            log.info("Entered into createCategory method");
            Category createdCategory = categoryService.createCategory(category);
            return ResponseEntity.ok(createdCategory);
        }catch(CategoryNotFoundException e){
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }


    /**
     * Get a category by ID.
     *
     * @param id The ID of the category to retrieve.
     * @return A response entity containing the category details.
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getCategoryWithId(@PathVariable Long id) {
        try {
            log.info("Entered into getCategoryWithId method");
            Category category = categoryService.getCategoryWithId(id);
            return ResponseEntity.ok(category);
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Get all categories.
     *
     * @return A response entity containing a list of all categories.
     */
    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllCategory() {
        try {
            log.info("Entered into getAllCategory method");
            List<Category> categoryList = categoryService.getAllCategory();
            return ResponseEntity.ok(categoryList);
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Get all categories with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of categories per page.
     * @return A response entity containing a paginated list of categories.
     */
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Object> getAllCategoryByPagination(
            @PathVariable Integer page,
            @PathVariable Integer size) {
        try {
            log.info("Entered into getAllCategoryByPagination method");
            Page<Category> categoryPage = categoryService.getAllCategoryByPagination(page, size);
            return ResponseEntity.ok(categoryPage);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("An error occurred while fetching categories.");
        }
    }

    /**
     * Update a category by ID.
     *
     * @param id             The ID of the category to update.
     * @param updatedCategory The updated category details.
     * @return A response entity containing the updated category details.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCategoryById(@PathVariable Long id, @RequestBody Category updatedCategory) {
        try {
            log.info("Entered into updateCategoryById method");
            Category category = categoryService.updateCategoryById(id, updatedCategory);
            return ResponseEntity.ok(category);
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Delete a category by ID.
     *
     * @param id The ID of the category to be deleted.
     * @return A message indicating the success or failure of the delete operation.
     */
    @DeleteMapping("/delete/{id}")
    public String deleteCategoryById(@PathVariable Long id) {
        try {
            log.info("Entered into deleteCategoryById method");
            categoryService.deleteCategoryById(id);
            return "Category deleted successfully";
        }catch(CategoryNotFoundException e){
            log.error(e.getMessage());
            return "Invalid Support Id";
        }
    }

    /**
     * Soft delete a category by ID.
     *
     * @param id The ID of the category to be soft-deleted.
     * @return A message indicating the success or failure of the soft delete operation.
     */
    @DeleteMapping("/soft-delete/{id}")
    public Boolean softDeleteCategoryById(@PathVariable Long id) {
        try {
            log.info("Entered into softDeleteCategoryById method");
            categoryService.softDeleteCategoryById(id);
            return true;
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return false;
        }
    }


    /**
     * Delete all categories.
     *
     * @return A message indicating the success or failure of the delete operation.
     */
    @DeleteMapping("/delete-all")
    public String deleteAllCategory() {
        log.info("Entered into deleteAllCategory method");
        categoryService.deleteAllCategory();
        return "All categories deleted successfully";
    }

//    @PostMapping("/create-image")
//    public ResponseEntity<Object> createCategoryByImageUpload(@RequestPart("category") String category, @RequestPart("image") MultipartFile image) {
//        try {
//            // Call the service method to create a new category with image upload
//            log.info("entering createCategoryByImageUpload method");
//
//            Category createdCategory = categoryService.createCategoryByImageUpload(getCategoryJSON(category), image);
//            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
//        } catch (CategoryNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//    public Category getCategoryJSON(String category){
//        Category categoryObj = new Category();
//        try{
//            ObjectMapper objectMapper = new ObjectMapper();
//            categoryObj = objectMapper.readValue(category,Category.class);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return categoryObj;
//    }
//
//    @GetMapping("/{categoryId}/details")
//    public ResponseEntity<byte[]> getCategoryWithImage(@PathVariable Long categoryId) {
//        try {
//            byte[] imageData = categoryService.getCategoryWithImage(categoryId);
//
//            // Determine the content type based on the file extension
//            String contentType = determineContentType(categoryId);
//
//            // Set the Content-Type header
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType(contentType));
//
//            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
//        } catch (CategoryNotFoundException e) {
//            log.error("Error retrieving category details and image: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage().getBytes());
//        }
//    }
//
//    private String determineContentType(Long categoryId) {
//        // Retrieve the Category entity from the repository
//        com.thinkpalm.thinkfood.entity.Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + categoryId + " not found"));
//
//        // Extract the file extension from the image file name and determine the content type
//        String imageName = category.getImage();
//
//        if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
//            return "image/jpeg";
//        } else if (imageName.endsWith(".png")) {
//            return "image/png";
//        } else {
//            // Default to "image/jpeg" if the file extension is unknown or not supported
//            return "image/jpeg";
//        }
//    }


}



/**
 * Service implementation for managing categories.
 * This class is responsible for handling CRUD operations on category entities.
 *
 * @author agrah.mv
 * @since 26 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.Category;


import com.thinkpalm.thinkfood.exception.CategoryNotFoundException;
import com.thinkpalm.thinkfood.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service implementation for managing categories.
 */
@Service
@Log4j2
public class CategoryServiceImplementation implements CategoryService {


    private static final String UPLOAD_DIR = "/Users/agrah/Downloads/connect-postgresql-db/daksar/API/src/main/java/com/thinkpalm/thinkfood/images";

    @Autowired private CategoryRepository categoryRepository;

    /**
     * Creates a new category.
     *
     * @param category The Category object containing details of the new category.
     * @return The created Category object.
     * @throws CategoryNotFoundException If the category cannot be created.
     */
//    @Override
//    public com.thinkpalm.thinkfood.model.Category createCategory(com.thinkpalm.thinkfood.model.Category category) throws CategoryNotFoundException {
//        log.info("Creating a new category");
//
//        if (category.getCategoryName() == null | category.getCategoryName().isEmpty())
//            throw new CategoryNotFoundException("category name should not be null or empty");
//        if (category.getImage() == null || category.getImage().isEmpty())
//            throw new CategoryNotFoundException("Image URL should not be null or empty");
//
//        // Check if a category with the same name already exists and is active
//        Optional<Category> existingCategory = categoryRepository.findByCategoryNameAndIsActiveTrue(category.getCategoryName());
//        if (existingCategory.isPresent()) {
//            throw new CategoryNotFoundException("Active category with name '" + category.getCategoryName() + "' already exists");
//        }
//
//
//        Category categoryEntity = new Category();
//        categoryEntity.setCategoryName(category.getCategoryName());
//        categoryEntity.setImage(category.getImage());
//        categoryEntity.setIsActive(true);
//
//
//        categoryEntity = categoryRepository.save(categoryEntity);
//
//        com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
//        BeanUtils.copyProperties(categoryEntity, categoryModel);
//
//        log.info("Category created successfully");
//        return categoryModel;
//    }

    @Override
    public com.thinkpalm.thinkfood.model.Category createCategory(com.thinkpalm.thinkfood.model.Category category) throws CategoryNotFoundException {
        log.info("Creating a new category");

        if (category.getCategoryName() == null | category.getCategoryName().isEmpty())
            throw new CategoryNotFoundException("category name should not be null or empty");
        if (category.getImage() == null || category.getImage().isEmpty())
            throw new CategoryNotFoundException("Image URL should not be null or empty");

        // 1. Convert category name to lowercase and remove white spaces
        String userInputCategoryName = category.getCategoryName().toLowerCase().replaceAll("\\s", "");

        // 2. Get all active categories from the database
        List<Category> activeCategories = categoryRepository.findByIsActive(true);

        // 3. Check for duplicate category names
        for (Category activeCategory : activeCategories) {
            if (activeCategory.getCategoryName().toLowerCase().replaceAll("\\s", "").equals(userInputCategoryName)) {
                throw new CategoryNotFoundException("Category name already exists");
            }
        }

        Category categoryEntity = new Category();
        categoryEntity.setCategoryName(category.getCategoryName());
        categoryEntity.setImage(category.getImage());
        categoryEntity.setIsActive(true);


        categoryEntity = categoryRepository.save(categoryEntity);

        com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
        BeanUtils.copyProperties(categoryEntity, categoryModel);

        log.info("Category created successfully");
        return categoryModel;
    }



    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return The Category object representing the category with the given ID.
     * @throws CategoryNotFoundException If the category with the specified ID is not found.
     */
    @Override
    public com.thinkpalm.thinkfood.model.Category getCategoryWithId(Long id) throws CategoryNotFoundException {
        log.info("Retrieving category with ID: " + id);

        Optional<Category> categoryEntityWrapper = categoryRepository.findById(id);
        if (categoryEntityWrapper.isEmpty()) {
            throw new CategoryNotFoundException("Category is not found with id: " + id);
        }

        Category categoryEntity = categoryEntityWrapper.get();

        if (!categoryEntity.getIsActive()) {
            throw new CategoryNotFoundException("Category is not active with id: " + id);
        }

        com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
        BeanUtils.copyProperties(categoryEntity, categoryModel);

        log.info("Category with ID " + id + " retrieved successfully");
        return categoryModel;

    }

    /**
     * Retrieves a list of all categories.
     *
     * @return List of Category objects representing all categories.
     */
    @Override
    public List<com.thinkpalm.thinkfood.model.Category> getAllCategory() {
        log.info("Retrieving all categories");

        List<com.thinkpalm.thinkfood.model.Category> categoryModelList = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            if (category.getIsActive()) {
                com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
                BeanUtils.copyProperties(category, categoryModel);
                categoryModelList.add(categoryModel);
            }
        }

        log.info("All categories retrieved successfully");
        return categoryModelList;
    }

    /**
     * Retrieves a paginated list of all categories.
     *
     * @param page The page number.
     * @param size The number of categories per page.
     * @return Paginated list of Category objects representing all categories.
     */
    @Override
    public Page<com.thinkpalm.thinkfood.model.Category> getAllCategoryByPagination(Integer page, Integer size) {
        log.info("Retrieving paginated categories - Page: " + page + ", Size: " + size);
        // Create a Pageable object to represent the pagination information
        Pageable pageable = PageRequest.of(page, size);

        // Retrieve only active categories directly from the repository
        Page<com.thinkpalm.thinkfood.model.Category> activeCategoryModelPage = categoryRepository.findByIsActiveTrue(pageable)
                .map(category -> {
                    com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
                    BeanUtils.copyProperties(category, categoryModel);
                    return categoryModel;
                });

        log.info("Paginated categories retrieved successfully");
        return activeCategoryModelPage;
    }


    /**
     * Updates the details of an existing category identified by its ID.
     *
     * @param id             The ID of the category to update.
     * @param updatedCategory The updated category object with new details.
     * @return The updated Category object representing the modified category.
     * @throws CategoryNotFoundException If the category with the specified ID is not found.
     */
//    @Override
//    public com.thinkpalm.thinkfood.model.Category updateCategoryById(Long id, com.thinkpalm.thinkfood.model.Category updatedCategory) throws CategoryNotFoundException {
//        log.info("Updating category with ID: " + id);
//
//        Optional<Category> categoryEntityOptional = categoryRepository.findById(id);
//
//        if (categoryEntityOptional.isEmpty()) {
//            throw new CategoryNotFoundException("Category not found with ID: " + id);
//        }
//
//        Category categoryEntity = categoryEntityOptional.get();
//
//        if (categoryEntity.getIsActive()) {
//
//            // Check if an active category with the updated name already exists
//            if (updatedCategory.getCategoryName() != null && !updatedCategory.getCategoryName().isEmpty()) {
//                Optional<Category> existingCategory = categoryRepository.findByCategoryNameAndIsActiveTrue(updatedCategory.getCategoryName());
//                if (existingCategory.isPresent() && !existingCategory.get().getId().equals(id)) {
//                    throw new CategoryNotFoundException("Active category with name '" + updatedCategory.getCategoryName() + "' already exists");
//                }
//            }
//
//            if (updatedCategory.getCategoryName() != null && !updatedCategory.getCategoryName().isEmpty() && updatedCategory.getImage() != null && !updatedCategory.getImage().isEmpty()) {
//                categoryEntity.setCategoryName(updatedCategory.getCategoryName());
//                categoryEntity.setImage(updatedCategory.getImage());
//
//                categoryEntity = categoryRepository.save(categoryEntity);
//
//                com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
//                BeanUtils.copyProperties(categoryEntity, categoryModel);
//
//                log.info("Category with ID " + id + " updated successfully");
//
//                return categoryModel;
//
//            } else {
//                throw new CategoryNotFoundException("Category name and image url must be provided properly for the update");
//            }
//        } else {
//            throw new CategoryNotFoundException("Cannot update an inactive category");
//        }
//    }

    @Override
    public com.thinkpalm.thinkfood.model.Category updateCategoryById(Long id, com.thinkpalm.thinkfood.model.Category updatedCategory) throws CategoryNotFoundException {
        log.info("Updating category with ID: " + id);

        Optional<Category> categoryEntityOptional = categoryRepository.findById(id);

        if (categoryEntityOptional.isEmpty()) {
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        }

        Category categoryEntity = categoryEntityOptional.get();

        if (categoryEntity.getIsActive()) {

            // 1. Convert updated category name to lowercase and remove white spaces
            String updatedCategoryName = updatedCategory.getCategoryName().toLowerCase().replaceAll("\\s", "");

            // 2. Get all active categories from DB and process their names
            List<Category> activeCategories = categoryRepository.findByIsActive(true);
            for (Category activeCategory : activeCategories) {
                // Convert category name to lowercase and remove white spaces
                String activeCategoryName = activeCategory.getCategoryName().toLowerCase().replaceAll("\\s", "");

                // 3. Check for a match excluding the current category being updated
                if (!activeCategory.getId().equals(id) && activeCategoryName.equals(updatedCategoryName)) {
                    throw new CategoryNotFoundException("Category name '" + updatedCategory.getCategoryName() + "' already exists.");
                }
            }


            if (updatedCategory.getCategoryName() != null && !updatedCategory.getCategoryName().isEmpty() && updatedCategory.getImage() != null && !updatedCategory.getImage().isEmpty()) {
                categoryEntity.setCategoryName(updatedCategory.getCategoryName());
                categoryEntity.setImage(updatedCategory.getImage());

                categoryEntity = categoryRepository.save(categoryEntity);

                com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
                BeanUtils.copyProperties(categoryEntity, categoryModel);

                log.info("Category with ID " + id + " updated successfully");

                return categoryModel;

            } else {
                throw new CategoryNotFoundException("Category name and image url must be provided properly for the update");
            }
        } else {
            throw new CategoryNotFoundException("Cannot update an inactive category");
        }
    }


    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     * @return The Category object representing the deleted category.
     * @throws CategoryNotFoundException If the category with the specified ID is not found.
     */
    @Override
    public com.thinkpalm.thinkfood.model.Category deleteCategoryById(Long id) throws CategoryNotFoundException {
        log.info("Deleting category with ID: " + id);

        Optional<Category> entity = categoryRepository.findById(id);
        if (entity.isEmpty()) {
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        }
        Category deletedCategory = entity.get();
        com.thinkpalm.thinkfood.model.Category deletedModel = new com.thinkpalm.thinkfood.model.Category();
        BeanUtils.copyProperties(deletedCategory, deletedModel);
        categoryRepository.deleteById(id);

        log.info("Category with ID " + id + " deleted successfully");
        return deletedModel;
    }

    /**
     * Soft deletes a category by marking it as inactive.
     *
     * @param id The ID of the category to soft delete.
     * @return A message indicating the success of the soft deletion.
     * @throws CategoryNotFoundException If the category with the specified ID is not found.
     */
    @Override
    public String softDeleteCategoryById(Long id) throws CategoryNotFoundException {
        log.info("Soft deleting category with ID: " + id);

        Category entity = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
        entity.setIsActive(false);
        categoryRepository.save(entity);

        log.info("Category with ID " + id + " has been soft deleted");
        return "Category With Id "+id+" deleted successfully!";
    }


    /**
     * Deletes all categories.
     */
    @Override
    public void deleteAllCategory(){
        categoryRepository.deleteAll();
    }
//
//    @Override
//    public com.thinkpalm.thinkfood.model.Category createCategoryByImageUpload(com.thinkpalm.thinkfood.model.Category category, MultipartFile image) throws CategoryNotFoundException {
//        log.info("Creating a new category");
//
//        if (category.getCategoryName() == null || category.getCategoryName().isEmpty())
//            throw new CategoryNotFoundException("Category name should not be null or empty");
//        if (image == null || image.isEmpty())
//            throw new CategoryNotFoundException("Image file should not be null or empty");
//
//        // Check if a category with the same name already exists and is active
//        Optional<Category> existingCategory = categoryRepository.findByCategoryNameAndIsActiveTrue(category.getCategoryName());
//        if (existingCategory.isPresent()) {
//            throw new CategoryNotFoundException("Active category with name '" + category.getCategoryName() + "' already exists");
//        }
//
//        // Upload the image and get the image URL
//        String imageUrl = uploadImage(image);
//
//        Category categoryEntity = new Category();
//        categoryEntity.setCategoryName(category.getCategoryName());
//        categoryEntity.setImage(imageUrl);
//        categoryEntity.setIsActive(true);
//
//        categoryEntity = categoryRepository.save(categoryEntity);
//
//        com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
//        BeanUtils.copyProperties(categoryEntity, categoryModel);
//
//        log.info("Category created successfully");
//        return categoryModel;
//    }
//
//    private String uploadImage(MultipartFile image) {
//        try {
//            // Generate a unique file name or use the original file name
//            String fileName = image.getOriginalFilename();
//
//            // Resolve the file path where you want to store the uploaded image
//            Path targetPath = Path.of(UPLOAD_DIR, fileName);
//
//            // Copy the image file to the target path
//            Files.copy(image.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
//
//            // Return the image URL (you may need to adjust the URL based on your project setup)
//            return  fileName;  // Example URL, adjust as needed
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to upload image", e);
//        }
//    }
//
//    @Override
//    public byte[] getCategoryWithImage(Long id) throws CategoryNotFoundException {
//        log.info("Retrieving category with ID: " + id);
//
//        Optional<Category> categoryEntityWrapper = categoryRepository.findById(id);
//        if (categoryEntityWrapper.isEmpty()) {
//            throw new CategoryNotFoundException("Category is not found with id: " + id);
//        }
//
//        Category categoryEntity = categoryEntityWrapper.get();
//
//        if (!categoryEntity.getIsActive()) {
//            throw new CategoryNotFoundException("Category is not active with id: " + id);
//        }
//
//        // Get category details
//        com.thinkpalm.thinkfood.model.Category categoryModel = new com.thinkpalm.thinkfood.model.Category();
//        BeanUtils.copyProperties(categoryEntity, categoryModel);
//
//        // Get image as byte array
//        byte[] imageData = getImageData(categoryEntity.getImage());
//
//        log.info("Category details and image retrieved successfully");
//        return imageData;
//    }
//
//    private byte[] getImageData(String imageName) {
//        try {
//            // Resolve the file path where the image is stored
//            Path imagePath = Path.of(UPLOAD_DIR, imageName);
//
//            // Read the image file into a byte array
//            return Files.readAllBytes(imagePath);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read image data", e);
//        }
//    }

}

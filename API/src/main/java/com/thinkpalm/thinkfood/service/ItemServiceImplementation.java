/**
 * Service implementation for managing items.
 * This class is responsible for handling CRUD operations on item entities.
 *
 * @author agrah.mv
 * @since 27 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.Category;
import com.thinkpalm.thinkfood.entity.Item;
import com.thinkpalm.thinkfood.exception.CategoryNotFoundException;
import com.thinkpalm.thinkfood.exception.ItemDetailsMissingException;
import com.thinkpalm.thinkfood.exception.ItemNotFoundException;
import com.thinkpalm.thinkfood.repository.CategoryRepository;
import com.thinkpalm.thinkfood.repository.ItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Implementation of the ItemService interface for managing items.
 */
@Service
@Log4j2
public class ItemServiceImplementation implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a new item and associates it with the specified category.
     *
     * @param item       The Item object containing details of the item.
     * @param categoryId The ID of the category to which the item belongs.
     * @return The created Item object.
     * @throws ItemDetailsMissingException If details of the item are missing.
     * @throws CategoryNotFoundException   If the specified category is not found.
     */
//    @Override
//    public com.thinkpalm.thinkfood.model.Item createItem(com.thinkpalm.thinkfood.model.Item item, Long categoryId)
//            throws ItemDetailsMissingException, CategoryNotFoundException {
//        log.info("creating item");
//
//        if (item.getItemName() == null  | item.getItemName().isEmpty()) {
//            throw new ItemDetailsMissingException("Item name should not be null or empty");
//        }
//        if (item.getImage() == null || item.getImage().isEmpty())
//            throw new ItemDetailsMissingException("Image URL should not be null or empty");
//
//
//        // Retrieve the associated category and check if it's active
//        Category categoryEntity = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
//
//        if (!categoryEntity.getIsActive()) {
//            throw new CategoryNotFoundException("Category is not active with ID: " + categoryId);
//        }
//
//        // Check if an active item with the same name already exists
//        Optional<Item> existingItem = itemRepository.findByItemNameAndIsActiveTrue(item.getItemName());
//        if (existingItem.isPresent()) {
//            throw new ItemDetailsMissingException("Active item with name '" + item.getItemName() + "' already exists");
//        }
//
//        Item itemEntity = new Item();
//        itemEntity.setItemName(item.getItemName());
//        itemEntity.setImage(item.getImage());
//        itemEntity.setIsActive(true);
//
//        // Set the associated category
//        itemEntity.setCategory(categoryEntity);
//
//        // Save the new item entity to the repository
//        itemEntity = itemRepository.save(itemEntity);
//
//        com.thinkpalm.thinkfood.model.Item itemModel = new com.thinkpalm.thinkfood.model.Item();
//        BeanUtils.copyProperties(itemEntity, itemModel);
//
//        log.info("item created successfully");
//        return itemModel;
//    }

    @Override
    public com.thinkpalm.thinkfood.model.Item createItem(com.thinkpalm.thinkfood.model.Item item, Long categoryId)
            throws ItemDetailsMissingException, CategoryNotFoundException {
        log.info("creating item");

        if (item.getItemName() == null  | item.getItemName().isEmpty()) {
            throw new ItemDetailsMissingException("Item name should not be null or empty");
        }
        if (item.getImage() == null | item.getImage().isEmpty())
            throw new ItemDetailsMissingException("Image URL should not be null or empty");

        // Retrieve the associated category and check if it's active
        Category categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));

        if (!categoryEntity.getIsActive()) {
            throw new CategoryNotFoundException("Category is not active with ID: " + categoryId);
        }

        // 1. Convert item name to lowercase and remove white spaces
        String userInputItemName = item.getItemName().toLowerCase().replaceAll("\\s", "");

        // 2. Get all active items from the database
        List<Item> activeItems = itemRepository.findByIsActive(true);

        // 3. Check for duplicate item names
        for (Item activeItem : activeItems) {
            if (activeItem.getItemName().toLowerCase().replaceAll("\\s", "").equals(userInputItemName)) {
                throw new ItemDetailsMissingException("Item name already exists");
            }
        }

        Item itemEntity = new Item();
        itemEntity.setItemName(item.getItemName());
        itemEntity.setImage(item.getImage());
        itemEntity.setIsActive(true);

        // Set the associated category
        itemEntity.setCategory(categoryEntity);

        // Save the new item entity to the repository
        itemEntity = itemRepository.save(itemEntity);

        com.thinkpalm.thinkfood.model.Item itemModel = new com.thinkpalm.thinkfood.model.Item();
        BeanUtils.copyProperties(itemEntity, itemModel);

        log.info("item created successfully");
        return itemModel;
    }


    /**
     * Retrieves all items belonging to a specific category.
     *
     * @param categoryId The ID of the category.
     * @return List of items belonging to the specified category.
     * @throws CategoryNotFoundException If the specified category is not found or is inactive.
     */
    @Override
    public List<com.thinkpalm.thinkfood.model.Item> getAllItemsByCategory(Long categoryId) {
        log.info("retrieving all items by category ID");

        Category categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
        if (categoryEntity.getIsActive()) {
            List<Item> items = itemRepository.findByCategory(categoryEntity);

            List<com.thinkpalm.thinkfood.model.Item> itemModels = new ArrayList<>();
            for (Item item : items) {
                // Check if the item is active
                if (item.getIsActive()) {
                    com.thinkpalm.thinkfood.model.Item itemModel = new com.thinkpalm.thinkfood.model.Item();
                    BeanUtils.copyProperties(item, itemModel);
                    itemModels.add(itemModel);
                }
            }

            log.info("retrieved all items by category ID successfully");
            return itemModels;
        } else {
            throw new CategoryNotFoundException("Category is not active with ID: " + categoryId);
        }
    }

    /**
     * Retrieves a paginated list of items belonging to a specific category.
     *
     * @param categoryId The ID of the category.
     * @param page       The page number.
     * @param pageSize   The number of items per page.
     * @return Paginated list of items belonging to the specified category.
     * @throws CategoryNotFoundException If the specified category is not found or is inactive.
     */
    @Override
    public Page<com.thinkpalm.thinkfood.model.Item> getAllItemsByCategoryPagination(Long categoryId, Integer page, Integer pageSize) {
        log.info("retrieving all items by category ID");

        Category categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));

        // Check if the category is active
        if (categoryEntity.getIsActive()) {
            // Create a PageRequest object for pagination
            PageRequest pageRequest = PageRequest.of(page, pageSize);

            Page<Item> items = itemRepository.findByCategory(categoryEntity, pageRequest);

            List<com.thinkpalm.thinkfood.model.Item> itemModels = new ArrayList<>();
            for (Item item : items) {
                // Check if the item is active
                if (item.getIsActive()) {
                    com.thinkpalm.thinkfood.model.Item itemModel = new com.thinkpalm.thinkfood.model.Item();
                    BeanUtils.copyProperties(item, itemModel);
                    itemModels.add(itemModel);
                }
            }

            log.info("retrieved all items by category ID successfully");
            return new PageImpl<>(itemModels, pageRequest, itemModels.size());
        } else {
            throw new CategoryNotFoundException("Category is not active with ID: " + categoryId);
        }
    }




//    @Override
//    public Page<com.thinkpalm.thinkfood.model.Item> getAllItemsByCategoryPagination(Long categoryId, Integer page, Integer pageSize) {
//        Category categoryEntity = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
//
//        // Create a PageRequest object for pagination
//        PageRequest pageRequest = PageRequest.of(page, pageSize);
//
//        Page<Item> items = itemRepository.findByCategory(categoryEntity, pageRequest);
//
//        // Convert the Page of items to a Page of item models
//        Page<com.thinkpalm.thinkfood.model.Item> itemModels = items.map(item -> {
//            com.thinkpalm.thinkfood.model.Item itemModel = new com.thinkpalm.thinkfood.model.Item();
//            BeanUtils.copyProperties(item, itemModel);
//            return itemModel;
//        });
//
//        return itemModels;
//    }

    /**
     * Deletes an item by its ID.
     *
     * @param id The ID of the item to delete.
     * @throws ItemNotFoundException If the item with the specified ID is not found.
     */
    @Override
    public void deleteItemById(Long id) throws ItemNotFoundException {
        log.info("deleting item");
        // Check if the item exists, and if it does, delete it
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
        } else {
            throw new ItemNotFoundException("Item not found with ID: " + id);
        }
    }

    /**
     * Soft deletes an item by marking it as inactive.
     *
     * @param id The ID of the item to soft delete.
     * @return A message indicating the success of the soft deletion.
     * @throws ItemNotFoundException If the item with the specified ID is not found.
     */
    @Override
    public String softDeleteItemById(Long id) throws ItemNotFoundException {
        log.info("soft deleting item by item ID");

        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item with ID " + id + " not found"));
        item.setIsActive(false);

        itemRepository.save(item);

        log.info("Item soft deleted");
        return "Item With Id "+id+" deleted successfully!";

    }

    /**
     * Updates the details of an existing item identified by its ID.
     *
     * @param id          The ID of the item to update.
     * @param updatedItem The updated item object with new details.
     * @return The updated Item object representing the modified item.
     * @throws ItemNotFoundException If the item with the specified ID is not found.
     */
//    @Override
//    public com.thinkpalm.thinkfood.model.Item updateItemById(Long id, com.thinkpalm.thinkfood.model.Item updatedItem) throws ItemNotFoundException {
//        log.info("updating item by item ID");
//        Optional<Item> itemEntityOptional = itemRepository.findById(id);
//
//        if (itemEntityOptional.isEmpty()) {
//            throw new ItemNotFoundException("Item not found with ID: " + id);
//        }
//
//        Item itemEntity = itemEntityOptional.get();
//
//        // Check if the category is active before allowing the update
//        if (itemEntity.getIsActive()) {
//
//            // Check if an active item with the updated name already exists
//            if (updatedItem.getItemName() != null && !updatedItem.getItemName().isEmpty()) {
//                Optional<Item> existingItem = itemRepository.findByItemNameAndIsActiveTrue(updatedItem.getItemName());
//                if (existingItem.isPresent() && !existingItem.get().getId().equals(id)) {
//                    throw new ItemNotFoundException("Active item with name '" + updatedItem.getItemName() + "' already exists");
//                }
//            }
//
//            if (updatedItem.getItemName() != null && !updatedItem.getItemName().isEmpty() && updatedItem.getImage() != null && !updatedItem.getImage().isEmpty()) {
//                itemEntity.setItemName(updatedItem.getItemName());
//                itemEntity.setImage(updatedItem.getImage());
//
//                // Save the updated entity back to the database
//                itemEntity = itemRepository.save(itemEntity);
//
//                com.thinkpalm.thinkfood.model.Item itemModel = new com.thinkpalm.thinkfood.model.Item();
//                BeanUtils.copyProperties(itemEntity, itemModel);
//
//                log.info("item updated successfully");
//                return itemModel;
//            } else {
//                throw new ItemNotFoundException("Item name must be provided properly for the update");
//            }
//        } else {
//            throw new ItemNotFoundException("Cannot update an inactive item");
//        }
//    }

    @Override
    public com.thinkpalm.thinkfood.model.Item updateItemById(Long id, com.thinkpalm.thinkfood.model.Item updatedItem) throws ItemNotFoundException {
        log.info("Updating item by item ID");
        Optional<Item> itemEntityOptional = itemRepository.findById(id);

        if (itemEntityOptional.isEmpty()) {
            throw new ItemNotFoundException("Item not found with ID: " + id);
        }

        Item itemEntity = itemEntityOptional.get();

        // Check if the category is active before allowing the update
        if (itemEntity.getIsActive()) {

            // 1. Convert updated item name to lowercase and remove white spaces
            String updatedItemName = updatedItem.getItemName().toLowerCase().replaceAll("\\s", "");

            // 2. Get all active items from DB and process their names
            List<Item> activeItems = itemRepository.findByIsActive(true);
            for (Item activeItem : activeItems) {
                // Convert item name to lowercase and remove white spaces
                String activeItemName = activeItem.getItemName().toLowerCase().replaceAll("\\s", "");

                // 3. Check for a match excluding the current category being updated
                if (!activeItem.getId().equals(id) && activeItemName.equals(updatedItemName)) {
                    throw new ItemNotFoundException("Item name '" + updatedItem.getItemName() + "' already exists.");
                }
            }

            if (updatedItem.getItemName() != null && !updatedItem.getItemName().isEmpty() && updatedItem.getImage() != null && !updatedItem.getImage().isEmpty()) {
                itemEntity.setItemName(updatedItem.getItemName());
                itemEntity.setImage(updatedItem.getImage());

                // Save the updated entity back to the database
                itemEntity = itemRepository.save(itemEntity);

                com.thinkpalm.thinkfood.model.Item itemModel = new com.thinkpalm.thinkfood.model.Item();
                BeanUtils.copyProperties(itemEntity, itemModel);

                log.info("Item updated successfully");
                return itemModel;
            } else {
                throw new ItemNotFoundException("Item name and image URL must be provided properly for the update");
            }
        } else {
            throw new ItemNotFoundException("Cannot update an inactive item");
        }
    }
}

/**
 * Service implementation for managing support queries.
 * This class is responsible for handling CRUD operations on support query entities.
 *
 * @author agrah.mv
 * @since 31 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.Support;
import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.InactiveSupportException;
import com.thinkpalm.thinkfood.exception.SupportNotFoundException;
import com.thinkpalm.thinkfood.model.Support1;
import com.thinkpalm.thinkfood.repository.SupportRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of the SupportService interface for managing support queries.
 */
@Service
@Log4j2
public class SupportServiceImplementation implements SupportService{
    @Autowired
    private SupportRepository supportRepository;

    /**
     * Creates a new support query.
     *
     * @param support The Support object containing the details of the support query.
     * @return The created Support object.
     * @throws EmptyInputException If the input Support object is empty or null.
     */
    @Override
    public com.thinkpalm.thinkfood.model.Support createSupport(com.thinkpalm.thinkfood.model.Support support) throws EmptyInputException {
        log.info("Creating a new support");

        if (support.getQuery()==null || support.getResponse()==null ){
            throw new EmptyInputException("All Fields are Mandatory");
        }

        Support entity=new Support();
        entity.setQuery(support.getQuery());
        entity.setResponse(support.getResponse());
        entity.setIsActive(true);
        supportRepository.save(entity);

        com.thinkpalm.thinkfood.model.Support model=new com.thinkpalm.thinkfood.model.Support();
        model.setQuery(entity.getQuery());
        model.setResponse(entity.getResponse());

        log.info("Support created successfully");
        return model;
    }

    /**
     * Retrieves a map of all support queries with their corresponding IDs.
     *
     * @return A map where the key is the support query ID and the value is the query itself.
     */
    @Override
    public Map<Long, String> getAllQueries() {
        log.info("Retrieving all queries");

        Map<Long, String> modelMap = new HashMap<>();
        List<Support> supportList = supportRepository.findAll();

        for (Support support : supportList) {
            if (support.getIsActive()) {
                modelMap.put(support.getId(), support.getQuery());
            };
        }

        log.info("All queries retrieved successfully");
        return modelMap;
    }

    /**
     * Retrieves a support query by its ID.
     *
     * @param id The ID of the support query to retrieve.
     * @return The Support1 object representing the support query.
     * @throws SupportNotFoundException   If the support query with the given ID is not found.
     * @throws InactiveSupportException    If the support query with the given ID is inactive.
     */
    @Override
    public com.thinkpalm.thinkfood.model.Support1 getSupportById(Long id) throws SupportNotFoundException , InactiveSupportException{
        log.info("Retrieving support with ID: " + id);

        Optional<Support> entity=supportRepository.findById(id);

        if (entity.isEmpty()) {
            throw new SupportNotFoundException("Support not found with ID: " + id);
        }

        Support support = entity.get();
        if (!support.getIsActive()) {
            throw new InactiveSupportException("Support with ID: " + id + " is not active.");
        }

        Support1 support1 = new Support1();
        support1.setResponse(entity.get().getResponse());
        support1.setQuery(entity.get().getQuery());

        log.info("Support with ID " + id + " retrieved successfully");
        return support1;
    }

    /**
     * Deletes a support query by its ID.
     *
     * @param id The ID of the support query to delete.
     * @return The Support object representing the deleted support query.
     * @throws SupportNotFoundException If the support query with the given ID is not found.
     */
    @Override
    public com.thinkpalm.thinkfood.model.Support deleteSupportById(Long id) throws SupportNotFoundException {
        log.info("Deleting support with ID: " + id);

        Optional<Support> entity = supportRepository.findById(id);

        if (entity.isEmpty()) {
            throw new SupportNotFoundException("Support not found with ID: " + id);
        }

        Support deletedSupport = entity.get();

        com.thinkpalm.thinkfood.model.Support deletedModel = new com.thinkpalm.thinkfood.model.Support();
        BeanUtils.copyProperties(deletedSupport, deletedModel);

        supportRepository.deleteById(id);

        log.info("Support with ID " + id + " deleted successfully");
        return deletedModel;
    }

    /**
     * Soft deletes a support query by marking it as inactive.
     *
     * @param id The ID of the support query to soft delete.
     * @return A message indicating the success of the soft deletion.
     * @throws SupportNotFoundException If the support query with the given ID is not found.
     */
    @Override
    public String softDeleteSupportById(Long id) throws SupportNotFoundException {
        log.info("Soft deleting support with ID: " + id);

        Support entity = supportRepository.findById(id).orElseThrow(() -> new SupportNotFoundException("Support with ID " + id + " not found"));
        entity.setIsActive(false);

        supportRepository.save(entity);

        log.info("Support with ID " + id + " has been soft deleted");
        return "Support With Id "+id+" deleted successfully!";

    }
}

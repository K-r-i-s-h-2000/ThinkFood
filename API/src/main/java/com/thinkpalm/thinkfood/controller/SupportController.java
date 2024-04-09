/**
 * Controller for managing support operations.
 *
 * This controller handles various operations related to support.
 * It provides methods for creation, regular and soft deletion of support queries.
 * Additionally, it allows retrieving all queries and getting details of specific query by ID.
 *
 * @author agrah.mv
 * @since 31 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.InactiveSupportException;
import com.thinkpalm.thinkfood.exception.SupportNotFoundException;
import com.thinkpalm.thinkfood.model.Support;
import com.thinkpalm.thinkfood.model.Support1;
import com.thinkpalm.thinkfood.service.SupportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/think-food/support")
@SecurityRequirement(name="thinkfood")
public class SupportController{

    @Autowired
    private SupportService supportService;

    /**
     * Create support.
     *
     * @param support The support details to create.
     * @return A message indicating the success or failure of the create operation.
     */
    @PostMapping("/create")
    public Boolean createSupport(@RequestBody Support support) {
        try{
            log.info("Entered into create support controller method");
            supportService.createSupport(support);
            return true;
        }catch(EmptyInputException e){
            log.error("Invalid inputs in create support controller " +e.getMessage());
            return false;
        }
    }

    /**
     * Get all queries.
     *
     * @return A response entity containing a map of query IDs and their corresponding details.
     */
    @GetMapping("/queries")
    public ResponseEntity<Object> getAllQueries() {
        try {
            log.info("Entered into get all queries method");
            Map<Long, String> queries = supportService.getAllQueries();
            return ResponseEntity.ok(queries);
        } catch (Exception e) {
            log.error("An error occurred while retrieving queries.");
            return ResponseEntity.ok("An error occurred while retrieving queries.");
        }
    }


//    @GetMapping("/query/{id}")
//    public ResponseEntity<Object> getResponseById(@PathVariable Long id) {
//        try {
//            Support1 support = supportService.getResponseById(id);
//            return ResponseEntity.ok(support);
//        } catch (SupportNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Query ID invalid");
//        }
//    }

    /**
     * Get support by ID.
     *
     * @param id The ID of the support query to retrieve.
     * @return A response entity containing the details of the support query.
     */
    @GetMapping("/query/{id}")
    public ResponseEntity<Object> getSupportById(@PathVariable Long id) {
        try {
            log.info("Entered into get support by Id method");
            Support1 support = supportService.getSupportById(id);
            return ResponseEntity.ok(support);
        } catch (InactiveSupportException e) {
            log.error("Support is not active");
            return ResponseEntity.ok("Support is not active.");

        } catch (SupportNotFoundException e) {
            log.error("Query Id invalid");
            return ResponseEntity.ok("Query ID invalid");
        }
    }

    /**
     * Delete support by ID.
     *
     * @param id The ID of the support query to be deleted.
     * @return A message indicating the success or failure of the delete operation.
     */
    @DeleteMapping("/delete/{id}")
    public String deleteSupportById(@PathVariable Long id) {
        try {
            log.info("Entered into delete support by id method");
            supportService.deleteSupportById(id);
            return "Support with ID " + id + " has been deleted.";
        } catch (SupportNotFoundException e) {
            log.error("Invalid Support Id");
            return "Invalid Support Id";
        }
    }

    /**
     * Soft delete support by ID.
     *
     * @param id The ID of the support query to be soft-deleted.
     * @return A message indicating the success or failure of the soft delete operation.
     */
    @DeleteMapping("/soft-delete/{id}")
    public Boolean softDeleteSupportById(@PathVariable Long id) {
        try {
            log.info("Entered into soft delete support by id method");
            supportService.softDeleteSupportById(id);
            return true;
        } catch (SupportNotFoundException e) {
            log.error("Invalid Support Id");
            return false;
        }
    }

}

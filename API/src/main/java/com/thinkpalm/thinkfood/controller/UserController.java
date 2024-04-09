package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.ChangePasswordRequest;
import com.thinkpalm.thinkfood.model.DeletionRequest;
import com.thinkpalm.thinkfood.service.UserServiceImplementation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Log4j2

@RestController
@RequestMapping("/think-food/users")
@RequiredArgsConstructor
@SecurityRequirement(name="thinkfood")
public class UserController {

    private final UserServiceImplementation service;

    @PostMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        boolean isSuccessful= service.changePassword(request, connectedUser);
        return ResponseEntity.ok(isSuccessful);
    }
    /**
     * Endpoint for hard deleting a user.
     * Deletes a user based on the provided email.
     *
     * @param request A map containing the email of the user to be deleted.
     * @return The deletion request.
     */
    @DeleteMapping("/hard-delete")
    public DeletionRequest deleteUser(@RequestBody Map<String, String> request)  {
        String email = request.get("email");
        return service.deleteUser(email);
    }
    /**
     * Endpoint for soft deleting a customer.
     * Soft deletes a customer based on the provided ID.
     *
     * @param id The ID of the customer to be soft-deleted.
     * @return A response entity with the result of the soft delete operation.
     */
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDeleteCustomer(@PathVariable Long id) {
        try {
            var response = service.softDeleteUser(id);
            log.info("Delete for User with ID " + id + " was successful.");
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            log.error(" User not found for ID " + id + ": " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}

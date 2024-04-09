package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.ChangePasswordRequest;
import com.thinkpalm.thinkfood.model.DeletionRequest;

import java.security.Principal;

public interface UserService {
    /**
     * Delete a user by their email address.
     *
     * @param email The email address of the user to be deleted.
     * @return A deletion request response with a message indicating the deletion status.
     */
    public DeletionRequest deleteUser(String email);
    public Boolean softDeleteUser(Long id) throws NotFoundException;
    public boolean changePassword(ChangePasswordRequest request, Principal connectedUser);
}

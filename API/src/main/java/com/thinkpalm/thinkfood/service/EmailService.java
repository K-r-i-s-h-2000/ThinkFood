
/**
 * Service interface for handling email-related operations.
 * Provides methods for sending emails, registering users, updating status emails, and sending order confirmation emails.
 * since : 01 November 2023
 * version : 2.0
 */


package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.Order;
import com.thinkpalm.thinkfood.model.RegisterRequest;
import jakarta.mail.MessagingException;


public interface EmailService {
    /**
     * Sends an email with the specified parameters.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param body    The body/content of the email.
     * @throws MessagingException If there is an issue with sending the email.
     */
    void sendEmail(String to, String subject, String body) throws MessagingException;

    /**
     * Registers a user based on the provided registration request.
     *
     * @param request The registration request containing user information.
     */

    void registerUser(RegisterRequest request);

    /**
     * Updates the status email for the specified order ID.
     *
     * @param orderId The unique identifier of the order.
     */

    void updateStatusEmail(Long orderId);

    /**
     * Sends an order confirmation email for the given order.
     *
     * @param newOrder The order for which the confirmation email should be sent.
     * @return The updated order object.
     * @throws NotFoundException If the order is not found.
     */

    Order orderConfirmationEmail(Order newOrder) throws NotFoundException;
   public void sendForgotPasswordEmail( RegisterRequest registerRequest);
public String generateRandomPassword();

}
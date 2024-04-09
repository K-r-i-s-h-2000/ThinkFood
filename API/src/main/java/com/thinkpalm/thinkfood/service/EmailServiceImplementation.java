/**
 * Implementation of the {@link EmailService} interface that provides methods for sending emails,
 * registering users, updating status emails, and sending order confirmation emails.
 *
 * author: Sharon Sam
 * since : 01 November 2023
 * version : 2.0
 */


package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.*;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.RegisterRequest;
import com.thinkpalm.thinkfood.repository.*;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

;
;

@Service
public class EmailServiceImplementation implements EmailService {

    @Autowired
    private Session emailSession;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * Sends an email with the specified parameters using JavaMail.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param body    The body/content of the email.
     * @throws MessagingException If there is an issue with sending the email.
     */

    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = new MimeMessage(emailSession);
        message.setFrom(new InternetAddress("thinkfood765@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }


    /**
     * Registers a user based on the provided registration request and sends a registration email.
     *
     * @param request The registration request containing user information.
     */

    @Override
    public void registerUser(RegisterRequest request) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(1L).orElse(null);

        try {
            if (emailTemplate != null) {
                String emailSubject = emailTemplate.getSubject();
                String emailMessage = emailTemplate.getMessage();

                sendEmail(request.getEmail(), emailSubject, emailMessage);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    public void sendForgotPasswordEmail( RegisterRequest registerRequest) {

        EmailTemplate emailTemplate = emailTemplateRepository.findById(5L).orElse(null);
        String randomPassword = generateRandomPassword();
        try {
            if (emailTemplate != null) {
                String emailSubject = emailTemplate.getSubject();
                String emailMessage = emailTemplate.getMessage() + randomPassword;


                sendEmail(registerRequest.getEmail(), emailSubject, emailMessage);
                // Update the user's password in the database with the newly generated random password
                updatePasswordInDatabase(registerRequest.getEmail(), randomPassword);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // Generate a random password

    }
    private void updatePasswordInDatabase(String email, String newPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Encrypt the new password before saving it to the database
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    /**
     * Updates the status email for the specified order ID.
     *
     * @param orderId The unique identifier of the order.
     */

    @Override
    public void updateStatusEmail(@PathVariable Long orderId) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(4L).orElse(null);

        Optional<Order> order = orderRepository.findById(orderId);

        Customer customer = order.get().getCustomer();


        try{
            restaurantService.updatePreparationStatus(orderId);

            if (emailTemplate != null) {
                String emailSubject = emailTemplate.getSubject();
                String emailMessage = emailTemplate.getMessage();

                sendEmail(customer.getEmail(), emailSubject, emailMessage);
            }




        }catch (MessagingException | NotFoundException e) {
            e.printStackTrace();

        }
    }

    /**
     * Sends order confirmation emails to the customer and the restaurant.
     *
     * @param newOrder The order for which the confirmation email should be sent.
     * @return The updated order object.
     * @throws NotFoundException If the order is not found.
     */

    @Override
    public com.thinkpalm.thinkfood.model.Order orderConfirmationEmail(com.thinkpalm.thinkfood.model.Order newOrder) throws NotFoundException {
        com.thinkpalm.thinkfood.model.Order createdOrder = orderService.createOrder(newOrder);

        try {
            EmailTemplate emailTemplate = emailTemplateRepository.findById(3L).orElse(null);

            if (emailTemplate != null) {
                String emailMessage = emailTemplate.getMessage();

                emailMessage = emailMessage.replace("\\n", "\n");
                emailMessage = emailMessage.replace("\\n\\n", "\n\n");


                Cart cart = cartRepository.findById(newOrder.getCartId())
                        .orElseThrow(() -> new NotFoundException("Cart not found"));

                Customer customer = customerRepository.findById(cart.getCustomer().getId())
                        .orElseThrow(() -> new NotFoundException("Customer not found"));

                StringBuilder itemList = new StringBuilder();

                com.thinkpalm.thinkfood.entity.Restaurant restaurant;

                for (CartItem cartItem : cart.getCartItems()) {
                    itemList.append("- ").append(cartItem.getMenu().getItem().getItemName()).append(": ");
                    itemList.append(cartItem.getQuantity()).append(" x Rs").append(cartItem.getItemPrice()).append("\n");
                }

                restaurant = cart.getCartItems().get(0).getMenu().getRestaurant();

                emailMessage = emailMessage.replace("[CustomerName]", customer.getCustomerName());
                emailMessage = emailMessage.replace("[ItemList]", itemList.toString());
                emailMessage = emailMessage.replace("[ShippingAddress]", customer.getCustomerName() + "\n" + customer.getAddress());
                emailMessage = emailMessage.replace("[TotalCost]", "Rs " + createdOrder.getTotalCost());

                String emailSubject = emailTemplate.getSubject();
                String restaurantEmailSubject = "New Order Request";


                sendEmail(customer.getEmail(), emailSubject, emailMessage);
                sendEmail(restaurant.getEmail(), restaurantEmailSubject, emailMessage);
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return createdOrder;
    }


    /**
     * Generates a random password.
     *
     * @return The randomly generated password.
     */

    public String generateRandomPassword() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[10];
        secureRandom.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }

}


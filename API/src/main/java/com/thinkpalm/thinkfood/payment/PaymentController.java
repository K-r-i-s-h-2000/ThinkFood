package com.thinkpalm.thinkfood.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/think-food/payments")
public class PaymentController {
    @SuppressWarnings("unused")
    @Value("${stripe.api.key}")
    private String stripeApiKey;
    @Autowired
    private  PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;


    @PostMapping("/process/{orderId}")
    public Boolean processPayment(@PathVariable Long orderId, @RequestBody PaymentRequest paymentrequest) {
        boolean success = paymentService.processPayment(
                paymentrequest.getCardNumber(),
                paymentrequest.getExpirationDate(),
                paymentrequest.getCvv(),orderId
        );

        if (success) {
            return true;
        }
        else {
            return false;
        }
    }

//    @GetMapping("/redirect")
//    public void redirectToRedirectUrl(HttpServletResponse response, @RequestParam String redirectUrl) {
//        try {
//            // Use the HttpServletResponse to send a redirect response
//            response.sendRedirect(redirectUrl);
//        } catch (IOException e) {
//            // Handle the exception (e.g., log it)
//            e.printStackTrace();
//        }
//    }

@GetMapping("/payment/success")
public String handlePaymentSuccess(@RequestParam("payment_intent_id") String paymentIntentId) {
    Stripe.apiKey = stripeApiKey;

    try {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        paymentIntent.confirm(); // Confirm the Payment Intent

        if ("succeeded".equals(paymentIntent.getStatus())) {
            // Payment was successful
            return "payment_success"; // This would be the name of your HTML template for success
        } else {
            // Payment failed
            return "payment_error"; // This would be the name of your HTML template for error
        }
    } catch (StripeException e) {
        e.printStackTrace();
        // Handle Stripe exception
        return "payment_error"; // This would be the name of your HTML template for error
    }
}

    @GetMapping("/history")
    public List<Payment> getPaymentHistory() {
        return paymentRepository.findAll();
    }
}


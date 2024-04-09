package com.thinkpalm.thinkfood.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkpalm.thinkfood.entity.Role;
import lombok.*;

/**
 * Model class representing an authentication response.
 *
 * <p>The `AuthenticationResponse` class contains information returned as a response to an authentication request,
 * including an authentication token and an optional message.
 *
 * @author ajay.S
 * @version 2.0
 * @since 31/10/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {
    /**
     * The authentication token provided upon successful authentication.
     */
    private String token;
    private  Long id;
    private  Long customerId;
    private Role role;
    private String customerAddress;
    private String customerLatitude;
    private String  customerLongitude;
    /**
     * An optional message related to the authentication response.
     */
    private String message;

    private Long custId;
    private Long restaurantId;
    private Long deliveryId;

private String restaurantName;
        private String restaurantDescription;
    private Double latitude;
    private Double longitude;
    private String phoneNumber;
    private String deliveryName;
    private Integer deliveryContactNumber;
    private String deliveryVehicleNumber;
    private String deliveryAvailability;
    private String deliveryEmail;
    private Boolean restaurantAvailability;
    private String customerName;

}

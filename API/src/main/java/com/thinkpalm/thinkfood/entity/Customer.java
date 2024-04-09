package com.thinkpalm.thinkfood.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Entity class for representing customer data in the database.
 * This class is used to manage and modify the data of the "customer" table.
 * Author: ajay.S
 * Since: 31/10/2023
 * Version: 2.0
 */
@Entity
@Table(name = "customer")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends EntityDoc {


    /**
     * Customer's address.
     */
    @Column(name = "address")
    private String address;

    /**
     * Longitude coordinate of the customer's location.
     */
    @Column(name = "longitude")
    private Double longitude;

    /**
     * Latitude coordinate of the customer's location.
     */
    @Column(name = "latitude")
    private Double latitude;

    /**
     * Date of birth of the customer.
     */
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    /**
     * Gender of the customer.
     */
    @Column(name = "gender", length = 10)
    private String gender;

    /**
     * Email address of the customer.
     */
    @Column(name = "email", length = 10)
    private String email;
    /**
     * Customer's name.
     */
    @Column(name = "customer_name", length = 10)
    private String customerName;
    /**
     * Whether the customer is active or not.
     */
    @Builder.Default
    @Column(name="is_active")
    private Boolean isActive=false;

    /**
     * User associated with the customer.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
@Column(name="image")
    private  String image;

}

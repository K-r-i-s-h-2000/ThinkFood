package com.thinkpalm.thinkfood.entity;

import lombok.Getter;

/**
 * The {@code Role} enum represents the role or authority of a user in the system.
 *
 * <p>Each role defines the level of access and privileges for users, such as customers, restaurant owners, and
 * delivery agents.</p>
 *
 * @author ajay.S
 * @version 2.0
 * @since 31/10/2023
 */

@Getter

public enum Role {
//    CUSTOMER("ROLE_CUSTOMER"),
//    RESTAURANT_OWNER("ROLE_ADMIN"),
//    DELIVERY_AGENT("ROLE_DELIVERY_AGENT");
//
//    Role(String roleName) {
//    }

    /**
     * Represents the "Customer" role with an ID of 1.
     */
    CUSTOMER(1),
    /**
     * Represents the "Restaurant Owner" role with an ID of 2.
     */
    RESTAURANT_OWNER(2),
    /**
     * Represents the "Delivery Agent" role with an ID of 3.
     */
    DELIVERY_AGENT(3),
    ADMIN(4);


    private final int roleId;


    /**
     * Constructs a new role with the specified role ID.
     *
     * @param roleId The ID of the role.
     */
    Role(int roleId) {
        this.roleId = roleId;
    }



}
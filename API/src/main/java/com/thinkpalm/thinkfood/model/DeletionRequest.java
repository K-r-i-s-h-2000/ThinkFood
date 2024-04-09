package com.thinkpalm.thinkfood.model;

import lombok.*;

import javax.persistence.Entity;

/**
 * Model class representing a deletion request.
 *
 * <p>The `DeletionRequest` class contains information related to a deletion request, including the user's email
 * and an optional message.
 *
 * @author ajay.S
 * @version 2.0
 * @since 31/10/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DeletionRequest {
    /**
     * The email associated with the deletion request.
     */
    private String email;
    /**
     * An optional message related to the deletion request.
     */
    private String message;
}
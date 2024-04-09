/**
 * Represents an entity class for storing email templates in the database.
 * author: Sharon Sam
 * since : 01 November 2023
 * version : 2.0
 */

package com.thinkpalm.thinkfood.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="email_template")
public class EmailTemplate{

    /**
     * Unique identifier for the email template.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Subject of the email template.
     */
    private String subject;

    /**
     * Message content of the email template.
     * The column definition is set to "TEXT" to accommodate longer text values.
     */
    @Column(columnDefinition = "TEXT")
    private String message;

}


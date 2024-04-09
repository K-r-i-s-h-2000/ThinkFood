/**
 * Entity class representing a support query for CRUD operations.
 * This class is designed to facilitate modifications to the corresponding "support" table.
 *
 * <p>{@code Support} includes details such as the query text, response, and a flag indicating whether
 * the support query is currently active or not.</p>
 *
 * @author agrah.mv
 * @since 31 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="support")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Support extends EntityDoc{

    /**
     * The text of the support query.
     */
    @Column(name="query")
    private String query;

    /**
     * The response to the support query.
     */
    @Column(name="response")
    private String response;

    /**
     * Indicates whether the support query is currently active.
     */
    @Column(name="is_active")
    private Boolean isActive=false;
}

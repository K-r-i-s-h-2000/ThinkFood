/**
 * Model class representing a support query.
 * This class is used to hold information about a support query, including its ID, query text, and response.
 *
 *
 * @author agrah.mv
 * @since 31 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Support {

    /**
     * The unique identifier for the support query.
     */
    private Long id;

    /**
     * The text of the support query.
     */
    private String query;

    /**
     * The response to the support query.
     */
    private String response;
}

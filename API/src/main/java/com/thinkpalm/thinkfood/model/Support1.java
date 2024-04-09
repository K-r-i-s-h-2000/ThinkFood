/**
 * Model class representing support query and response.
 * This class is used to hold information about a support query, including its query text and response.
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
public class Support1 {

    /**
     * The text of the support query.
     */
    private String query;

    /**
     * The response to the support query.
     */
    private String response;
}


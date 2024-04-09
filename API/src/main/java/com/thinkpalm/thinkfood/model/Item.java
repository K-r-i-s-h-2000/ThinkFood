/**
 * Model class representing an item.
 * This class is used to hold information about an item, including its ID and name.
 *
 *
 * @author agrah.mv
 * @since 27 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Item {
    /**
     * The unique identifier for the item.
     */
    private Long id;

    /**
     * The name of the item.
     */
    private String itemName;

    private String image;
}


/**
 * this class is used to list the order nad it quantity to display.
 * @author: Rinkle Rose Renny
 * @since : 2 November 2023
 * @version : 2.0
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
public class DeliveryItem {
    /**
     *  Name of the item
     */
    private String itemName;
    /**
     * Quantity of that item.
     */
    private Integer quantity;
}

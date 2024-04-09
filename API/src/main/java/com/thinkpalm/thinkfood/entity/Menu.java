/**
 * Entity class for menu CRUD operations.
 * This class is to make modification to the table mentioned.
 * author: Sharon Sam
 * since : 26 October 2023
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
@Table(name="menu")
public class Menu extends EntityDoc{

    /**
     * The restaurant to which this menu item belongs.
     */

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    /**
     * The item associated with this menu entry.
     */

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    /**
     * A brief description of the menu item.
     */

    @Column(name = "item_description")
    private String itemDescription;

    /**
     * The price of the menu item.
     */

    @Column(name = "item_price")
    private Double itemPrice;

    /**
     * The preparation time in minutes for this menu item.
     */

    @Column(name = "preparation_time")
    private Integer preparationTime;

    /**
     * Indicates whether the menu item is currently available.
     */

    @Column(name = "item_availability")
    private Boolean itemAvailability;

    /**
     * Indicates whether the menu item is deleted or not.
     */

    @Column(name="is_active")
    private Boolean isActive=false;
}

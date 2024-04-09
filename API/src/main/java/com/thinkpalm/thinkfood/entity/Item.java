/**
 * Entity class representing Item.
 * This class extends EntityDoc.
 * This class is used to store information about specific food item coming under particular Category.
 *
 * @author agrah.mv
 * @since 27 October 2023
 * @version 2.0
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
@Table(name = "item")
public class Item extends EntityDoc {


    /**
     * The category to which this item belongs.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * The name of the item.
     */
    @Column(name = "item_name",nullable = false)
    private String itemName;

    /**
     * Indicates whether the item is currently active.
     * Default value is set to false.
     */
    @Column(name="is_active")
    private Boolean isActive=false;

    @Column(name="image")
    private String image;


}


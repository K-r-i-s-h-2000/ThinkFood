/**
 * Entity class representing Category.
 * This class extends EntityDoc and represents a food category in the system.
 *
 * @author agrah.mv
 * @version 2.0
 * @since 26 October 2023
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
@Table(name = "category")
public class Category extends EntityDoc {

    /**
     * The name of the category.
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * Indicates whether the category is currently active.
     * Default value is set to false.
     */
    @Column(name="is_active")
    private Boolean isActive=false;


    @Column(name="image_path")
    private String image;

}
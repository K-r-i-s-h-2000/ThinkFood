/**
 * Model class representing a category.
 * This class is used to hold information about a category, including its ID and name.
 *
 *
 * @author agrah.mv
 * @since 26 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.model;



import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Category {
    /**
     * The unique identifier for the category.
     */
    private Long id;

    /**
     * The name of the category.
     */
    private String categoryName;

    private String image;


}


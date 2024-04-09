package com.thinkpalm.thinkfood.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateMenu {
    private String itemDescription;
    private Double itemPrice;
    private Integer preparationTime;
    private Boolean itemAvailability;
}

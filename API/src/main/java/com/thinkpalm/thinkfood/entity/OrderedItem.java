package com.thinkpalm.thinkfood.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ordered_item")
public class OrderedItem extends EntityDoc {
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "subtotal")
    private Double subtotal;

}


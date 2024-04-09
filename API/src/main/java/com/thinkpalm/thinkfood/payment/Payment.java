package com.thinkpalm.thinkfood.payment;

import com.thinkpalm.thinkfood.entity.EntityDoc;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
@Builder
public class Payment extends EntityDoc {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private double amount;
    private boolean success;


}


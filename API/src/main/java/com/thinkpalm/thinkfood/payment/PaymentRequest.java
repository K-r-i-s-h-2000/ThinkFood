package com.thinkpalm.thinkfood.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class PaymentRequest {
    private String cardNumber;
    private String expirationDate;
    private String cvv;

    private long orderId;


}

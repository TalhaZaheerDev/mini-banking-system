package com.talha.bankingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer implements ModelClass {
    String customerId;
    String customerName;
    String customerEmail;
    String customerPhone;
    String customerAddress;



    @Override
    public String getId() {
        return customerId;
    }

}

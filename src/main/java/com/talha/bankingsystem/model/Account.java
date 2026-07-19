package com.talha.bankingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account implements ModelClass {
    String accountNumber;
    Long accountBalance;
    AccountType accountType;
    Customer customer;
    Status status;

    public String getId() {
        return accountNumber;
    }
}

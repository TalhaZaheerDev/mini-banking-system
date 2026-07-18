package com.talha.bankingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
    String accountNumber;
    Long accountBalance;
    AccountType accountType;
    Customer customer;
    Status status;

    String getId() {
        return accountNumber;
    }
}

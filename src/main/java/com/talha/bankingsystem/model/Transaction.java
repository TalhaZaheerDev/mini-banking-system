package com.talha.bankingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction implements ModelClass{
    String transactionId;
    Account fromAccount;
    Account toAccount;
    long amount;
    LocalDateTime timestamp;
    TransactionType transactionType;
    Status transactionStatus;

    @Override
    public String getId() {
        return transactionId;
    }

}

package com.talha.bankingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
    String transactionId;
    Account fromAccount;
    Account toAccount;
    long amount;
    LocalDateTime timestamp;
    TransactionType transactionType;
    Status transactionStatus;
}

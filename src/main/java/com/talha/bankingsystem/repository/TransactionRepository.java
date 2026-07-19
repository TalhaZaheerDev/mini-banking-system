package com.talha.bankingsystem.repository;

import com.talha.bankingsystem.model.Transaction;
import com.talha.bankingsystem.model.TransactionStatus;
import com.talha.bankingsystem.model.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TransactionRepository extends InMemoryRepository<Transaction> {
    public List<Transaction> findByAccount(String accountNumber){
        return findAll().stream()
                .filter(Transaction -> Transaction.getFromAccount().getAccountNumber().equals(accountNumber)
                || Transaction.getToAccount().getAccountNumber().equals(accountNumber))
                .toList();
    }

    public List<Transaction> findByType(TransactionType type){
        return findAll().stream()
                .filter(Transaction -> Transaction.getTransactionType() == type)
                .toList();
    }

    public List<Transaction> findByDate(LocalDate date){
        return findAll().stream()
                .filter(Transaction -> Transaction.getTimestamp().toLocalDate().equals(date))
                .toList();
    }

    public List<Transaction> findSuccessfulTransactions(){
        return findAll().stream()
                .filter(transaction -> transaction.getTransactionStatus() == TransactionStatus.SUCCESS)
                .toList();
    }

    public List<Transaction> findFailedTransactions(){
        return findAll().stream()
                .filter(transaction -> transaction.getTransactionStatus() == TransactionStatus.FAILURE)
                .toList();
    }

    public List<Transaction> findPendingTransactions(){
        return findAll().stream()
                .filter(transaction -> transaction.getTransactionStatus()==TransactionStatus.PENDING)
                .toList();
    }

    public List<Transaction> findLatestTransactions() {
        return findAll().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .toList();
    }


    public List<Transaction> findByTransactionType(TransactionType type) {
        return  findAll().stream()
                .filter(transaction -> transaction.getTransactionType() == type)
                .toList();
    }

    public List<Transaction> findByTransactionStatus(TransactionStatus status) {
        return findAll().stream()
                .filter(transaction -> transaction.getTransactionStatus() == status)
                .toList();
    }
}

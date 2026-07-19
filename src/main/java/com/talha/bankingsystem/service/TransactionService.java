package com.talha.bankingsystem.service;

import com.talha.bankingsystem.model.Transaction;
import com.talha.bankingsystem.model.TransactionStatus;
import com.talha.bankingsystem.model.TransactionType;
import com.talha.bankingsystem.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Save Transaction
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    // Complete Transaction History
    public List<Transaction> getTransactionHistory() {
        return transactionRepository.findAll();
    }

    // Find Transaction By ID
    public Transaction findTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Transaction not found."));
    }

    // Transactions of an Account
    public List<Transaction> findTransactionsByAccount(String accountNumber) {
        return transactionRepository.findByAccount(accountNumber);
    }

    // Transactions By Date
    public List<Transaction> findTransactionsByDate(LocalDate date) {
        return transactionRepository.findByDate(date);
    }

    // Transactions By Type
    public List<Transaction> findTransactionsByType(TransactionType type) {
        return transactionRepository.findByTransactionType(type);
    }

    // Transactions By Status
    public List<Transaction> findTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByTransactionStatus(status);
    }

    // Latest Transactions
    public List<Transaction> getLatestTransactions(int limit) {
        return transactionRepository.findAll().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(limit)
                .toList();
    }

    // Oldest Transactions
    public List<Transaction> getOldestTransactions(int limit) {
        return transactionRepository.findAll().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .limit(limit)
                .toList();
    }

    // Sort Latest -> Oldest
    public List<Transaction> sortLatestFirst() {
        return transactionRepository.findAll().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .toList();
    }

    // Sort Oldest -> Latest
    public List<Transaction> sortOldestFirst() {
        return transactionRepository.findAll().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .toList();
    }
}
package com.talha.bankingsystem.service;

import com.talha.bankingsystem.model.*;
import com.talha.bankingsystem.repository.AccountRepository;
import com.talha.bankingsystem.repository.CustomerRepository;
import com.talha.bankingsystem.repository.TransactionRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public ReportService(CustomerRepository customerRepository,
                         AccountRepository accountRepository,
                         TransactionRepository transactionRepository) {

        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /*--------------------------------------------------
                    BANK SUMMARY
    ---------------------------------------------------*/

    public long getTotalCustomers() {
        return customerRepository.findAll().size();
    }

    public long getTotalAccounts() {
        return accountRepository.findAll().size();
    }

    public long getTotalTransactions() {
        return transactionRepository.findAll().size();
    }

    public long getTotalBankBalance() {
        return accountRepository.findAll()
                .stream()
                .mapToLong(Account::getAccountBalance)
                .sum();
    }

    /*--------------------------------------------------
                  BALANCE REPORTS
    ---------------------------------------------------*/

    public double getAverageBalance() {
        return accountRepository.findAll()
                .stream()
                .mapToLong(Account::getAccountBalance)
                .average()
                .orElse(0);
    }

    public long getHighestBalance() {
        return accountRepository.findAll()
                .stream()
                .mapToLong(Account::getAccountBalance)
                .max()
                .orElse(0);
    }

    public long getLowestBalance() {
        return accountRepository.findAll()
                .stream()
                .mapToLong(Account::getAccountBalance)
                .min()
                .orElse(0);
    }

    /*--------------------------------------------------
                 CUSTOMER REPORTS
    ---------------------------------------------------*/

    public List<Account> getTopRichCustomers(int limit) {
        return accountRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Account::getAccountBalance)
                        .reversed())
                .limit(limit)
                .toList();
    }

    public List<Customer> getCustomersWithMultipleAccounts() {

        Map<String, Long> accountsPerCustomer =
                accountRepository.findAll()
                        .stream()
                        .collect(Collectors.groupingBy(
                                account -> account.getCustomer().getCustomerId(),
                                Collectors.counting()));

        return customerRepository.findAll()
                .stream()
                .filter(customer ->
                        accountsPerCustomer.getOrDefault(
                                customer.getCustomerId(), 0L) > 1)
                .toList();
    }

    public List<Customer> getMostActiveCustomers(int limit) {

        Map<String, Long> transactionCount =
                transactionRepository.findAll()
                        .stream()
                        .collect(Collectors.groupingBy(
                                transaction ->
                                        transaction.getFromAccount()
                                                .getCustomer()
                                                .getCustomerId(),
                                Collectors.counting()));

        return customerRepository.findAll()
                .stream()
                .sorted((c1, c2) -> Long.compare(
                        transactionCount.getOrDefault(
                                c2.getCustomerId(), 0L),
                        transactionCount.getOrDefault(
                                c1.getCustomerId(), 0L)))
                .limit(limit)
                .toList();
    }

    /*--------------------------------------------------
                TRANSACTION REPORTS
    ---------------------------------------------------*/

    public List<Transaction> getLargestTransactions(int limit) {

        return transactionRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Transaction::getAmount)
                        .reversed())
                .limit(limit)
                .toList();
    }

    public long getTotalDeposits() {

        return transactionRepository.findAll()
                .stream()
                .filter(t -> t.getTransactionType() == TransactionType.DEPOSIT)
                .mapToLong(Transaction::getAmount)
                .sum();
    }

    public long getTotalWithdrawals() {

        return transactionRepository.findAll()
                .stream()
                .filter(t -> t.getTransactionType() == TransactionType.WITHDRAW)
                .mapToLong(Transaction::getAmount)
                .sum();
    }

    public long getTransferCount() {

        return transactionRepository.findAll()
                .stream()
                .filter(t -> t.getTransactionType() == TransactionType.TRANSFER)
                .count();
    }

    /*--------------------------------------------------
                  ACCOUNT REPORTS
    ---------------------------------------------------*/

    public List<Account> getSavingsAccounts() {

        return accountRepository.findAll()
                .stream()
                .filter(a -> a.getAccountType() == AccountType.SAVINGS)
                .toList();
    }

    public List<Account> getCurrentAccounts() {

        return accountRepository.findAll()
                .stream()
                .filter(a -> a.getAccountType() == AccountType.CURRENT)
                .toList();
    }

    public List<Account> getAccountsAboveBalance(long balance) {

        return accountRepository.findAll()
                .stream()
                .filter(a -> a.getAccountBalance() >= balance)
                .toList();
    }

    /*--------------------------------------------------
                     GROUPING
    ---------------------------------------------------*/

    public Map<AccountType, List<Account>> groupAccountsByType() {

        return accountRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Account::getAccountType));
    }

    public Map<TransactionType, List<Transaction>> groupTransactionsByType() {

        return transactionRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getTransactionType));
    }

}
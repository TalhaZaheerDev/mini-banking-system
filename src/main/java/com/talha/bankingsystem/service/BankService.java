package com.talha.bankingsystem.service;

import com.talha.bankingsystem.model.*;
import com.talha.bankingsystem.repository.AccountRepository;
import com.talha.bankingsystem.repository.CustomerRepository;
import com.talha.bankingsystem.repository.Repository;
import com.talha.bankingsystem.repository.TransactionRepository;
import com.talha.bankingsystem.util.IdGenerater;

import java.time.LocalDateTime;
import java.util.Optional;

public class BankService {
   Repository<Account> accountRepository= new AccountRepository();
   Repository<Customer> customerRepository= new CustomerRepository();
   Repository<Transaction> transactionRepository= new TransactionRepository();

   /// ///////////////////////////
   /// ///////////////////////////
   /// Customer Handling /////////
   /// ///////////////////////////
   /// ///////////////////////////
    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer.getId());
    }

    public void updateCustomer(Customer customer) {
        customerRepository.update(customer);
    }

    /// ///////////////////////////
    /// ///////////////////////////
    /// Account Handling /////////
    /// ///////////////////////////
    /// ///////////////////////////

    public void openAccount(Account account) {
        accountRepository.save(account);
    }
    public void closeAccount(Account account) {
        accountRepository.delete(account.getId());
    }

    public Optional<Account> findAccount(String id) {
        return accountRepository.findById(id);
    }

    public Long balanceInquiry(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"))
                .getAccountBalance();
    }

    /// ///////////////////////////
    /// ///////////////////////////
    /// Banking  Handling /////////
    /// ///////////////////////////
    /// ///////////////////////////
    public void deposit(String accountId, long amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found."));

        account.setAccountBalance(account.getAccountBalance() + amount);

        accountRepository.update(account);

        Transaction transaction = new Transaction(
                IdGenerater.generateTransactionId(),
                null,
                account,
                amount,
                LocalDateTime.now(),
                TransactionType.DEPOSIT,
                TransactionStatus.SUCCESS
        );

        transactionRepository.save(transaction);
    }

    public void withdraw(String accountId, long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be greater than zero.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        account.setAccountBalance(account.getAccountBalance() - amount);
        accountRepository.update(account);
        Transaction transaction = new Transaction(
                IdGenerater.generateTransactionId(),
                null,
                account,
                amount,
                LocalDateTime.now(),
                TransactionType.WITHDRAW,
                TransactionStatus.SUCCESS
        );

        transactionRepository.save(transaction);
    }

    public void transfer(String fromAccountId, String toAccountId, long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }

        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("From account id must not equal to to account id.");
        }

        Account sender = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found."));
        Account reciever = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found."));

        if(sender.getAccountBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        sender.setAccountBalance(sender.getAccountBalance() - amount);
        accountRepository.update(sender);
        reciever.setAccountBalance(reciever.getAccountBalance() + amount);
        accountRepository.update(reciever);

        Transaction transaction = new Transaction(
                IdGenerater.generateTransactionId(),
                sender,
                reciever,
                amount,
                LocalDateTime.now(),
                TransactionType.TRANSFER,
                TransactionStatus.SUCCESS
        );
    }



}

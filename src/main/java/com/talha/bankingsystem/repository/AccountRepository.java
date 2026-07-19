package com.talha.bankingsystem.repository;

import com.talha.bankingsystem.model.Account;
import com.talha.bankingsystem.model.AccountType;
import com.talha.bankingsystem.model.Status;

import java.util.List;
import java.util.Optional;

public class AccountRepository extends InMemoryRepository<Account>{

    public Optional<Account> findByAccountNumber(String accountNumber){
        return findAll().stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst();
    }


    public List<Account> findByCustomerId(String customerId){
        return findAll().stream()
                .filter(account -> account.getCustomer().getCustomerId().equals(customerId))
                .toList();
    }

    public List<Account> findByAccountType(AccountType accountType){
        return findAll().stream()
                .filter(account -> account.getAccountType() == accountType)
                .toList();
    }

    public List<Account> findActiveAccounts(){
        return findAll().stream()
                .filter(account -> account.getStatus() == Status.ACTIVE)
                .toList();
    }

    public boolean existsByAccountNumber(String accountNumber){
        return findAll().stream().anyMatch(account -> account.getAccountNumber().equals(accountNumber));
    }

}

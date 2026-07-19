package com.talha.bankingsystem.repository;

import com.talha.bankingsystem.model.Customer;

import java.util.List;

public class CustomerRepository extends InMemoryRepository<Customer> {
    AccountRepository accountRepository = new AccountRepository();

    public List<Customer> findByEmail(String email){
        return findAll().stream()
                .filter(customer -> customer.getCustomerEmail().equals(email))
                .toList();
    }

    public List<Customer> findByPhone(String Phone){
        return findAll().stream()
                .filter(customer -> customer.getCustomerPhone().equals(Phone))
                .toList();
    }

    public List<Customer> findByName(String name){
        return findAll().stream()
                .filter(customer -> customer.getCustomerName().equals(name))
                .toList();
    }

    public List<Customer> findCustomersWithMultipleAccounts() {
        return findAll().stream()
                .filter(customer ->
                        accountRepository.findByCustomerId(customer.getCustomerId()).size() > 1)
                .toList();
    }
}

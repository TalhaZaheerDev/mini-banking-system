package com.talha.bankingsystem.repository;

import com.talha.bankingsystem.model.Customer;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class CustomerRepository extends InMemoryRepository<Customer> {
    private final AccountRepository accountRepository;

    public CustomerRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    List<Customer> findByEmail(String email){
        return findAll().stream()
                .filter(customer -> customer.getCustomerEmail().equals(email))
                .toList();
    }

    List<Customer> findByPhone(String Phone){
        return findAll().stream()
                .filter(customer -> customer.getCustomerPhone().equals(Phone))
                .toList();
    }

    List<Customer> findByName(String name){
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

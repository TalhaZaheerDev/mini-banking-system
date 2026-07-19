package com.talha.bankingsystem.util;

import com.talha.bankingsystem.model.*;
import com.talha.bankingsystem.service.BankService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SampleDataGenerator {

    private final BankService bankService;
    private final Random random = new Random();

    private final List<Customer> customers = new ArrayList<>();
    private final List<Account> accounts = new ArrayList<>();

    public SampleDataGenerator(BankService bankService) {
        this.bankService = bankService;
    }

    public void seed() {

        createCustomers(100);

        createAccounts();

        createInitialBalances();

        System.out.println("----------------------------------------");
        System.out.println("Sample Data Generated Successfully");
        System.out.println("Customers : " + customers.size());
        System.out.println("Accounts  : " + accounts.size());
        System.out.println("----------------------------------------");
    }

    private void createCustomers(int totalCustomers) {

        for (int i = 1; i <= totalCustomers; i++) {

            Customer customer = new Customer(
                    IdGenerater.generateCustomerId(),
                    "Customer " + i,
                    "customer" + i + "@gmail.com",
                    "0300" + String.format("%07d", i),
                    "Islamabad"
            );

            bankService.createCustomer(customer);

            customers.add(customer);
        }
    }

    private void createAccounts() {

        for (Customer customer : customers) {

            Account account = new Account(
                    IdGenerater.generateAccountId(),
                    0L,
                    randomAccountType(),
                    customer,
                    Status.ACTIVE
            );

            bankService.openAccount(account);

            accounts.add(account);
        }
    }

    private void createInitialBalances() {

        for (Account account : accounts) {

            long balance = random.nextLong(50_000, 500_001);

            bankService.deposit(
                    account.getAccountNumber(),
                    balance
            );
        }
    }

    private AccountType randomAccountType() {

        AccountType[] accountTypes = AccountType.values();

        return accountTypes[random.nextInt(accountTypes.length)];
    }

}
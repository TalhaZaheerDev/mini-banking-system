package com.talha.bankingsystem.app;

import com.talha.bankingsystem.repository.AccountRepository;
import com.talha.bankingsystem.repository.CustomerRepository;
import com.talha.bankingsystem.repository.TransactionRepository;
import com.talha.bankingsystem.service.BankService;
import com.talha.bankingsystem.service.ReportService;
import com.talha.bankingsystem.service.SimulationService;
import com.talha.bankingsystem.util.SampleDataGenerator;

public class Main {
    private static final CustomerRepository customerRepository = new CustomerRepository();
    private static final TransactionRepository transactionRepository = new TransactionRepository();
    private static final AccountRepository accountRepository = new AccountRepository();


    private static final BankService bankService = new BankService(
                    accountRepository,
                    customerRepository,
                    transactionRepository);
    private static final ReportService reportService = new ReportService(
                    customerRepository,
                    accountRepository,
                    transactionRepository);
    static SimulationService simulationService = new SimulationService(
                    bankService,
                    accountRepository,
                    reportService);

    public static void main(String[] args) {
        SampleDataGenerator generator =
                new SampleDataGenerator(bankService);

        generator.seed();

        SimulationService simulationService =
                new SimulationService(
                        bankService,
                        accountRepository,
                        reportService
                );
        simulationService.startSimulation();
    }
}

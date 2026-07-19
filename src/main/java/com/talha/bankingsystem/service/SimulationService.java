package com.talha.bankingsystem.service;

import com.talha.bankingsystem.model.Account;
import com.talha.bankingsystem.repository.AccountRepository;
import com.talha.bankingsystem.thread.DepositTask;
import com.talha.bankingsystem.thread.TransferTask;
import com.talha.bankingsystem.thread.WithdrawTask;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationService {

    private final BankService bankService;

    private final AccountRepository accountRepository;

    private final ReportService reportService;

    private final Random random = new Random();

    public SimulationService(BankService bankService,
                             AccountRepository accountRepository,
                             ReportService reportService) {

        this.bankService = bankService;
        this.accountRepository = accountRepository;
        this.reportService = reportService;
    }

    public void startSimulation() {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        long start = System.currentTimeMillis();

        generateDepositTasks(executor,100);

        generateWithdrawTasks(executor,100);

        generateTransferTasks(executor,300);

        executor.shutdown();

        try {

            executor.awaitTermination(5, TimeUnit.MINUTES);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

        long end = System.currentTimeMillis();

        printSimulationReport(end-start);

    }

    private void generateDepositTasks(ExecutorService executor,
                                      int count){

        List<Account> accounts = accountRepository.findAll();

        for(int i=0;i<count;i++){

            System.out.println(accounts.size());
            Account account =
                    accounts.get(random.nextInt(accounts.size()));

            long amount = random.nextLong(100,10000);

            executor.submit(new DepositTask(
                    bankService,
                    account.getAccountNumber(),
                    amount
            ));

        }

    }

    private void generateWithdrawTasks(ExecutorService executor,
                                       int count){

        List<Account> accounts = accountRepository.findAll();

        for(int i=0;i<count;i++){

            Account account =
                    accounts.get(random.nextInt(accounts.size()));

            long amount = random.nextLong(100,5000);

            executor.submit(new WithdrawTask(
                    bankService,
                    account.getAccountNumber(),
                    amount
            ));

        }

    }

    private void generateTransferTasks(ExecutorService executor,
                                       int count){

        List<Account> accounts = accountRepository.findAll();

        for(int i=0;i<count;i++){

            Account from =
                    accounts.get(random.nextInt(accounts.size()));

            Account to =
                    accounts.get(random.nextInt(accounts.size()));

            while(from.getAccountNumber().equals(to.getAccountNumber())){

                to = accounts.get(random.nextInt(accounts.size()));

            }

            long amount = random.nextLong(100,5000);

            executor.submit(new TransferTask(
                    bankService,
                    from.getAccountNumber(),
                    to.getAccountNumber(),
                    amount
            ));

        }

    }

    private void printSimulationReport(long executionTime){

        System.out.println();

        System.out.println("========== Simulation Report ==========");

        System.out.println("Customers : "
                + reportService.getTotalCustomers());

        System.out.println("Accounts : "
                + reportService.getTotalAccounts());

        System.out.println("Transactions : "
                + reportService.getTotalTransactions());

        System.out.println("Total Bank Balance : "
                + reportService.getTotalBankBalance());

        System.out.println("Execution Time : "
                + executionTime + " ms");

        System.out.println("=======================================");

    }

}
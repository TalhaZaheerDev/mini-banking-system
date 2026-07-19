package com.talha.bankingsystem.thread;

import com.talha.bankingsystem.service.BankService;

public class TransferTask implements Runnable {

    private final BankService bankService;

    private final String fromAccount;

    private final String toAccount;

    private final long amount;

    public TransferTask(BankService bankService,
                        String fromAccount,
                        String toAccount,
                        long amount) {

        this.bankService = bankService;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Override
    public void run() {

        try {

            bankService.transfer(fromAccount, toAccount, amount);

        } catch (Exception e) {

            System.err.println(Thread.currentThread().getName()
                    + " -> " + e.getMessage());

        }
    }
}
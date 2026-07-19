package com.talha.bankingsystem.thread;

import com.talha.bankingsystem.service.BankService;

public class DepositTask implements Runnable {
    private final BankService bankService;
    private final String accountId;
    private final long amount;

    public DepositTask(BankService bankService, String accountId, long amount) {
        this.bankService = bankService;
        this.accountId = accountId;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            bankService.deposit(accountId, amount);
        } catch (Exception e) {
            System.err.println(Thread.currentThread().getName()
                    + " -> Deposit Failed: " + e.getMessage());
        }
    }

}

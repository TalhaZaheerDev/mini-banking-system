package com.talha.bankingsystem.thread;

import com.talha.bankingsystem.service.BankService;

public class WithdrawTask implements Runnable {
    private final BankService bankService;
    private final String accountId;
    private final long amount;
    public WithdrawTask(BankService bankService, String accountId, long amount) {
        this.bankService = bankService;
        this.accountId = accountId;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            bankService.withdraw(accountId, amount);
        } catch (Exception e) {
            System.err.println(Thread.currentThread().getName()
                    + " -> " + e.getMessage());
        }
    }
}

package com.talha.bankingsystem.util;

public final class IdGenerater {

    private static long customerCounter = 1;
    private static long accountCounter = 1;
    private static long transactionCounter = 1;

    private IdGenerater() {
    }

    public static synchronized String generateCustomerId() {
        return String.format("CUS-%05d", customerCounter++);
    }

    public static synchronized String generateAccountId() {
        return String.format("ACC-%05d", accountCounter++);
    }

    public static synchronized String generateTransactionId() {
        return String.format("TRX-%05d", transactionCounter++);
    }
}
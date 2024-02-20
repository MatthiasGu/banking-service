package model;

import exception.InsufficientBalanceException;

public class Account {
    private String accountId;
    private double balance;

    public Account(String accountId, double initialDeposit) {
        this.accountId = accountId;
        this.balance = initialDeposit;
    }

    public Account(String accountId) {
        this.accountId = accountId;
        this.balance = 0;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) throws InsufficientBalanceException {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        throw new InsufficientBalanceException(this.accountId);
    }
}

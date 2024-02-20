package service;

import exception.AccountAlreadyExistsException;
import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import model.Account;
import java.util.*;

public class BankingService {
    private Map<String, Account> accounts;

    public BankingService() {
        this.accounts = new HashMap<>();
    }

    public Account createAccount(String accountId, double initialDeposit) throws AccountAlreadyExistsException {
        if (accounts.containsKey(accountId)) {
            throw new AccountAlreadyExistsException(accountId);
        }
        Account account = new Account(accountId, initialDeposit);
        accounts.put(accountId, account);
        return account;
    }

    public void deposit(String accountId, double amount) {
        Account account = accounts.get(accountId);
        if (account != null) {
            account.deposit(amount);
            System.out.println(amount + " deposited successfully to account " + accountId);
        } else {
            System.out.println("Account not found!");
        }
    }

    public void withdraw(String accountId, double amount) {
        Account account = accounts.get(accountId);
        if (account != null) {
            try {
                if (account.withdraw(amount)) {
                    System.out.println(amount + " withdrawn successfully from account " + accountId);
                }
            } catch (InsufficientBalanceException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Account not found!");
        }
    }

    public void transfer(String fromAccountId, String toAccountId, double amount) throws AccountNotFoundException {
        Account fromAccount = Optional.
                ofNullable(accounts.get(fromAccountId))
                .orElseThrow(() -> new AccountNotFoundException(fromAccountId));
        Account toAccount = Optional.
                ofNullable(accounts.get(toAccountId))
                .orElseThrow(() -> new AccountNotFoundException(toAccountId));
        if (fromAccount != null && toAccount != null) {
            try {
                if (fromAccount.withdraw(amount)) {
                    toAccount.deposit(amount);
                    System.out.println(amount + " transferred successfully from account " + fromAccountId + " to account " + toAccountId);
                }
            } catch (InsufficientBalanceException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("One or both accounts not found!");
        }
    }

    public double getAccountBalance(String accountId) {
        Account account = accounts.get(accountId);
        if (account != null) {
            return account.getBalance();
        } else {
            System.out.println("Account not found!");
            return -1; // Return a negative value to indicate account not found
        }
    }
}

package exception;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String accountId) {
        super("Account " + accountId + "has insufficient balance!");
    }

}

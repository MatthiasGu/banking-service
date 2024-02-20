package exception;

public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(String accountId) {
        super("Account with id " + accountId + "already exists");
    }
}

package service;

import exception.AccountAlreadyExistsException;
import exception.AccountNotFoundException;
import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankingServiceTest {
    private BankingService bankingService;

    @BeforeEach
    public void setUp() {
        bankingService = new BankingService();
    }

    @Test
    public void testCreateAccount() throws AccountAlreadyExistsException {
        Account account = bankingService.createAccount("123456", 1000);
        assertNotNull(account);
        assertEquals("123456", account.getAccountId());
        assertEquals(1000, account.getBalance());
    }

    @Test
    public void testCreateAccount_withExistingId_throwsException() {
        assertThrows(AccountAlreadyExistsException.class, () -> {
            bankingService.createAccount("123456", 0);
            bankingService.createAccount("123456", 0);
        });
    }

    @Test
    public void testDeposit() throws AccountAlreadyExistsException, AccountNotFoundException {
        bankingService.createAccount("123456", 1000);
        bankingService.deposit("123456", 200);
        assertEquals(1200, bankingService.getAccountBalance("123456"));
    }

    @Test
    public void testWithdraw_sufficientBalance() throws AccountAlreadyExistsException, AccountNotFoundException {
        bankingService.createAccount("123456", 1000);
        bankingService.withdraw("123456", 500);
        assertEquals(500, bankingService.getAccountBalance("123456"));
    }

    @Test
    public void testWithdraw_insufficientBalance() throws AccountAlreadyExistsException, AccountNotFoundException {
        bankingService.createAccount("123456", 100);
        assertDoesNotThrow(() -> bankingService.withdraw("123456", 200));
        assertEquals(100, bankingService.getAccountBalance("123456"));
    }

    @Test
    public void testTransfer() throws AccountAlreadyExistsException, AccountNotFoundException {
        bankingService.createAccount("123456", 1000);
        bankingService.createAccount("789012", 500);
        bankingService.transfer("123456", "789012", 300);
        assertEquals(700, bankingService.getAccountBalance("123456"));
        assertEquals(800, bankingService.getAccountBalance("789012"));
    }

    @Test
    public void testTransfer_throwsExceptionWhenFromAccountNotFound() throws AccountAlreadyExistsException, AccountNotFoundException {
        bankingService.createAccount("123456", 1000);
        bankingService.createAccount("789012", 500);
        assertThrows(
                AccountNotFoundException.class,
                () -> bankingService.transfer(
                        "12345", "789012", 300));
        assertEquals(1000, bankingService.getAccountBalance("123456"));
        assertEquals(500, bankingService.getAccountBalance("789012"));
    }

    @Test
    public void testTransfer_throwsExceptionWhenToAccountNotFound() throws AccountAlreadyExistsException, AccountNotFoundException {
        bankingService.createAccount("123456", 1000);
        bankingService.createAccount("789012", 500);
        assertThrows(
                AccountNotFoundException.class,
                () -> bankingService.transfer(
                        "123456", "78901", 300));
        assertEquals(1000, bankingService.getAccountBalance("123456"));
        assertEquals(500, bankingService.getAccountBalance("789012"));
    }

    @Test
    public void testTransfer_insufficientBalance() throws AccountAlreadyExistsException, AccountNotFoundException {
        bankingService.createAccount("123456", 1000);
        bankingService.createAccount("789012", 500);
        bankingService.transfer("123456", "789012", 3000);
        assertEquals(1000, bankingService.getAccountBalance("123456"));
        assertEquals(500, bankingService.getAccountBalance("789012"));
    }

    @Test
    public void testGetAccountBalance() throws AccountAlreadyExistsException, AccountNotFoundException {
        bankingService.createAccount("123456", 1000);
        assertEquals(1000, bankingService.getAccountBalance("123456"));
    }

    @Test
    public void testGetAccountBalance_throwsException() throws AccountAlreadyExistsException {
        bankingService.createAccount("12345", 1000);
        assertThrows(AccountNotFoundException.class, () -> bankingService.getAccountBalance("123456"));
    }
}

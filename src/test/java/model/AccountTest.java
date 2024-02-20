package model;

import exception.InsufficientBalanceException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    @Test
    public void testAccountCreation() {
        Account account = new Account("123456", 1000);
        assertEquals("123456", account.getAccountId());
        assertEquals(1000, account.getBalance());
    }

    @Test
    public void testAccountCreation_withNoDeposit() {
        Account account = new Account("123456");
        assertEquals("123456", account.getAccountId());
        assertEquals(0, account.getBalance());
    }

    @Test
    public void testDeposit() {
        Account account = new Account("123456", 1000);
        account.deposit(200);
        assertEquals(1200, account.getBalance());
    }

    @Test
    public void testWithdraw_sufficientBalance() {
        Account account = new Account("123456", 1000);
        assertDoesNotThrow(() -> account.withdraw(500));
        assertEquals(500, account.getBalance());
    }

    @Test
    public void testWithdraw_insufficientBalance() {
        Account account = new Account("123456", 100);
        assertThrows(InsufficientBalanceException.class, () -> account.withdraw(200));
        assertEquals(100, account.getBalance());
    }
}

package homework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

class TestDatabaseProxyUnit {
	
	private DatabaseProxy dbProxy = new DatabaseProxy();
	
	@BeforeEach
	void initializeDb() {
		this.dbProxy.balances.put(1234, 100);
		this.dbProxy.balances.put(9876, 100);
		
		this.dbProxy.accounts.put(1234, "1234");
		this.dbProxy.accounts.put(9876, "4567");

	}
	
	@AfterEach
	void resetDb() {
		this.dbProxy.balances.put(1234, 100);
		this.dbProxy.balances.put(9876, 100);
		
		this.dbProxy.accounts.put(1234, "1234");
		this.dbProxy.accounts.put(9876, "4567");
	}

	@Test
	void test_should_decrease_account_amount_when_account_has_enough_balance() {
		this.dbProxy.minusBalance(1234, 10);
		
		int newAmount = this.dbProxy.balances.get(1234);
		
		assertEquals(newAmount, 90);
	}
	
	@Test
	void test_should_allow_when_balance_is_zero() {
		this.dbProxy.minusBalance(1234, 100);
		
		int newAmount = this.dbProxy.balances.get(1234);
		
		assertEquals(newAmount, 0);
	}

	
	@Test
	void test_should_throw_on_decrease_balance_when_not_enough_balance() {
		Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
			this.dbProxy.minusBalance(1234, 110);
			
			int newAmount = this.dbProxy.balances.get(1234);
		});
	}
	
	@Test
	void test_should_increase_balance_when_money_inserted() {
		this.dbProxy.plusBalance(1234, 10);
		
		int newAmount = this.dbProxy.balances.get(1234);
		
		assertEquals(newAmount, 110);
	}
	
	@Test
	void test_should_return_balance_information_when_asked_by_valid_user() {
		int balance = this.dbProxy.checkTheBalance(1234);
				
		assertEquals(balance, 100);
	}
	
	
	@Test
	void test_should_throw_error_on_balance_information_when_asked_by_invalid_user() {
		int invalidUserId = 83051;
		//not paying attention to the specific error class
		//throwing UnsupportedOperationException in every case to be fast
		Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
			int balance = this.dbProxy.checkTheBalance(invalidUserId);
			
			int newAmount = this.dbProxy.balances.get(1234);
		});
	}
	
	@Test
	void test_should_not_verify_an_invalid_user() {
		int invalidUserId = 83051;

		
		//not paying attention to the specific error class
		//throwing UnsupportedOperationException in every case to be fast
		Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
			String password = this.dbProxy.selectPasswordByAccountNum(invalidUserId);
		});
	}

	
	@Test
	void test_should_verify_a_valid_user() {
		// please refer -> @BeforeEach
		String password = this.dbProxy.selectPasswordByAccountNum(1234);
		assertEquals(password, "1234");
		
		// please refer -> @BeforeEach
		password = this.dbProxy.selectPasswordByAccountNum(9876);
		assertEquals(password, "4567");
	}
}

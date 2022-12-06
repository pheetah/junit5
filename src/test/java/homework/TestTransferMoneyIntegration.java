package homework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class TestTransferMoneyIntegration {

	private int maximumWithdrawal = 5;
	private int minimumWithdarawal = 1;
	private int totalFundInAtm = 100;
	private int minimumCashInATm = 50;
	private ATM atm;
	private NetworkToBank networkToBank;
	private CashDispenser cashDispenser;
	private Display display;
	private Account account = new Account();
	private Card card = new Card(1234);
	private CardReader cardReader;
	private DatabaseProxy dbProxy;

	@BeforeEach
	void initializeAtm() {
		this.atm = new ATM();
		this.networkToBank = Mockito.mock(NetworkToBank.class);
		this.display = Mockito.mock(Display.class);
		this.dbProxy = Mockito.mock(DatabaseProxy.class);
		this.cashDispenser = Mockito.mock(CashDispenser.class);

		this.atm
		.useNetworkToBank(this.networkToBank)
		.useDisplay(this.display)
		.useDbProxy(this.dbProxy)
		.useCashDispenser(this.cashDispenser)
		;
		
		this.atm.startup(
				this.totalFundInAtm, 
				this.maximumWithdrawal, 
				this.minimumWithdarawal, 
				this.minimumCashInATm
		);	
	}
	
	
	//even though real databases are created inside function, guaranteed db resets
	@AfterEach
	void reset() {
		this.dbProxy = Mockito.mock(DatabaseProxy.class); 
	}

	//happy path
	@Test
	void test_user_can_send_money_to_other_user() {
		DatabaseProxy realDatabase = new DatabaseProxy();
		realDatabase.balances.put(1234, 100);
		realDatabase.balances.put(9876, 100);
		
		realDatabase.accounts.put(1234, "1234");
		realDatabase.accounts.put(9876, "4567");
		
		this.atm.useDbProxy(realDatabase);
		
		this.account.account_number = 9876;
		this.account.enter(this.atm, "4567");
		this.account.transfer(this.atm, 1234, 10);
		
		int newSenderBalance = realDatabase.balances.get(9876);
		int newReceiverBalance = realDatabase.balances.get(1234);
		
		assertEquals(newSenderBalance, 90);
		assertEquals(newReceiverBalance, 110);
	}

	
	//fail case
	@Test
	void test_atm_should_display_message_when_user_tries_to_send_more_money_than_he_has() {
		DatabaseProxy realDatabase = new DatabaseProxy();
		realDatabase.balances.put(1234, 100);
		realDatabase.balances.put(9876, 100);
		
		realDatabase.accounts.put(1234, "1234");
		realDatabase.accounts.put(9876, "4567");
		
		this.atm.useDbProxy(realDatabase);
		
		this.account.account_number = 9876;
		this.account.enter(this.atm, "4567");
		this.account.transfer(this.atm, 1234, 110);
		
		int newSenderBalance = realDatabase.balances.get(9876);
		int newReceiverBalance = realDatabase.balances.get(1234);
		
		Mockito.verify(this.display, Mockito.times(1)).display("You can not send money, not enough balance.");
	}
	
	
	//normally I would create a better enter() function
	@Test
	void test_should_not_allow_user_to_verify_if_account_is_not_valid() {
		DatabaseProxy realDatabase = new DatabaseProxy();
		realDatabase.accounts.put(1234, "1234");
		realDatabase.accounts.put(9876, "4567");
		
		this.atm.useDbProxy(realDatabase);
		
		int invalidUserId = 7302;
		
		this.account.account_number = invalidUserId;
		this.account.enter(this.atm, "3751");
		
		Mockito.verify(this.display, Mockito.times(1)).display("Specified account does not exist.");
	}
	
	
	@Test
	void test_should_display_message_when_user_enters_wrong_pin() {
		DatabaseProxy realDatabase = new DatabaseProxy();
		realDatabase.accounts.put(1234, "1234");
		realDatabase.accounts.put(9876, "4567");
		
		this.atm.useDbProxy(realDatabase);
		
		int validUserId = 1234;
		
		this.account.account_number = validUserId;
		this.account.enter(this.atm, "3751");
		
		Mockito.verify(this.display, Mockito.times(1)).display("You entered the wrong PIN.");
	}

	@Test
	void test_should_increase_balance_when_user_deposits() {
		DatabaseProxy realDatabase = new DatabaseProxy();
		realDatabase.accounts.put(1234, "1234");
		realDatabase.accounts.put(9876, "4567");
		realDatabase.balances.put(1234, 100);
		realDatabase.balances.put(9876, 100);

		this.atm.useDbProxy(realDatabase);
		
		int validUserId = 1234;
		
		this.account.account_number = validUserId;
		this.account.enter(this.atm, "1234");
		this.account.deposit(this.atm, 13);
		
		int newBalance = realDatabase.balances.get(1234);
		assertEquals(newBalance, 113);
	}
	
	@Test
	void test_should_decrease_balance_when_user_withdraws_with_enough_balance() {
		DatabaseProxy realDatabase = new DatabaseProxy();
		realDatabase.accounts.put(1234, "1234");
		realDatabase.accounts.put(9876, "4567");
		realDatabase.balances.put(1234, 100);
		realDatabase.balances.put(9876, 100);

		this.atm.useDbProxy(realDatabase);
		
		int validUserId = 1234;
		
		this.account.account_number = validUserId;
		this.account.enter(this.atm, "1234");
		this.account.withdraw(this.atm, 25);
		
		int newBalance = realDatabase.balances.get(1234);
		assertEquals(newBalance, 75);
	}
	
	@Test
	void test_should_display_error_messsage_when_user_withdraws_with_enough_balance() {
		DatabaseProxy realDatabase = new DatabaseProxy();
		realDatabase.accounts.put(1234, "1234");
		realDatabase.accounts.put(9876, "4567");
		realDatabase.balances.put(1234, 100);
		realDatabase.balances.put(9876, 100);

		this.atm.useDbProxy(realDatabase);
		
		int validUserId = 1234;
		
		this.account.account_number = validUserId;
		this.account.enter(this.atm, "1234");
		this.account.withdraw(this.atm, 125);
		
		Mockito.verify(this.display, Mockito.times(1)).display("You don't have enough balance.");
	}
}

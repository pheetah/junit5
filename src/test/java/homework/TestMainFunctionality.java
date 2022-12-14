package homework;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

class TestMainFunctionality {
	private ATM atm;
	private NetworkToBank networkToBank;
	private Display display;
	private Account account = new Account();
	private Card card = new Card(1234);
	private CardReader cardReader;
	private DatabaseProxy dbProxy;
	private CashDispenser cashDispenser;
	
	@BeforeEach
	void initializeAtm() {
		this.card.status = "VALID";
		
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
		
		this.cardReader = new CardReader(this.atm);
	}

	@Test
	void test_should_read_card_when_valid_card_inserted() {
		this.cardReader = Mockito.mock(CardReader.class);
		this.account.insertCard(this.cardReader, this.card);
		
		Mockito.verify(this.cardReader, Mockito.times(1)).checkCardValidity(this.card);
		Mockito.verify(this.cardReader, Mockito.times(1)).readCard(this.card);
		Mockito.verify(this.cardReader, Mockito.times(1)).sendCardInformation();

	}
	
	@Test
	void test_should_send_card_information_to_atm_when_valid_card_inserted() {
		ATM mock_atm = Mockito.mock(ATM.class);
		this.cardReader = new CardReader(mock_atm);
		this.account.insertCard(this.cardReader, this.card);
		
		Mockito.verify(mock_atm, Mockito.times(1)).setCardInformation(this.card);
	}

	@Test
	void test_should_display_error_message_when_wrong_password_is_entered() {
		this.card.status = "INVALID";
		ATM mock_atm = Mockito.mock(ATM.class);
		this.cardReader = new CardReader(mock_atm);
		this.account.insertCard(this.cardReader, this.card);
		
		Mockito.verify(mock_atm, Mockito.times(1)).showErrorMessage("Inserted card is not valid.");
	}
	
	@Test
	void test_should_authorize_user_when_correct_pin_is_entered() {
		String validPin = "1234";
		ATM mock_atm = Mockito.mock(ATM.class);
		when(mock_atm.verify(validPin)).thenReturn(true);
		
		this.account.account_number = 1234;
		this.account.enter(mock_atm, validPin);
		
		Mockito.verify(mock_atm, Mockito.times(1)).readAccountNum(this.account.account_number);
	}
	
	@Test
	void test_should_show_error_message_on_display_when_incorrect_pin_is_entered() {
		ATM mock_atm = Mockito.mock(ATM.class);
		when(mock_atm.verify("1345")).thenReturn(false);
		
		this.account.account_number = 1234;
		this.account.enter(mock_atm, "1234");
				
		Mockito.verify(mock_atm, Mockito.times(1)).showErrorMessage("You entered the wrong PIN.");
	}
	
	@Test
	void test_atm_should_correctly_verify_password_when_passwords_match() {
		String dbPin = "0000";
		String enteredPin = "0000";
		
		when(this.dbProxy.selectPasswordByAccountNum(1234)).thenReturn(dbPin);
		this.atm.readAccountNum(1234);
		
		assertEquals(this.atm.verify(enteredPin), true);
	}
	
	@Test
	void test_atm_should_not_verify_password_when_passwords_dont_match() {
		String dbPin = "0000";
		String enteredPin = "1111";
		
		when(this.dbProxy.selectPasswordByAccountNum(1234)).thenReturn(dbPin);
		this.atm.readAccountNum(1234);
		
		assertEquals(this.atm.verify(enteredPin), false);
	}
	
	@Test
	void test_atm_should_freeze_card_when_user_enters_wrong_password_three_times() {
		String user_entered_pin = "1345";
		ATM mock_atm = Mockito.mock(ATM.class);
		when(mock_atm.verify(user_entered_pin)).thenReturn(false);
		
		this.account.account_number = 1234;
		this.account.enter(mock_atm, user_entered_pin);
		this.account.enter(mock_atm, user_entered_pin);
		this.account.enter(mock_atm, user_entered_pin);

		Mockito.verify(mock_atm, Mockito.times(3)).showErrorMessage("You entered the wrong PIN.");
		Mockito.verify(mock_atm, Mockito.times(1)).freezeCard();
	}
	
    @ParameterizedTest(name = "available amounts entered by the user.")
    @ValueSource(ints = { 10, 9, 1 })
	void atm_should_deliver_cash_if_there_is_enough_balance(int amount) {
		int available_mock_balance = 10;
		
		//log user in, before delivering
		this.account.account_number = 1234;
		this.atm.readAccountNum(this.account.account_number);

		when(this.dbProxy.checkTheBalance(1234)).thenReturn(available_mock_balance);
		

		
		this.atm.deliverCash(amount);
		
		Mockito.verify(this.dbProxy, Mockito.times(1)).minusBalance(this.account.account_number, amount);
		//Mockito.verify(this.cashDispenser, Mockito.times(1)).dispenseCash(amount);
	}
    
    @ParameterizedTest(name = "unavailable amounts entered by the user.")
    @ValueSource(ints = { 11, 100 })
	void atm_should_not_deliver_cash_if_there_is_not_enough_balance(int amount) {
		int available_mock_balance = 10;
		
		//log user in, before delivering
		this.account.account_number = 1234;
		this.atm.readAccountNum(this.account.account_number);

		when(this.dbProxy.checkTheBalance(amount)).thenReturn(available_mock_balance);
		
		
		this.atm.deliverCash(amount);
		
		Mockito.verify(this.display, Mockito.times(1)).display("You don't have enough balance.");
	}
    
	@Test
	void test_should_deposit_cash_when_users_enters_an_amount() {		
		int user_entered_deposit = 10;
		
		//log user in, before deposit
		this.account.account_number = 1234;
		this.atm.readAccountNum(this.account.account_number);
		
		this.account.deposit(this.atm, user_entered_deposit);

		Mockito.verify(this.dbProxy, Mockito.times(1)).plusBalance(this.account.account_number, user_entered_deposit);
		Mockito.verify(this.cashDispenser, Mockito.times(1)).insertCash(user_entered_deposit);
	}
	
	@Test
	void test_should_make_transaction_when_user_transfers_money() {		
		int user_entered_amount = 10;
		
		//log user in, before transaction
		this.account.account_number = 1234;
		int accountIdToSend = 5678;
		
		this.atm.readAccountNum(this.account.account_number);
				
		this.account.transfer(this.atm, accountIdToSend, user_entered_amount);

		
		Mockito.verify(this.dbProxy, Mockito.times(1)).minusBalance(this.account.account_number, user_entered_amount);
		Mockito.verify(this.dbProxy, Mockito.times(1)).plusBalance(accountIdToSend, user_entered_amount);
	}
	
	@Test
	void test_should_select_balance_information_when_needed_to_display() {		
		this.account.account_number = 1234;
						
		this.account.checkBalance(this.atm);

		Mockito.verify(this.dbProxy, Mockito.times(1)).selectBalanceInformation();
	}

	@Test
	void test_should_display_the_balance_with_curreny_when_received_from_db() {		
		this.account.account_number = 1234;
		
		int return_mock_balance = 100;
		when(this.dbProxy.selectBalanceInformation()).thenReturn(return_mock_balance);
		
		this.account.checkBalance(this.atm);

		Mockito.verify(this.display, Mockito.times(1)).display(return_mock_balance + " TL");
	}
	
	@Test
	void test_atm_should_display_error_message_when_unverified_user_enters() {
		doThrow(UnsupportedOperationException.class)
		.when(this.dbProxy)
		.selectPasswordByAccountNum(8888);
		
		//log user in, before transaction
		this.account.account_number = 8888;		
		this.atm.readAccountNum(this.account.account_number);
		
		this.account.enter(this.atm, "1234");
		
		Mockito.verify(this.display, Mockito.times(1)).display("Specified account does not exist.");

	}
}

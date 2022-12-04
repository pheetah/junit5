import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestMainFunctionality {
	private ATM atm;
	private NetworkToBank networkToBank;
	private Display display;
	private Account account = new Account();
	private Card card = new Card(1234);
	private CardReader cardReader;
	private DatabaseProxy dbProxy;
	
	@BeforeEach
	void initializeAtm() {
		this.card.status = "VALID";
		
		this.atm = new ATM();
		this.networkToBank = Mockito.mock(NetworkToBank.class);
		this.display = Mockito.mock(Display.class);
		this.dbProxy = Mockito.mock(DatabaseProxy.class);
		
		this.atm
		.useNetworkToBank(networkToBank)
		.useDisplay(display)
		.useDbProxy(this.dbProxy)
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
		ATM mock_atm = Mockito.mock(ATM.class);
		when(mock_atm.verify("1345")).thenReturn(false);
		
		this.account.account_number = 1234;
		this.account.enter(mock_atm, "1234");
		this.account.enter(mock_atm, "1234");
		this.account.enter(mock_atm, "1234");

		Mockito.verify(mock_atm, Mockito.times(3)).showErrorMessage("You entered the wrong PIN.");
		Mockito.verify(mock_atm, Mockito.times(1)).freezeCard();
	}
}

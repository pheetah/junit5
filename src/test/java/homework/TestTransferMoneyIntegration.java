package homework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
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
	
	@BeforeEach
	void initializeAtm() {
		this.atm = new ATM();
		this.networkToBank = Mockito.mock(NetworkToBank.class);
		this.cashDispenser = Mockito.mock(CashDispenser.class);

		this.atm
		.useNetworkToBank(networkToBank)
		.useCashDispenser(cashDispenser)
		;
		
		this.atm.startup(
				this.totalFundInAtm, 
				this.maximumWithdrawal, 
				this.minimumWithdarawal, 
				this.minimumCashInATm
		);	
	}
	
	@Test
	void test_user_can_send_money_to_other_user() {

	}

}

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;  

// Tests: 4.1.1 -> FR1
class TestAtmInitialization {
	
	public static class AtmConfiguredCorrectly{
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
		}
		
		@Test
		void test_atm_should_start_up_when_proper_inputs_provided() {
			this.atm.startup(
					this.totalFundInAtm, 
					this.maximumWithdrawal, 
					this.minimumWithdarawal, 
					this.minimumCashInATm
			);
			assertEquals(this.atm.totalFund, this.totalFundInAtm);
			assertEquals(this.atm.maximumAmount, this.maximumWithdrawal);
			assertEquals(this.atm.minimumAmount, this.minimumWithdarawal);
			assertEquals(this.atm.minimumCash, this.minimumCashInATm);
		}
	
		@Test
		void test_atm_should_start_up_when_zero_fund_provided() {
			this.totalFundInAtm = 0;
			this.atm.startup(
					this.totalFundInAtm, 
					this.maximumWithdrawal, 
					this.minimumWithdarawal, 
					this.minimumCashInATm
			);
			assertEquals(this.atm.totalFund, this.totalFundInAtm);
			assertEquals(this.atm.maximumAmount, this.maximumWithdrawal);
			assertEquals(this.atm.minimumAmount, this.minimumWithdarawal);
			assertEquals(this.atm.minimumCash, this.minimumCashInATm);
		}
		
		@Test
		void test_atm_should_not_start_up_when_negative_fund_inserted() {
			this.totalFundInAtm = -1;
		    Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
				this.atm.startup(
						this.totalFundInAtm, 
						this.maximumWithdrawal, 
						this.minimumWithdarawal, 
						this.minimumCashInATm
				);
		    });
		}
		
		@Test
		void test_atm_should_set_initial_cash_when_received_from_operator_panel() {
			Money mockInitialCash = Mockito.mock(Money.class);

			OperatorPanel panel = Mockito.mock(OperatorPanel.class);
			when(panel.getInitialCash()).thenReturn(mockInitialCash);
			
			this.atm
			.startup(
					this.totalFundInAtm, 
					this.maximumWithdrawal, 
					this.minimumWithdarawal, 
					this.minimumCashInATm
			);
			
			this.atm.dispenseInitialCash(panel);
			
			Mockito.verify(this.cashDispenser, Mockito.times(1)).setInitialCash(mockInitialCash);
		}
	}

	public static class AtmIsNotConfiguredCorrectly{
		private int maximumWithdrawal = 5;
		private int minimumWithdarawal = 1;
		private int totalFundInAtm = 100;
		private int minimumCashInATm = 50;
		private ATM atm;
		
		@Test
		void test_atm_should_throw_error_when_atm_is_not_configured_properly() {
			Exception exception = assertThrows(NullPointerException.class, () -> {
				this.atm
				.startup(
						this.totalFundInAtm, 
						this.maximumWithdrawal, 
						this.minimumWithdarawal, 
						this.minimumCashInATm
				);
			});
		}
	}

}
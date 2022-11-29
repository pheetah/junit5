import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// Tests: 4.1.1 -> FR1
class TestAtmInitialization {
	private int maximumWithdrawal = 5;
	private int minimumWithdarawal = 1;
	private int totalFundInAtm = 100;
	private int minimumCashInATm = 50;
	private ATM atm;
	
	@BeforeEach
	void initializeAtm() {
		this.atm = new ATM();
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
}
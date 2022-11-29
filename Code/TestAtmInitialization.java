import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// Tests: 4.1.1 -> FR1
class TestAtmInitialization {
	private int maximumWithdrawal = 5;
	private int minimumWithdarawal = 1;
	private int totalFundInAtm = 100;
	private int minimumCashInATm = 50;
	private ATM atm = new ATM();
	
	@BeforeEach
	void initializeAtm() {
		this.atm.startup(
			totalFundInAtm, 
			maximumWithdrawal, 
			minimumWithdarawal, 
			minimumCashInATm
		);
	}
	
	@Test
	void atmStartedUpSuccessfully() {
		assertEquals(this.atm.totalFund, this.totalFundInAtm);
	}
}
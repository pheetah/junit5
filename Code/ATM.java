import java.text.SimpleDateFormat;

// does not apply the pattern
public class ATM implements ATMBuilder {

	//converted member variables to public to avoid getters, since not a serious project
	public int minimumAmount;
	public int maximumAmount;
	private int limitTimeForOperation;
	public int totalFund;
	public int minimumCash;
	private DatabaseProxy dbProxy;
	private NetworkToBank networkToBank;
	private Display display;
	private CashDispenser cashDispenser;
	/**
	 * 
	 * @param password
	 */
	public String verify(String password) {
		// TODO - implement ATM.verify
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void startup(
			int totalFund,
			int maximumWithdrawal,
			int minimumWithdrawal,
			int minimumCash
	){
		if(totalFund < 0) {
			throw new UnsupportedOperationException();
		}
		
		this.totalFund = totalFund;
		this.maximumAmount = maximumWithdrawal;
		this.minimumAmount = minimumWithdrawal;
		this.minimumCash = minimumCash;
		
		Money initialCash = new Money(this.totalFund);
		
		//change here later with proper logic
		this.networkToBank.openConnection();
		this.cashDispenser.setInitialCash(initialCash);
	}

	@Override
	public ATM useDbProxy(DatabaseProxy dbProxy) {
		return this;
	}
	

	@Override
	public ATM useNetworkToBank(NetworkToBank networkToBank) {
		this.networkToBank = networkToBank;
		return this;
	}
	
	
	@Override
	public ATM useCashDispenser(CashDispenser cashDispenser) {
		this.cashDispenser = cashDispenser;
		return this;
	}
	/**
	 * 
	 * @param accountNum
	 */
	public void readAccountNum(int accountNum) {
		// TODO - implement ATM.readAccountNum
		throw new UnsupportedOperationException();
	}

	public Message checkAvailabilityOfCashInATM() {
		Message isCashAvailable = new Message();
		if(this.minimumCash <= 0) {
			isCashAvailable.isProceedable = true;
			return isCashAvailable;
		}
		
		isCashAvailable.isProceedable = false;
		return isCashAvailable;
	}

	public Message verifyInputAmount() {
		// TODO - implement ATM.verifyInputAmount
		throw new UnsupportedOperationException();
	}

	public SimpleDateFormat checkTime() {
		// TODO - implement ATM.checkTime
		throw new UnsupportedOperationException();
	}

}
package homework;

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
	public String atmState = "OFF"; //normally would create an Enum or sth.
	public Card lastInsertedCard;
	private int latestAccount;
	private DatabaseProxy databaseProxy;
	/**
	 * 
	 * @param password
	 */
	public boolean verify(String password) {
		String dbPassword = "";
		
		System.out.println("DEBUG: " + this.latestAccount + " and " + password);
		
		try {
			dbPassword = this.databaseProxy.selectPasswordByAccountNum(this.latestAccount);
		}
		catch(Exception e) {
			this.display.display("Specified account does not exist.");
			return false;
		}
		
		if(password.equalsIgnoreCase(dbPassword)) {
			return true;
		}else {
			return false;
		}
		
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
		this.atmState = "IDLE";
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
	
	public ATM useDbProxy(DatabaseProxy databaseProxy) {
		this.databaseProxy = databaseProxy;
		return this;
	}
	
	
	@Override
	public ATM useDisplay(Display display) {
		this.display = display;
		return this;
	}
	
	/**
	 * 
	 * @param accountNum
	 */
	public void readAccountNum(int accountNum) {
		this.latestAccount = accountNum;
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
	
	public void dispenseInitialCash(OperatorPanel panel) {
		Money initalCash = panel.getInitialCash();
		this.cashDispenser.setInitialCash(initalCash);
	}
	
	public void switchOn(OperatorPanel panel) {
		this.startup(100, 0, 0, 0);
		Money initalCash = panel.getInitialCash();
		this.cashDispenser.setInitialCash(initalCash);
	}
	
	public void setCardInformation(Card card){
		this.lastInsertedCard = card;
	}
	
	public void freezeCard() {
		this.databaseProxy.freezeCard(lastInsertedCard);
	}
	
	public void setBankingConfigurationAndConnection(
			int maximumWithdrawal,
			int minimumWithdrawal,
			int minimumCash
	) {
		this.maximumAmount = maximumWithdrawal;
		this.minimumAmount = minimumWithdrawal;
		this.minimumCash = minimumCash;		

		this.networkToBank.openConnection();
	}
	
	public void switchOff() {
		this.shutdown();
		
		try {
			this.networkToBank.closeConnection();
			this.display.display("Closed network to bank connection successfully");
		}catch(Exception e) {
			// normally it should be declared in separate module as constant!
			String shutdownErrorMessage = "Can't close network connection, contact mainteiners!";
			
			this.display.display(shutdownErrorMessage);
		}
	}
	
	public void shutdown() {
		this.atmState = "OFF";
	}

	public Message verifyInputAmount() {
		// TODO - implement ATM.verifyInputAmount
		throw new UnsupportedOperationException();
	}

	public SimpleDateFormat checkTime() {
		// TODO - implement ATM.checkTime
		throw new UnsupportedOperationException();
	}
	
	public void connectToBank() {
		this.networkToBank.openConnection();
	}
	
	public void showErrorMessage(String errorMessage) {
		this.display.display(errorMessage);
	}

	public boolean deliverCash(int amount) {
		int availableBalance = this.databaseProxy.checkTheBalance(this.latestAccount);
		
		if(availableBalance >= amount) {
			this.databaseProxy.minusBalance(this.latestAccount, amount);
			this.cashDispenser.dispenseCash(amount);

			return true;
		}else {
			this.display.display("You don't have enough balance.");
			return false;
		}
	}
	
	
	public void deposit(int amount) {
		this.cashDispenser.insertCash(amount);
		this.databaseProxy.plusBalance(this.latestAccount, amount);
	}
	
	public void transfer(int accountIdToSend, int amount) {
		Transaction transaction = new Transaction(this.databaseProxy);

		if(!this.databaseProxy.accounts.containsKey(accountIdToSend)) {
			this.display.display("The receiver account is invalid.");
			return;
		};
		
		boolean isMoneySent = transaction.transfer(this.latestAccount, accountIdToSend, amount);
		
		if(!isMoneySent) {
			this.display.display("You can not send money, not enough balance.");
		}
	}
	
	// DEPRECATED
	public void checkBalance() {
		int balance = this.databaseProxy.selectBalanceInformation();
		this.display.display(Integer.toString(balance) + " TL");
	}
	
	public void selectBalanceInfo(int account_id) {
		int balance = this.databaseProxy.checkTheBalance(account_id);
		this.display.display(Integer.toString(balance) + " TL");
	}
}
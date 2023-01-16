package homework;

public class Account {

	// not losing time on creating gettters / setters
	public int account_number;
	public String password;
	public double balance;
	public int accountType;
	private int wrongEntranceCount = 0;

	public void insertCard(CardReader cardReader, Card card) {
		cardReader.checkCardValidity(card);
		cardReader.readCard(card);
		cardReader.sendCardInformation();
		this.wrongEntranceCount = 0;
	}
	
	
	public boolean enter(ATM atm, String password) {
		atm.readAccountNum(this.account_number);
		boolean isValid = atm.verify(password);
	
		
		if(!isValid) {
			atm.showErrorMessage("You entered the wrong PIN.");
			this.wrongEntranceCount++;
			
			if(this.wrongEntranceCount >= 3) {
				atm.freezeCard();
			}
			
			return false;
		}
		
		return true;
	}
	
	
	public void withdraw(ATM atm, int amount) {
		atm.deliverCash(amount);
	}
	
	public void deposit(ATM atm, int amount) {
		atm.deposit(amount);
	}
	
	public void transfer(ATM atm, int accountIdToSend, int amount){
		atm.transfer(accountIdToSend, amount);
	}
	
	public void checkBalance(ATM atm) {
		atm.checkBalance();
	}
	
}
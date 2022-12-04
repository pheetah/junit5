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
	
	
	public void enter(ATM atm, String password) {
		boolean isValid = atm.verify(password);
		
		if(isValid) {
			atm.readAccountNum(this.account_number);
		}else {
			atm.showErrorMessage("You entered the wrong PIN.");
			this.wrongEntranceCount++;
			
			if(this.wrongEntranceCount >= 3) {
				atm.freezeCard();
			}
		}
	}
	
	
	public void enterWrongPasswordThreeTimes() {
		
	}
}
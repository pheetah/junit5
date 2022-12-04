public class Account {

	// not losing time on creating gettters / setters
	public int account_number;
	public String password;
	public double balance;
	public int accountType;

	public void insertCard(CardReader cardReader, Card card) {
		cardReader.checkCardValidity(card);
		cardReader.readCard(card);
		cardReader.sendCardInformation();
	}
	
}
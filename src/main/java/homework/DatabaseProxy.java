package homework;

public class DatabaseProxy {

	public Card frozenCard;
	
	/**
	 * 
	 * @param accountNum
	 */
	public String selectPasswordByAccountNum(int accountNum) {
		return "any";
	}
	
	public void freezeCard(Card card) {
		this.frozenCard = card;
	}

	public void minusBalance(int account_number, int amount) {
		System.out.println("minus balance: " + amount);
	}

	public void plusBalance(int account_number, int amount) {
		System.out.println("plus balance: " + amount);
	}

	public int createNewAccount() {
		// TODO - implement DatabaseProxy.createNewAccount
		throw new UnsupportedOperationException();
	}
	
	public int selectBalanceInformation() {
		return 100;
	}

	public int checkTheBalance(int amount) {
		// doesn't matter since it's going to be mocked in tests;
		// Since no tests will be added for DatabaseProxy class
		return 100;
	}

}
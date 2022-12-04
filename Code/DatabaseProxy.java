public class DatabaseProxy {

	public Card frozenCard;
	
	/**
	 * 
	 * @param accountNum
	 */
	public String selectPasswordByAccountNum(int accountNum) {
		// TODO - implement DatabaseProxy.selectPasswordByAccountNum
		throw new UnsupportedOperationException();
	}
	
	public void freezeCard(Card card) {
		this.frozenCard = card;
	}

	public void minusBalance(int amount) {
		System.out.println("minus balance: " + amount);
	}

	public void plusBalance(int amount) {
		System.out.println("plus balance: " + amount);
	}

	public int createNewAccount() {
		// TODO - implement DatabaseProxy.createNewAccount
		throw new UnsupportedOperationException();
	}

	public int checkTheBalance(int amount) {
		// doesn't matter since it's going to be mocked in tests;
		// Since no tests will be added for DatabaseProxy class
		return 100;
	}

}
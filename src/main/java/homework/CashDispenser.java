package homework;

public class CashDispenser {

	private Log log;
	private Money cashOnHand;

	/**
	 * 
	 * @param log
	 */
	public CashDispenser() {}

	/**
	 * 
	 * @param initialCash
	 */
	public void setInitialCash(Money initialCash) {
		System.out.println("set initial cash value: " + initialCash.value);
	}

	/**
	 * 
	 * @param amount
	 */
	public boolean checkCashOnHand(int amount) {
		return true;
		// TODO - implement CashDispenser.checkCashOnHand
		//throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param amount
	 */
	public void dispenseCash(int amount) {
		System.out.println("dispence cash: " + amount);
	}

	public void insertCash(int amount) {
		System.out.println("inserted cash: " + amount);
	}

}
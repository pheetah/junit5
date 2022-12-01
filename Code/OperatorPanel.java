public class OperatorPanel {
	
	private ATM atm;

	/**
	 * 
	 * @param atm
	 */
	public OperatorPanel(ATM atm) {
		// TODO - implement OperatorPanel.OperatorPanel
		this.atm = atm;
		//throw new UnsupportedOperationException();
	}
	
	public void switchOn() {
		//I'm aware it's bad practice.
		this.atm.startup(100,0,0,0);
	}

	public Money getInitialCash() {
		// TODO - implement OperatorPanel.getInitialCash
		Money initialMoney = new Money(atm.totalFund);
		return initialMoney;
		//throw new UnsupportedOperationException();
	}

}
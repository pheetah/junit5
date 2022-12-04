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

	public Money getInitialCash() {
		// TODO - implement OperatorPanel.getInitialCash
		Money initialMoney = new Money(100);
		return initialMoney;
		//throw new UnsupportedOperationException();
	}
	
	public void setAtmMaxMinWithdrawalsAndBankingConfig(
			int maxAmount,
			int minAmount,
			int minCash
	) {
		this.atm.setBankingConfigurationAndConnection(maxAmount, minAmount, minCash);
	}
	
	
	public void shutdownAtm() {
		this.atm.switchOff();
	}
}
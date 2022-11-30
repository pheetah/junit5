public interface ATMBuilder {
	void startup(
		int totalFund,
		int maximumWithdrawal,
		int minimumWithdrawal,
		int minimumCash
	);
	
	ATM useDbProxy(DatabaseProxy dbProxy);
	ATM useNetworkToBank(NetworkToBank networkToBank);
	//ATM useNetworkToBank(CashDispenser cashDispenser);
}

public interface ATMBuilder {
	void startup(
		int totalFund,
		int maximumWithdrawal,
		int minimumWithdrawal,
		int minimumCash
	);
	
	ATM useNetworkToBank(NetworkToBank networkToBank);
	ATM useCashDispenser(CashDispenser cashDispenser);
	//ATM useNetworkToBank(CashDispenser cashDispenser);
}

public interface ATMBuilder {
	void startup(
		int totalFund,
		int maximumWithdrawal,
		int minimumWithdrawal,
		int minimumCash
	);
	
	void useDbProxy(DatabaseProxy dbProxy);
}

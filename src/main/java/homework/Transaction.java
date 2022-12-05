package homework;

public class Transaction {

	protected DatabaseProxy dbProxy;
	
	public Transaction(DatabaseProxy dbProxy) {
		this.dbProxy = dbProxy;
	}
	
	public boolean transfer(
			int sendingAccountId,
			int senderAccountId,
			int amount
	) {
		try {
			this.dbProxy.plusBalance(senderAccountId, amount);
			this.dbProxy.minusBalance(sendingAccountId, amount);
			return true;

		}
		catch(Exception e) {
			return false;
		}
	}
}
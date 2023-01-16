package homework;
import java.util.*;

public class DatabaseProxy {

	public List<Card> frozenCards = new ArrayList<Card>();
	
	//accountId-amount
	public Hashtable<Integer, Integer> balances = new Hashtable<Integer, Integer>();
	//accountId-password
	public Hashtable<Integer, String> accounts = new Hashtable<Integer, String>();

	public DatabaseProxy(){
		this.balances.put(1234, 100);
		this.balances.put(9876, 100);

		this.accounts.put(1234, "1234");
		this.accounts.put(9876, "4567");
	}
	
	/**
	 * 
	 * @param accountNum
	 */
	public String selectPasswordByAccountNum(int accountNum) {
		if(this.accounts.containsKey(accountNum)) {
			String password = this.accounts.get(accountNum);
			
			return password;
		}else {
			throw new UnsupportedOperationException();
		}
	}
	
	public void freezeCard(Card card) {
		this.frozenCards.add(card);
	}

	public void minusBalance(int account_number, int amount) {
		System.out.println("minus balance: " + amount);
		System.out.println("from account balance: " + account_number);
		if(this.balances.containsKey(account_number)) {
			int accountAmount = this.balances.get(account_number);
			accountAmount = accountAmount - amount;
			System.out.println("New balance:" + accountAmount);
			if(accountAmount < 0) {
				throw new UnsupportedOperationException();
			}
			
			this.balances.put(account_number, accountAmount);
		}
		
	}

	public void plusBalance(int account_number, int amount) {
		System.out.println("plus balance: " + amount);
		if(this.balances.containsKey(account_number)) {
			int accountAmount = this.balances.get(account_number);
			accountAmount = accountAmount + amount;
			
			System.out.println("New Balance: " + accountAmount);
			this.balances.put(account_number, accountAmount);
		}
	}

	public int createNewAccount() {
		// TODO - implement DatabaseProxy.createNewAccount
		//throw new UnsupportedOperationException();
		return 1;
	}
	
	public int selectBalanceInformation() {
		return 100;
	}
	
	public int selectBalanceInformationv2(int account_number) {
		return 100;
	}

	public int checkTheBalance(int account_id) {
		if(this.balances.containsKey(account_id)) {
			int accountAmount = this.balances.get(account_id);
			return accountAmount;
		}else {
			throw new UnsupportedOperationException();
		}
	}

}
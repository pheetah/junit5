package homework;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;  

public class Main {
	  public static boolean insertCard(Account account, CardReader cardReader) {
		  	System.out.println("Please insert your Card (ID)");
	        // Enter data using BufferReader
		  	Scanner reader= new Scanner(System.in); 
	 
	        // Reading data using readLine
	        int cardId;
	        String cardStatus;
			try {
				String strCardId = reader.next();
				cardId = Integer.parseInt(strCardId);
				
			  	System.out.println("Please insert your Card status 'VALID' or 'INVALID'");

			  	reader = new Scanner(System.in); 
				cardStatus = reader.next();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Invalid Card");
				System.out.println("Ejecting Card...");
				return false;
			}
	        // Printing the read line
	        System.out.println(cardId);
	        Card card = new Card(cardId);
	        card.status = cardStatus;
	        account.insertCard(cardReader, card);
	        return true;
	}
	  
	
	public static ATM initializeAtm() {
		// initialize ATM
		Log log = new Log();
		NetworkToBank networkToBank = new NetworkToBank(log, 12345);
		Display display = new Display();
		DatabaseProxy dbProxy = new DatabaseProxy();
		CashDispenser cashDispenser = new CashDispenser();
		
		ATM atm = new ATM();
		atm
			.useCashDispenser(cashDispenser)
			.useDbProxy(dbProxy)
			.useDisplay(display)
			.useNetworkToBank(networkToBank);
		
		return atm;
	}
	
	  
	  
	public static void main(String[] args) {
		  	ATM atm = initializeAtm();
		  	CardReader cardReader = new CardReader(atm);
			Account account = new Account();
		  	insertCard(account, cardReader);

		  	atm.switchOff();
		  	System.out.println(atm);
	}
	  
}

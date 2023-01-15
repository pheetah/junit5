package homework;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	  public static boolean insertCard() {
		  	System.out.println("Please insert your Card (ID)");
			  
	        // Enter data using BufferReader
	        BufferedReader reader = new BufferedReader(
	            new InputStreamReader(System.in));
	 
	        // Reading data using readLine
	        int cardId;
			try {
				String strCardId = reader.readLine();
				cardId = Integer.parseInt(strCardId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Invalid Card");
				System.out.println("Ejecting Card...");
				return false;
			}
	        // Printing the read line
	        System.out.println(cardId);
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
		  	insertCard();
		  	ATM atm = initializeAtm();
		  	CardReader cardReader = new CardReader(atm);
		  	
		  	atm.switchOff();
		  	System.out.println(atm);
	}
	  
}

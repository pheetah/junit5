package homework;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;  

public class Main {
	  public static boolean insertCard(Account account, CardReader cardReader, ATM atm) {
		  	System.out.println("Please insert your Card (ID)");
	        // Enter data using BufferReader
		  	Scanner reader = new Scanner(System.in); 
	 
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
				System.out.println("Ejecting Card...");
				return false;
			}
	        // Printing the read line
	        System.out.println("DEBUG: " + cardId);
	        Card card = new Card(cardId);
	        account.account_number = cardId;
	        card.status = cardStatus;
	        account.insertCard(cardReader, card);
	        
	        if(cardStatus.equals("VALID")) {
	        	return true;
	        }else {
		        return false;
	        }
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
	
	
	public static boolean login(Account account, ATM atm) {		
		String password;
		try {
		  	System.out.println("Please enter your password");

		  	Scanner reader = new Scanner(System.in); 
			password = reader.next();
			boolean isPasswordCorrect = account.enter(atm, password);
			
			if(isPasswordCorrect) {
				return true;
			}else {
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error on validation password");
			return false;
		}
	}
	
	  
	  
	public static void main(String[] args) {
		  	ATM atm = initializeAtm();
		  	CardReader cardReader = new CardReader(atm);
			Account account = new Account();
		  	boolean isInsertedSuccessfully = insertCard(account, cardReader, atm);
		  	
		  	if(!isInsertedSuccessfully) {
		  		atm.showErrorMessage("Unexpected behaviour occured.");
		  		atm.switchOff();
		  		return;		  		
		  	}
		  	
		  	boolean status = false;
		  	for (int i = 0; i < 3; i++) {
		  		status = login(account, atm);
		  		
		  		if(status == true) {
		  			break;
		  		}
		  	}
		  	
		  	if(status == false) {
		  		atm.showErrorMessage("Unexpected behaviour occured.");
		  		atm.switchOff();
		  		return;
		  	}
		  	

		  	atm.switchOff();
		  	System.out.println(atm);
		  	System.out.println(status);
	}
	  
}

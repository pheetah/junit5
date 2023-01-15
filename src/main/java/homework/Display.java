package homework;

public class Display {

	public String displayMessage = "default";
	
	public Display() {
		// TODO - implement Display.Display
	}

	/**
	 * 
	 * @param message
	 */
	public void display(String message) {
		this.displayMessage = message;
		System.out.println(message);
		// TODO - implement Display.display
		//throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param prompt
	 */
	public int readPIN(String prompt) {
		// TODO - implement Display.readPIN
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param prompt
	 * @param menu
	 */
	public int readMenuChoice(String prompt, String[] menu) {
		// TODO - implement Display.readMenuChoice
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param prompt
	 */
	public Money readAmount(String prompt) {
		// TODO - implement Display.readAmount
		throw new UnsupportedOperationException();
	}

}
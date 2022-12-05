package homework;

public class CardReader {

	private ATM atm;
	private Card card;

	/**
	 * 
	 * @param atm
	 */
	public CardReader(ATM atm) {
		this.atm = atm;
	}
	
	public boolean checkCardValidity(Card card) {
		if(card.status == "VALID") {
			return true;
		}else {
			this.atm.showErrorMessage("Inserted card is not valid.");
			return false;
		}
	}

	public Card readCard(Card card) {
		this.card = card;
		
		return this.card;
	}
	
	public void sendCardInformation() {
		this.atm.setCardInformation(this.card);
	}

	public void ejectCard(ATM atm) {
		System.out.println("Performing: Eject Card!");
	}

	public void retainCard() {
		// TODO - implement CardReader.retainCard
		throw new UnsupportedOperationException();
	}

}
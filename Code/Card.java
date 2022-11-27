public class Card {

	private int number;

	/**
	 * 
	 * @param number
	 */
	public Card(int number) {
		this.number = number;
		// TODO - implement Card.Card
		if(this.number < 0) {
			throw new UnsupportedOperationException();
		}
	}

	public int getNumber() {
		return this.number;
	}

}
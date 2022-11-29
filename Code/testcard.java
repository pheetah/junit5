import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.mockito.Mockito.when;  
import org.mockito.Mockito;

class testcard {

    @ParameterizedTest(name = "proper inputs given.")
    @ValueSource(ints = { 10, 1 })
	void test_should_create_card_when_proper_input_provided() {
		final int inputArgument = 10;
	  	Card card = new Card(inputArgument);
	  	int outputNumber = card.getNumber();
	  	assertEquals(inputArgument, outputNumber);
	}

    @ParameterizedTest(name = "improper inputs given.")
    @ValueSource(ints = { -10, -1 })
	void test_should_throw_error_when_improper_input_provided() {
	    Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
			final int inputArgument = -10;
		  	Card card = new Card(inputArgument);
		  	int number = card.getNumber();
	    });
	}
    
    @Test
    void test_should_accept_when_boundary_input_is_provided() {
		final int boundaryInput = 0;
	  	Card card = new Card(boundaryInput);
	  	int outputNumber = card.getNumber();
	  	assertEquals(boundaryInput, outputNumber);
    }
    
    @Test
    void test_mock_card() {
    	Card mockCard = Mockito.mock(Card.class);
        when(mockCard.getNumber()).thenReturn(8);
        int no = mockCard.getNumber();
        assertEquals(8, mockCard.getNumber());
    }
}

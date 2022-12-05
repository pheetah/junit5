package homework;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

public class TestTransactionUnit {
	private DatabaseProxy dbProxy;
	private Transaction transaction;
	
	@BeforeEach
	public void injectMockDependencies() {
		this.dbProxy = Mockito.mock(DatabaseProxy.class);
		this.transaction = new Transaction(this.dbProxy);
	}
	
	@Test
	public void test_transaction_should_occur_when_performed() {
		int senderId = 1234;
		int receiverId = 9876;
		int amount = 100;
		
		boolean isMoneySent = this.transaction.transfer(senderId, receiverId, amount);
		
		assertEquals(isMoneySent, true);
	}
	
	@Test
	public void test_transaction_should_return_false_when_db_action_cannot_be_performed() {
		int senderId = 1234;
		int receiverId = 9876;
		int amount = 100;

		doThrow(UnsupportedOperationException.class)
		.when(this.dbProxy)
		.plusBalance(receiverId, amount);
		
		boolean isMoneySent = this.transaction.transfer(senderId, receiverId, amount);

		assertEquals(isMoneySent, false);
	}
}

package homework;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestShutDown {

	public static class AtmConfiguredCorrectly{
		private ATM atm;
		private NetworkToBank networkToBank;
		private Display display;

		@BeforeEach
		void initializeAtm() {
			this.atm = new ATM();
			this.networkToBank = Mockito.mock(NetworkToBank.class);
			this.display = Mockito.mock(Display.class);

			this.atm
			.useNetworkToBank(networkToBank)
			.useDisplay(display)
			;
		}
		
		@Test
		void test_atm_should_correctly_shutdown_when_operator_panel_switches_off() {
			OperatorPanel panel = new OperatorPanel(this.atm);
			
			panel.shutdownAtm();
			
			assertEquals(this.atm.atmState, "OFF");
			Mockito.verify(this.networkToBank, Mockito.times(1)).closeConnection();
		}
		
		
		@Test
		void test_display_should_show_error_message_when_panel_cant_shutdown_properly() {
			OperatorPanel panel = new OperatorPanel(this.atm);
			
			doThrow(UnsupportedOperationException.class)
				.when(this.networkToBank)
				.closeConnection();
			
			panel.shutdownAtm();
			
			assertEquals(this.atm.atmState, "OFF");
			//assertEquals(this.display.displayMessage, "Can't close network connection, contact mainteiners!");
			Mockito.verify(this.display, Mockito.times(1)).display("Can't close network connection, contact mainteiners!");

		}
		
	}

	
	public static class AtmNotConfiguredCorrectly{
		private ATM atm;
		private NetworkToBank networkToBank;

		@BeforeEach
		void initializeAtm() {
			this.atm = new ATM();
		}
		
		@Test
		void test_throw_error_when_not_configured_properly() {
			Exception exception = assertThrows(NullPointerException.class, () -> {
				OperatorPanel panel = new OperatorPanel(this.atm);
				
				panel.shutdownAtm();
				
				assertEquals(this.atm.atmState, "OFF");
				Mockito.verify(this.networkToBank, Mockito.times(1)).closeConnection();
			});
		}
	}
}

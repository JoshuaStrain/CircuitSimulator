package circuit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ORGateTest extends CommonComponentTests{
	List<Boolean> inputs;
	
	Component createComponent(){
		return new ORGate();
	}
	
	int getExpectedNumberOfInputs(){
		return 2;
	}
	
	@Test
	public void ORWithInputTwoTruesReturnsATrue() {
		inputs = Arrays.asList(new Boolean(true), new Boolean(true));
		assertTrue(component.processInputs(inputs));
	}


	@Test
	public void ORWithInputTwoFalsesReturnsAFalse() {
		inputs = Arrays.asList(new Boolean(false), new Boolean(false));
		assertFalse(component.processInputs(inputs));
	}

	@Test
	public void ORWithInputTrueAndFalseReturnsTrue() {
		inputs = Arrays.asList(new Boolean(true), new Boolean(false));
		assertTrue(component.processInputs(inputs));
	}

	@Test
	public void ORWithInputFalseAndTrueReturnsTrue() {
		inputs = Arrays.asList(new Boolean(false), new Boolean(true));
		assertTrue(component.processInputs(inputs));
	}
	
	@Test
	public void setBusesWithOnlyOneInputReturnsFalse(){
		assertFalse(component.processInputs(Arrays.asList(new Boolean(false))));
	}
	
}

package circuit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ANDGateTest extends CommonComponentTests{
	List<Boolean> inputs;
	
	@Override
	Component createComponent(){
		return new ANDGate();
	}
	
	int getExpectedNumberOfInputs(){
		return 2;
	}

	@Test
	public void ANDWithTwoTruesInputReturnsTrue() {
		inputs = Arrays.asList(new Boolean(true), new Boolean(true));
		assertTrue(component.processInputs(inputs));
	}

	@Test
	public void ANDWithTwoFalsesReturnsFalse() {
		inputs = Arrays.asList(new Boolean(false), new Boolean(false));
		assertFalse(component.processInputs(inputs));
	}

	@Test
	public void ANDWithInputTrueAndFalseReturnsFalse() {
		inputs = Arrays.asList(new Boolean(true), new Boolean(false));
		assertFalse(component.processInputs(inputs));
	}
	

}

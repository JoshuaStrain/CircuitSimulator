package circuit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class NOTGateTest extends CommonComponentTests{
	List<Boolean> inputs;
	
	Component createComponent(){
		return new NOTGate();
	}
	
	int getExpectedNumberOfInputs(){
		return 1;
	}

	@Test
	public void NOTWithInputFalseReturnsTrue() {
		inputs = Arrays.asList(new Boolean(false));
		assertTrue(component.processInputs(inputs));
	}

	@Test
	public void NOTWithInputTrueReturnsFalse() {
		inputs = Arrays.asList(new Boolean(true));
		assertFalse(component.processInputs(inputs));
	}
	
}

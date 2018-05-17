package circuit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class CommonComponentTests {
	Component component;

	abstract Component createComponent();
	abstract int getExpectedNumberOfInputs();

	@Before
	public void setUp(){
		component = createComponent();
	}

	@Test
	public void canary()
	{
		assertTrue(true);
	}

	@Test
	public void ComponentMethodGetNumberOfRequiredInputsReturnsExpectedNumber(){
		assertEquals(getExpectedNumberOfInputs(), component.getRequiredNumberOfInputs());
	}

	
	@Test

	public void setBusesWithNullInputsSetsFlagToFalse(){
		component.setBuses(null, new Bus());
		assertFalse(component.allBusesAreConnected());
	}
	
	@Test
	public void setBusesWithEmptyInputsSetsFlagToFalse(){
		component.setBuses(new ArrayList<Bus>(), new Bus());
		assertFalse(component.allBusesAreConnected());
	}
	
	@Test
	public void setBusesWithNullOutputSetsFlagToFalse(){
		List<Bus> buses = Arrays.asList(new Bus(), new Bus());
		component.setBuses(buses, null);
		
		assertFalse(component.allBusesAreConnected());
	}
	
	@Test
	public void setBusesWithFewerThanRequiredInputsSetsFlagToFalse(){
		List<Bus> inputBuses = new ArrayList<Bus>();
		int fewerInputs = component.getRequiredNumberOfInputs() - 1;
		for (int i = 0; i < fewerInputs; i++)
			inputBuses.add(new Bus());
		
		component.setBuses(inputBuses, new Bus());
		assertFalse(component.allBusesAreConnected());
	}
	
	@Test
	public void setBusesWithAdequateNumberOfBusesReturnsTrue(){
		component.setBuses(Arrays.asList(new Bus(), new Bus()), new Bus());
		
		assertTrue(component.allBusesAreConnected());
	}
	
	@Test
	public void processAndSetWhenComponentInputsNotConnectedReturnsFalse(){
		assertFalse(component.processAndSet());
	}
	
	@Test
	public void getInputsWhenInputsAllFalseReturnsFalseBooleanList(){
		List<Bus> inputBuses = new ArrayList<Bus>();
		List<Boolean> booleans = new ArrayList<Boolean>();
		for (int i = 0; i < component.getRequiredNumberOfInputs(); i++){
			Bus bus = new Bus();
			bus.setValue(false);
			inputBuses.add(bus);
			booleans.add(new Boolean(false));
		}
		component.setBuses(inputBuses, new Bus());
		
		assertArrayEquals(booleans.toArray(), component.getInputValues().toArray());
	}
	



	@Test
	public void processAndSetWithUnconnectedBusesReturnsFalse(){
		assertFalse(component.processAndSet());
	}

	@Test
	public void processAndSetWithConnectedBusesReturnsTrue(){
		component.setBuses(Arrays.asList(new Bus(), new Bus()), new Bus());
		
		assertTrue(component.processAndSet());
	}

	
	@Test
	public void getInputBusesReturnsBuses(){
		Bus bus1 = new Bus();
		Bus bus2 = new Bus();
		component.setBuses(Arrays.asList(bus1, bus2), new Bus());
		
		assertEquals(Arrays.asList(bus1,bus2), component.getInputBuses());
	}
	
	@Test
	public void getOutputBusReturnsOutputBus(){
		Bus outputBus = new Bus();
		component.setBuses(Arrays.asList(new Bus(), new Bus()), outputBus);
		
		assertEquals(outputBus, component.getOutputBus());
	}
}

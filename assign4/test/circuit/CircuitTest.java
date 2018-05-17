package circuit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CircuitTest {
	
	Circuit circuit;

	@Before
	public void setUp(){
		circuit = new Circuit(10);
	}
	
	@Test
	public void circuitConstructionCreatesBusListOfTheSizeThatWasInput(){
		assertEquals(10, circuit.getNumberOfBuses());
	}
	
	@Test
	public void getCircuitComponentsAfterConstructionsReturnsEmptyList(){
		assertTrue(circuit.getComponents().isEmpty());
	}
	
	@Test
	public void connectSingleInputComponentSetsComponentBusesAndAddsComponentToComponentList(){
		int[] inputs = {1};
		NOTGate notGate = new NOTGate();
		circuit.connectComponent(notGate, inputs, 2);
		
		assertTrue(notGate.allBusesAreConnected());
		assertEquals(1, circuit.getComponents().size());
	}
	
	@Test
	public void connectDualInputsComponentSetsComponentBusesAndAddsComponentToComponentList(){
		int[] inputs = {1,2};
		ANDGate andGate = new ANDGate();
		circuit.connectComponent(andGate, inputs, 3);
		
		assertTrue(andGate.allBusesAreConnected());
		assertEquals(1, circuit.getComponents().size());
	}

	@Test
	public void toggleFalseBusValueTogglesBusValueToTrue(){
		circuit.toggleBusValue(0);
		
		assertEquals(true, circuit.getBusValue(0));
	}
	
	@Test
	public void toggleTrueBusValueTogglesBusValueToFalse(){
		circuit.setBusValue(0,true);
		circuit.toggleBusValue(0);
		
		assertEquals(false, circuit.getBusValue(0));
	}
	
	@Test
	public void busValueSettableForBusWithOutputLeadReturnsFalseForOutputBus(){
		int[] inputBuses = {0};
		int outputBus = 1;
		circuit.connectComponent(new NOTGate(), inputBuses, outputBus);
		
		assertTrue(circuit.busValueSettable(0));
		assertFalse(circuit.busValueSettable(1));
	}
	
	@Test
	public void busHasInputLeadsWhenItDoesNotReturnsFalse(){
		int[] inputBusNumber = {0};
		circuit.connectComponent(new NOTGate(), inputBusNumber, 1);
		assertFalse(circuit.busHasInputLeads(5));
	}
	
	@Test
	public void busHasInputLeadsWhenItDoesReturnsTrue(){
		int[] inputBusNumber = {0};
		circuit.connectComponent(new NOTGate(), inputBusNumber, 1);
		assertTrue(circuit.busHasInputLeads(0));
	}
	
	@Test
	public void evaluateEvaluatesAllComponents(){
		int[] input1 = {0};
		circuit.connectComponent(new NOTGate(), input1, 1);
		circuit.evaluate();
		
		assertTrue(circuit.getBusValue(1));
	}
	
	@Test
	public void busHasOutputLeadsForBusWithOutputLeadReturnsTrue(){
		int[] inputBuses = {0};
		int outputBus = 1;
		circuit.connectComponent(new NOTGate(), inputBuses, outputBus);
		
		assertTrue(circuit.busHasOutputLeads(1));
		assertFalse(circuit.busHasOutputLeads(0));
	}
	
	@Test
	public void getUnconnectedOutputsWithOneConnectedAndOneUnconnectedReturnsCorrectList(){
		int[] inputBuses = {0};
		int outputBus = 1;
		int[] inputBuses2 = {1};
		int outputBus2 = 2;
		circuit.connectComponent(new NOTGate(), inputBuses, outputBus);
		circuit.connectComponent(new NOTGate(), inputBuses2, outputBus2);
		
		List<Integer> unconnected = circuit.getUnconnectedOutputs();
		assertTrue(unconnected.size() == 1);
		assertEquals(2, unconnected.get(0).intValue());
	}
	
	@Test
	public void getUnconnectedOutputs(){
		int[] inputBuses = {0};
		int outputBus = 1;
		circuit.connectComponent(new NOTGate(), inputBuses, outputBus);
		
		assertEquals(1, circuit.getUnconnectedOutputs().get(0).intValue());
	}
	
}

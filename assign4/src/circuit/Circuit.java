package circuit;

import java.util.List;
import java.util.ArrayList;

public class Circuit {
	private List<Bus> buses;
	private List<Component> circuitComponents;

	public Circuit(int numberOfBuses){
		this.buses = new ArrayList<Bus>();
		for (int i = 0; i < numberOfBuses; i++){
			buses.add(new Bus());
		}
		circuitComponents = new ArrayList<Component>();
	}

	public void connectComponent(Component component,  int[] inputBusNumbers, int outputBusNumber){
		List<Bus> inputBuses = new ArrayList<Bus>();
		for (int i = 0; i < inputBusNumbers.length; i++){
			inputBuses.add(buses.get(inputBusNumbers[i]));
		}
		Bus outputBus = buses.get(outputBusNumber);
		component.setBuses(inputBuses, outputBus);
		circuitComponents.add(component);
	}

	public int getNumberOfBuses(){
		return buses.size();
	}

	public boolean getBusValue(int busNumber){
		return buses.get(busNumber).getValue();
	}

	public void setBusValue(int busNumber, boolean value){
		buses.get(busNumber).setValue(value);
	}

	public List<Component> getComponents(){
		return circuitComponents;
	}


	public boolean busValueSettable(int busNumber){
		Bus bus = buses.get(busNumber);
		for (Component component : circuitComponents){
			if (component.getOutputBus() == bus)
				return false;
		}
		return true;
	}

	public boolean busHasInputLeads(int busNumber){
		Bus bus = buses.get(busNumber);
		for (Component component : circuitComponents)
			if (component.getInputBuses().contains(bus))
				return true;
		return false;
	}

	public boolean busHasOutputLeads(int busNumber){
		return !busValueSettable(busNumber);
	}

	public void evaluate(){
		circuitComponents.forEach(Component::processAndSet);
	}

	public void toggleBusValue(int busNumber){
		Bus bus = buses.get(busNumber);
		bus.setValue(!bus.getValue());
	}

	public List<Integer> getUnconnectedOutputs(){
		List<Integer> unconnectedComponentNumbers = new ArrayList<Integer>();
		for (int i = 0; i < buses.size(); i++){
			if (busHasOutputLeads(i) && !busHasInputLeads(i)){
				unconnectedComponentNumbers.add(new Integer(i));
			}
		}
		return unconnectedComponentNumbers;
	}


}

package circuit;

import java.util.List;
import java.lang.Boolean;
import java.util.stream.Collectors;

public abstract class Component {

	protected List<Bus> inputBuses; 
	protected Bus outputBus; 
	protected boolean busesConnected;


	public void setBuses(List<Bus> inputBuses, Bus outputBus){
		if ( (inputBuses != null) && (!inputBuses.isEmpty()) && (outputBus != null) 
											&& (inputBuses.size() >= getRequiredNumberOfInputs()) ){

			this.inputBuses = inputBuses;
			this.outputBus = outputBus;
			busesConnected = true;
		}
	}
	
	List<Boolean> getInputValues(){
		return inputBuses.stream()
		 .map(Bus::getValue)
		 .collect(Collectors.toList());
	}
	
	public List<Bus> getInputBuses(){
		return inputBuses;
	}
	
	public boolean processAndSet(){ 
		if (!allBusesAreConnected())
			return false;
		else
			outputBus.setValue(processInputs(getInputValues()));
		return true;
	}

	protected abstract boolean processInputs(List<Boolean> inputs); 

	public abstract int getRequiredNumberOfInputs();

	public Bus getOutputBus(){
		return outputBus;
	}
	
	public boolean allBusesAreConnected(){
		return busesConnected;
	}

}

package circuit;

import java.util.List;
import java.util.function.Predicate;


public class ANDGate extends Component{


	public boolean processInputs(List<Boolean> inputs){		
	
		return inputs.stream()
		  .allMatch(Predicate.isEqual(true));  
	}

	public int getRequiredNumberOfInputs()
	{
		return 2;
	}
}

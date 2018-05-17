package circuit;

import java.util.List;
import java.util.function.Predicate;
import java.lang.Boolean;

public class ORGate extends Component{


	public boolean processInputs(List<Boolean> inputs){

	  return inputs.stream()
	  		.anyMatch(Predicate.isEqual(true));        
	}
	

	public int getRequiredNumberOfInputs()
	{
		return 2;
	}

}

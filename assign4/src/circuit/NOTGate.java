package circuit;

import java.util.List;
import java.lang.Boolean;

public class NOTGate extends Component{


	public boolean processInputs(List<Boolean> inputs){
		return (!inputs.get(0).booleanValue());
	}

	public int getRequiredNumberOfInputs()
	{
		return 1;
	}
}

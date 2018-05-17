package circuit;

import circuit.util.ComponentsExplorer;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;

public class ComponentsExplorerTest {
	
	ComponentsExplorer explorer;

	@Before
	public void setUp(){
		explorer = new ComponentsExplorer();
	}
	
	@Test
	public void constructorCorrectlyGetsComponentNames(){
		
		assertEquals(Arrays.asList("ANDGate", "NOTGate", "ORGate"), explorer.getShortComponentNames());
	}
	
	
	

}
package circuit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BusTest {
	Bus bus;

	@Before
	public void setUp(){
		bus = new Bus();
	}

	@Test
	public void setValueTrueSetsValueToTrue(){
		bus.setValue(true);
		assertTrue(bus.getValue());
	}
	
	@Test
	public void setValueFalseSetsValueToFalse(){
		bus.setValue(false);
		assertFalse(bus.getValue());
	}
	

}

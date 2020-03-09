package elevator;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PeopleTest {

    public Integer startFloor = 10;
    public Integer toFloor = 1;
    public Integer startFloor2 = 5;
    public Integer toFloor2 = 12;
    public Long startTime = (long) 60.00;
    public Long waitTime = (long) 10.00;
    
  
	
	@Test
	public void testConstructor(){
		People test_obj1 = new People(startFloor, toFloor, startTime);
		People test_obj2 = new People(startFloor2, toFloor2, startTime);
		assertEquals("DOWN", test_obj1.dir);
		assertEquals("UP", test_obj2.dir);
	}
	
	@Test
	public void testexitElavator() {
		People test_obj1 = new People(startFloor, toFloor, startTime);		
		test_obj1.waitTime = (long) 70.00;		
		test_obj1.exitElevator((long) 150.00);	
		
		assertEquals((long) 20.00,(long)test_obj1.rideTime);	
	}
	
	@Test
	public void testenterElavator() {
		People test_obj1 = new People(startFloor, toFloor, startTime);
		test_obj1.enterElevator((long)100.00);
		assertEquals((long)40.00, (long)test_obj1.waitTime);
	}
	
	//cannot unit test private method directly.
	//test the method that calls the private method to test it works as expected.
	
	//using @Rule annotation with an ExpectedException class in JUNIT4 to test exceptions 
	@Rule
    public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testsetStartFloor() {
		exception.expect(InvalidParameterException.class);
		exception.expectMessage("Invalid floor number");
		
		People test_obj = new People(-1,toFloor, startTime);
	}
	

	@Test
	public void testsetToFloor() {
			exception.expect(InvalidParameterException.class);
			exception.expectMessage("Invalid floor number");
			
			People test_obj = new People(startFloor, 0, startTime);
		
	}
}



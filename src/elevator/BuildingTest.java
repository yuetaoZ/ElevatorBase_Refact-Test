package elevator;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.List;

import org.junit.Test;

public class BuildingTest {
	Integer numFloor = 2;
    Integer numElevator = 1;
    Integer elevatorCap = 1;
    Long flrSec = 1000L;
    Long dorSec = 1000L;
    Long idleSec = 10000L;

	@Test
	public void testBuildingConstructor() {
		
		Building test = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
		
		assertNotNull(test);
		
	}
	
	@Test(expected = InvalidParameterException.class)
	public void testInvalidNumOfElevator() {
		
		numElevator = -1;
		
		Building test = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
		
		assertNull(test);
		
	}
	
	@Test(expected = InvalidParameterException.class)
	public void testInvalidElevatorCapacity() {
		
		elevatorCap = -1;
		
		Building test = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
		
		assertNull(test);
		
	}
	
	@Test(expected = InvalidParameterException.class)
	public void testInvalidFloorSec() {
		
		flrSec = (long) -1;
		
		Building test = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
		
		assertNull(test);
		
	}
	
	@Test
	public void testGetElevators() {
		
		List<Elevator> elevatorList = null;
		
		Building test = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
		
		elevatorList = test.getElevators();
		
		assertNotNull(elevatorList);
	}
	
	@Test(expected = InvalidParameterException.class)
	public void testSetValidFloorNumber() {
		
		int numFloor = -1;
		
		Building test = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
		
		assertNull(test);
		
	}

}

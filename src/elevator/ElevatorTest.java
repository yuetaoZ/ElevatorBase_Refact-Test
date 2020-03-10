package elevator;

import static org.junit.Assert.*;
import org.junit.Test;

public class ElevatorTest {
	
	Integer numFloor = 20;
    Integer numElevator = 1;
    Integer elevatorCap = 15;
    Long flrSec = 1000L;
    Long dorSec = 1000L;
    Long idleSec = 10000L;

	@Test
	public void testCurrDirection() throws InterruptedException {
		
		// test this.allStops.isEmpty() && this.toFloor.equals(1)
		testCurrDirection_case1_TrueTrue();
		testCurrDirection_case2_TrueFalse();
		testCurrDirection_case3_FalseTrue();
		testCurrDirection_case4_FalseFalse();
	}
	
	private void testCurrDirection_case1_TrueTrue() throws InterruptedException {
		Building testBuilding = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
		Elevator testElevator = (Elevator) testBuilding.getElevators().get(0);
		testElevator.entryNewPeople();
		
		assertTrue("Allstops should be empty.", testElevator.getAllStops().isEmpty());
		assertTrue("ToFloor should be equal to 1.", testElevator.getToFloor().equals(1));
	}
	
	private void testCurrDirection_case2_TrueFalse() throws InterruptedException {
		Building testBuilding = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
		Elevator testElevator = (Elevator) testBuilding.getElevators().get(0);
		People testPeople = new People(5, 15, 0L);
		testElevator.addtoWaitList(testPeople);
		testElevator.entryNewPeople();

		assertTrue("Allstops should be empty.", testElevator.getAllStops().isEmpty());
		assertFalse("ToFloor should not equal to 1.", testElevator.getToFloor().equals(1));
	}
	
	private void testCurrDirection_case3_FalseTrue() throws InterruptedException {
		Building testBuilding = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
		Elevator testElevator = (Elevator) testBuilding.getElevators().get(0);
		People testPeople1 = new People(1, 5, 0L);
		People testPeople2 = new People(11, 2, 0L);
		testElevator.addtoWaitList(testPeople1);
		testElevator.addtoWaitList(testPeople2);
		testElevator.entryNewPeople();

		assertFalse("Allstops shouldn't be empty.", testElevator.getAllStops().isEmpty());
		assertTrue("ToFloor should equal to 1.", testElevator.getToFloor().equals(1));
	}
	
	private void testCurrDirection_case4_FalseFalse() throws InterruptedException {
		Building testBuilding = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
		Elevator testElevator = (Elevator) testBuilding.getElevators().get(0);
		People testPeople = new People(1, 6, 0L);
		
		testElevator.addtoWaitList(testPeople);
		testElevator.entryNewPeople();

		assertFalse("Allstops shouldn't be empty.", testElevator.getAllStops().isEmpty());
		assertFalse("ToFloor should not equal to 1.", testElevator.getToFloor().equals(1));
	}
	
}

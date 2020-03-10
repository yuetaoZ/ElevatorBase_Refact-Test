package elevator;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;
import static elevator.Main.sTime;

public class ElevatorTest {
	Integer capacity = 20;
	Integer totalFloor = 42;
	
	Integer numFloor = 20;
	Integer numElevator = 1;
	Integer elevatorCap = 15;
	long floorSec = 1000;
	long doorSec = 5000;
	long idleSec = 29000;
	
	
//	People p1 = new People(1, 10, 0L);
//    People p2 = new People(1, 20, 0L);
	
//	Building test_building = new Building(numFloor,numElevator,elevatorCap,floorSec,doorSec,idleSec);
//	Elevator test_obj = test_building.getElevators().get(0);
		

	
	@Test
	public void exit_waitlist_control_case1() {
		Building test_building = new Building(numFloor,numElevator,elevatorCap,floorSec,doorSec,idleSec);
		Elevator test_obj = test_building.getElevators().get(0);
		People p1 = new People(1, 20, 0L);
		  
		test_obj.addtoWaitList(p1);
		
		assertEquals((Integer)1,test_obj.getCurrFloor());

		test_obj.getAllStops().remove(p1.toFloor);
		test_obj.exit_waitlist_control((long)1000);
		
		
	}
	
	//this test case find dead code from Elevator class line 221-224
	@Test
	public void exit_waitlist_control_case2() throws InterruptedException {
		Building test_building = new Building(numFloor,numElevator,elevatorCap,floorSec,doorSec,idleSec);
		Elevator test_obj = test_building.getElevators().get(0);
		People p1 = new People(1, 11, 1000L);
//		People p2 = new People(1, 20, 1000L);
		  
		test_obj.addtoWaitList(p1);
//		test_obj.addtoWaitList(p2);
		assertFalse(test_obj.getAllStops().containsKey(p1.toFloor));
		test_obj.entryNewPeople();
		assertTrue(test_obj.getAllStops().containsKey(p1.toFloor));
		test_obj.getAllStops().remove(p1.toFloor);		
		assertFalse(test_obj.getAllStops().containsKey(p1.toFloor));
		test_obj.exit_waitlist_control((long)2000);
	}
	
	@Rule
    public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void exit_waitlist_control_case3() {
		People p1 = new People(1, 10, 0L);
		
		exception.expect(InvalidParameterException.class);
		exception.expectMessage("Elevator full, not allow to enter.");
		Elevator test2 = new Elevator(1, 40, 1000, 5000, 29000);
		test2.addtoWaitList(p1);
		test2.exit_waitlist_control((long)1000);
	
	}
		
	
	@Test
	public void testgetFloorSec() {
		Elevator test2 = new Elevator(1, 40, 1000, 5000, 29000);
		assertEquals(1000, test2.getFloorSec());
	}
	
//	@Rule
//  public ExpectedException exception2 = ExpectedException.none();
//	
//	@Test
//	public void testrun() {
//		Elevator test2 = new Elevator(1, 40, 1000, 5000, 29000);
//		
//		test2.run();
//		Thread.currentThread().interrupt();
//		exception2.expect(InterruptedException.class);
//		
//	}
	

	
	@Test
	public void testexitPeople_case2() throws InterruptedException {
		Building test_building = new Building(numFloor,numElevator,elevatorCap,floorSec,doorSec,idleSec);
		Elevator test_obj = test_building.getElevators().get(0);
		People p1 = new People(1, 10, 0L);
		People p2 = new People(1, 1, 0L);
		
		test_obj.addtoWaitList(p1);
		test_obj.addtoWaitList(p2);
		assertNotNull(test_obj.getAllStops());
		
		test_obj.entryNewPeople();		
		assertTrue(test_obj.getAllStops().containsKey(10));
//		assertTrue(test_obj.getAllStops().containsKey(1));
		
		assertEquals((Integer)1,test_obj.getCurrFloor());
		assertNotNull(test_obj.exitPeople());	
		
		assertFalse(test_obj.getAllStops().isEmpty());
		assertFalse(test_obj.getCurrStatus().equals("IDLE"));
		assertTrue(test_obj.getToFloor().equals(1));
		test_obj.getAllStops().clear();
//		test_obj.exitPeople();
	}
	
	@Rule
    public ExpectedException exception3 = ExpectedException.none();
	
	@Test
	public void testexitPeople_case1() throws InterruptedException {
		Building test_building = new Building(numFloor,numElevator,elevatorCap,floorSec,doorSec,idleSec);
		Elevator test_obj = test_building.getElevators().get(0);
		People p1 = new People(1, 10, 0L);
		
		exception.expect(InvalidParameterException.class);
		exception.expectMessage("No people exit!");
		test_obj.addtoWaitList(p1);
		assertNotNull(test_obj.getAllStops());
		test_obj.exitPeople();
	}
	

}

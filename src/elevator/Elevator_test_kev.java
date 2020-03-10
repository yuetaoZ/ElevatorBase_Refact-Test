package elevator;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

import org.junit.*;
import static elevator.Main.sTime;

public class Elevator_test_kev {
	List<Elevator> elevators_t;

	@Before
	public void before_f() {

	}

	@Test
	public void test_entrynewpeople() throws InterruptedException {
		long num = 1000;
		Building test_b = new Building(20, 4, 10, num, num, num);
		Building test_b_full = new Building(20, 4, 1, num, num, num);
		List<Elevator> elevators_t = test_b.getElevators();
		List<Elevator> elevators_t_full = test_b_full.getElevators();
		Elevator e1 = elevators_t.get(0);
		Elevator e2 = elevators_t_full.get(0);

		People p1 = new People(1, 10, 0L);
		People p2 = new People(1, 15, 0L);


		sTime = new Date();
		e1.addtoWaitList(p1);
		assertTrue(e1.getwaitList_size() == 1);
		e1.entryNewPeople();
		assertTrue(e1.getwaitList_size() == 0);
//

		try { // test for exception
			e2.addtoWaitList(p1);
			e2.addtoWaitList(p2);
			e2.entryNewPeople();
			fail();
		} catch (InvalidParameterException a) {
		}
	}

	@Test
	public void test_entrynewpeople2() throws InterruptedException {
		long num = 1000;
		People p3 = new People(2, 3, 0L);
		People p4 = new People(3 ,4, 0L);
		Building test_b = new Building(20, 4, 10, num, num, num);
		elevators_t = test_b.getElevators();
		Elevator e3 = elevators_t.get(0);

		sTime = new Date();
		e3.addtoWaitList(p3);
		e3.addtoWaitList(p4);
		assertTrue(e3.getwaitList_size() == 2);
		e3.entryNewPeople();
		assertTrue(e3.getwaitList_size() == 2); //change elevator starting floor, not entry yet
		
		
	}
	
	

}

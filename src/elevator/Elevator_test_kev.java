package elevator;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.*;
import static elevator.Main.sTime;

public class Elevator_test_kev {
	List<Elevator> elevators_t;

//	@Before
//	public void before_f() {
//
//	}

	@Test
	public void test_entrynewpeople() throws InterruptedException {
		long num = 1000;
		Building test_b = new Building(20, 4, 10, num, num, num);
		List<Elevator> elevators_t = test_b.getElevators();
		Elevator e1 = elevators_t.get(0);
		People p1 = new People(1, 10, 0L);
		People p2 = new People(1, 15, 0L);
		People p3 = new People(15, 19, 0L);
		
		sTime = new Date();
		e1.addtoWaitList(p2);
		assertTrue(e1.getwaitList_size() == 1);
		e1.entryNewPeople();
		assertTrue(e1.getwaitList_size() == 0);
		
		
//		assertTrue(e1.getwaitList_size() == 0);
		
		

	}
}

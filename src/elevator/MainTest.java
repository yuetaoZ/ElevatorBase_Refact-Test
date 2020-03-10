package elevator;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {

	
	@Test
	public void testconvertSecondsToHMmSs() {
		assertNotNull(Main.convertSecondsToHMmSs(180));
		assertEquals("00:04:00",Main.convertSecondsToHMmSs(240000));
	}
	
//	@Test
//	public void testgetBuilding() throws InterruptedException {
//		Main.main(null);
//	//	assertNotNull(Main.sTime);
//	}

}

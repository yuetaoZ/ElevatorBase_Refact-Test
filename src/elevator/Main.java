// This time will try to merge without pull.

package elevator;

import gui.ElevatorDisplay;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static gui.ElevatorDisplay.Direction.*;


public class Main {

    public static Date sTime;
    public static DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

    public static String convertSecondsToHMmSs(long seconds) {
        seconds /= 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", h,m,s);
    }

    public static void main(String[] args) throws InterruptedException {

        Integer numFloor = 1;
        Integer numElevator = 1;
        Integer elevatorCap = 1;
        Long flrSec = 1000L;
        Long dorSec = 1000L;
        Long idleSec = 10000L;

        JSONParser parser = new JSONParser();
        try {

            Object object= parser.parse(new FileReader("src/input/base.json"));
            JSONObject jsonObject = (JSONObject) object;
            //System.out.println(jsonObject);
            numFloor = ((Number) jsonObject.get("floor")).intValue();
            numElevator = ((Number) jsonObject.get("elevators")).intValue();
            elevatorCap = ((Number) jsonObject.get("elevatorCap")).intValue();
            flrSec = ((Number) jsonObject.get("floorTime")).longValue();
            dorSec = ((Number) jsonObject.get("doorTime")).longValue();
            idleSec = ((Number) jsonObject.get("idleTime")).longValue();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Building building = new Building(numFloor, numElevator, elevatorCap, flrSec, dorSec, idleSec);
        List<Elevator> e = building.allElevator;

        Elevator e1 = e.get(0);
        Elevator e2 = e.get(1);
        Elevator e3 = e.get(2);
        Elevator e4 = e.get(3);

        People p1 = new People(1, 10, 0L);
        People p2 = new People(1, 20, 0L);
        People p3 = new People(15, 19, 0L);
        People p4 = new People(20, 5, 0L);
        People p5 = new People(20, 1, 0L);
        People p6 = new People(10, 1, 0L);
        People p7 = new People(1, 10, 0L);
        People p8 = new People(8, 17, 0L);
        People p9 = new People(1, 9, 0L);
        People p10 = new People(3, 1, 0L);

        //TEST 1
        System.out.println("Start TEST 1.....");
        sTime = new Date();
        e1.addtoWaitList(p1);
        Thread.sleep(flrSec * 50);
        System.out.println("End TEST 1......");

        // TEST 2
        System.out.println("Start TEST 2.....");
        sTime = new Date();
        e2.addtoWaitList(p2);
        e2.addtoWaitList(p3);
        e2.addtoWaitList(p4);
        Thread.sleep(flrSec * 70);
        System.out.println("End TEST 2......");

        // TEST 3
        System.out.println("Start TEST 3.....");
        sTime = new Date();
        e3.addtoWaitList(p5);
        e3.addtoWaitList(p6);
        Thread.sleep(flrSec * 70);
        System.out.println("End TEST 3......");
        // TEST 4

        System.out.println("Start TEST 4.....");
        sTime = new Date();
        e1.addtoWaitList(p7);
        e1.addtoWaitList(p8);
        Thread.sleep(flrSec * 6);
        e4.addtoWaitList(p9);
        e4.addtoWaitList(p10);
        Thread.sleep(flrSec * 70);
        System.out.println("End TEST 4......");

    }
}

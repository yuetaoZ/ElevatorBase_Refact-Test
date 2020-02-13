package elevator;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

import static elevator.Main.sTime;
import static elevator.Main.dateFormat;
import static elevator.Main.convertSecondsToHMmSs;
import static gui.ElevatorDisplay.*;

public class Elevator implements Runnable{
    private static Integer NUM = 1;

    public String currStatus;
    public Integer elevatorNUM;
    public Integer currFloor;
    public Integer toFloor;
    private Integer capacity;
    private Integer currCapacity;
    private Integer totalFloor;
    private long floorSec;
    private long doorSec;
    private long idleSec;
    private List<People> waitList;
    private HashMap<Integer, List<People>> allStops;

    public Elevator(Integer cap, Integer totalFloor, long flrSec, long dorSec, long idleSec) throws InvalidParameterException
    {

        setElevatorNum();
        this.currStatus = "IDLE";
        this.currFloor = 1;
        this.capacity = cap;
        this.totalFloor = totalFloor;
        this.toFloor = 1;
        this.floorSec = flrSec;
        this.doorSec = dorSec;
        this.idleSec = idleSec;
        this.currCapacity = 0;
        this.waitList = new ArrayList<>();
        this.allStops = new HashMap<>();
        getInstance().addElevator(this.elevatorNUM, this.currFloor);
    }

    private void move() throws InterruptedException{

        entryNewPeople();
        currDir();
        // check if ppls need to exits
        if (this.allStops.containsKey(this.currFloor)){
            exitPeople();
        }

        // exit move if there is not tofloor args
        if (this.allStops.isEmpty() && this.toFloor.equals(1) && this.currFloor.equals(1)){
            return;
        }
        //
        move_aux();
        
        // **start
//        if (this.currStatus.equals("UP")){
//            Integer preFloor = this.currFloor;
//            this.currFloor++;
//            getInstance().updateElevator(this.elevatorNUM,this.currFloor,this.currCapacity, Direction.UP);
//            long t = new Date().getTime()-sTime.getTime();
//            System.out.println(convertSecondsToHMmSs(t) + ": Elevator " + this.elevatorNUM + " move up from floor " + preFloor + " to floor " + this.currFloor);
//        }else if (this.currStatus.equals("DOWN")){
//            Integer preFloor = this.currFloor;
//            this.currFloor--;
//            getInstance().updateElevator(this.elevatorNUM,this.currFloor,this.currCapacity, Direction.DOWN);
//            long t = new Date().getTime()-sTime.getTime();
//            System.out.println(convertSecondsToHMmSs(t) + ": Elevator " + this.elevatorNUM + " move down from floor " + preFloor + " to floor " + this.currFloor);
//        }else{
//            Integer preFloor = this.currFloor;
//            this.currFloor--;
//            getInstance().updateElevator(this.elevatorNUM,this.currFloor,this.currCapacity, Direction.IDLE);
//            long t = new Date().getTime()-sTime.getTime();
//            System.out.println(convertSecondsToHMmSs(t) + ": Elevator " + this.elevatorNUM + " move down from floor " + preFloor + " to floor " + this.currFloor);
//        }
        //**end
        Thread.sleep(this.floorSec);
    }
    
    // duplicated code fix
    
   private void move_aux() {
	   long t = new Date().getTime()-sTime.getTime();
	   String massage = convertSecondsToHMmSs(t)+": Elevator " + this.elevatorNUM;
	   Direction cur; 
	   if(this.currStatus.equals("UP")) {
		   this.currFloor++;
		   massage += " move up from floor ";
		   cur = Direction.UP;
	   }else {
		   this.currFloor--;
		   massage += " move down from floor ";
		   if(this.currStatus.equals("DOWN")) {
			   cur = Direction.DOWN;
		   }else {
			   cur = Direction.IDLE;
		   }
	   }
	   getInstance().updateElevator(this.elevatorNUM,this.currFloor,this.currCapacity, cur);
	   massage += (Integer)this.currFloor + " to floor " +this.currFloor;
	   System.out.println(massage);
   }

  // end here
    
    
    
    private void setElevatorNum(){
        this.elevatorNUM = Elevator.NUM;
        Elevator.NUM++;
    }

    private void currDir(){

        if (this.allStops.isEmpty() && this.toFloor.equals(1)){
            this.currStatus = "IDLE";
            getInstance().updateElevator(this.elevatorNUM, this.currFloor, this.currCapacity, Direction.IDLE);
        }else{
            boolean up = false;
            boolean down = false;

            if (!this.toFloor.equals(1)) {
                if (this.toFloor > this.currFloor){
                    up = true;
                }else if (this.allStops.isEmpty()){
                    down = true;
                }else{
                    this.toFloor = 1;
                }
            }
            else{
                for (Integer key : this.allStops.keySet()) {
                    if (this.currFloor < key) {
                        up = true;
                    } else if (this.currFloor > key) {
                        down = true;
                    }
                }
            }
            if (this.currFloor.equals(this.totalFloor)){
                this.currStatus = "DOWN";
            }else if (this.currFloor.equals(1)){
                this.currStatus = "UP";
            }else if ((this.currStatus.equals("UP") && up) || (this.currStatus.equals("DOWN") && down) || this.currStatus.equals("IDLE")){
                // do nothing
            }else{
                if (this.currStatus.equals("UP")){
                    this.currStatus = "DOWN";
                }else{
                    this.currStatus = "UP";
                }
            }
        }
    }

    private List<People> exitPeople() throws InterruptedException{
        long t;
        if (! this.allStops.containsKey(this.currFloor)){
            throw new InvalidParameterException("No people exit!");
        }
        List<People> allPeople = this.allStops.get(this.currFloor);
        getInstance().openDoors(this.elevatorNUM);
        t = new Date().getTime()-sTime.getTime();
        System.out.println(convertSecondsToHMmSs(t) + ": Elevator " + this.elevatorNUM + " open door at floor " + this.currFloor);
        Thread.sleep(this.doorSec);
        for (People p: allPeople){
            t = new Date().getTime()-sTime.getTime();
            System.out.println(convertSecondsToHMmSs(t) + ": People " + p.peopleNUM + " exit elevator floor " + this.elevatorNUM);
        }
        List<People> allPeople1 = new ArrayList<>();
        for (People p1: this.waitList){
            if (p1.startFloor.equals(this.currFloor)){
                System.out.println(convertSecondsToHMmSs(t) + ": People " + p1.peopleNUM + " enter elevator floor " + this.elevatorNUM);
                allPeople1.add(p1);
                this.currCapacity++;
                if (this.capacity > this.currCapacity) {
                    if (this.allStops.containsKey(p1.toFloor)) {
                        this.allStops.get(p1.toFloor).add(p1);
                    } else {
                        List<People> newPeople = new ArrayList<>();
                        newPeople.add(p1);
                        this.allStops.put(p1.toFloor, newPeople);
                        currDir();
                    }
                } else {
                    throw new InvalidParameterException("Elevator full, not allow to enter.");
                }
            }
        }
        for (People p1: allPeople1){
            this.waitList.remove(p1);
        }
        getInstance().closeDoors(this.elevatorNUM);
        t = new Date().getTime()-sTime.getTime();
        System.out.println(convertSecondsToHMmSs(t) + ": Elevator " + this.elevatorNUM + " close door at floor " + this.currFloor);
        Thread.sleep(this.doorSec);
        this.allStops.remove(this.currFloor);
        this.currCapacity -= allPeople.size();
        if (this.allStops.isEmpty() && !this.currStatus.equals("IDLE") && this.toFloor.equals(1)) {
            this.currStatus = "IDLE";
            getInstance().updateElevator(this.elevatorNUM, this.currFloor, this.currCapacity, Direction.IDLE);
            t = new Date().getTime()-sTime.getTime();
            Thread.sleep(this.idleSec);
            System.out.println(convertSecondsToHMmSs(t) + ": Elevator " + this.elevatorNUM + " to IDLE.");
        }
        return allPeople;
    }

    public void addtoWaitList(People p){
        this.waitList.add(p);
    }

    private void entryNewPeople() throws InterruptedException {
        boolean openDoor = false;
        List<People> allPeople = new ArrayList<>();
        if (this.waitList.isEmpty()){
            return;
        }

        for (People p: this.waitList) {
            if (p.startFloor.equals(this.currFloor) && (this.currStatus.equals(p.dir)
                    || this.currStatus.equals("IDLE") || this.toFloor.equals(this.currFloor))) {
                openDoor = true;
                if (this.capacity > this.currCapacity) {
                    if (this.allStops.containsKey(p.toFloor)) {
                        this.allStops.get(p.toFloor).add(p);
                    } else {
                        List<People> newPeople = new ArrayList<>();
                        newPeople.add(p);
                        this.allStops.put(p.toFloor, newPeople);
                        currDir();
                    }
                } else {
                    throw new InvalidParameterException("Elevator full, not allow to enter.");
                }
                // pick up people
                if (this.toFloor.equals(this.currFloor) && !this.toFloor.equals(1)){
                    this.toFloor = 1;
                }
                this.currCapacity++;
                allPeople.add(p);
            }else if (this.currStatus.equals("IDLE")){
                this.toFloor = p.startFloor;
                break;
            }
        }
        if (openDoor){
            getInstance().openDoors(this.elevatorNUM);
            long t = new Date().getTime()-sTime.getTime();
            System.out.println(convertSecondsToHMmSs(t) + ": Elevator " + this.elevatorNUM + " open door at floor " + this.currFloor);
            Thread.sleep(this.doorSec);
            for (People i : allPeople){
                t = new Date().getTime()-sTime.getTime();
                System.out.println(convertSecondsToHMmSs(t) + ": People " + i.peopleNUM + " enter " + this.elevatorNUM + " elevator.");
                System.out.println(convertSecondsToHMmSs(t) + ": People " + i.peopleNUM + " press floor " + i.toFloor + " at elevator." + this.elevatorNUM);
                this.waitList.remove(i);
            }
            getInstance().closeDoors(this.elevatorNUM);
            t = new Date().getTime()-sTime.getTime();
            System.out.println(convertSecondsToHMmSs(t) + ": Elevator " + this.elevatorNUM + " close door at floor " + this.currFloor);
            Thread.sleep(this.doorSec);
        }
    }

    @Override
    public void run(){
        while(true){
            try{
                move();//
                Thread.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

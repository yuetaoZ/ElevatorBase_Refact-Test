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

    private String currStatus;
    private Integer elevatorNUM;
    private Integer currFloor;
    private Integer toFloor;
    private Integer capacity;
    private Integer currCapacity;
    private Integer totalFloor;
    private boolean up = false;
    private boolean down = false;
    private long floorSec;
    private long doorSec;
    private long idleSec;
    private List<People> waitList;
    private HashMap<Integer, List<People>> allStops;
    
    public HashMap<Integer, List<People>> getAllStops() {
    	return allStops;
    }
    
    public Integer getCurrFloor() {
    	return currFloor;
    }
    
    public Integer getToFloor() {
    	return toFloor;
    }
    
    public String getCurrStatus() {
    	return currStatus;
    }
    
    public Integer getCurrCapacity() {
    	return currCapacity;
    }
    
    public int getwaitList_size() {// added to test_exitPeople
    	return waitList.size();
    }

    Elevator(Integer cap, Integer totalFloor, long flrSec, long dorSec, long idleSec) throws InvalidParameterException
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

    public void move() throws InterruptedException{

        entryNewPeople();
        currDirection();
        // check if ppls need to exits
        if (this.allStops.containsKey(this.currFloor)){
            exitPeople();
        }

        // exit move if there is not tofloor args
        if (this.allStops.isEmpty() && this.toFloor.equals(1) && this.currFloor.equals(1)){
            return;
        }
        // change here
        move_aux();
        

        Thread.sleep(this.floorSec);
    }
    
    // duplicated code fix, Extract Methods
    
   public void move_aux() {
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
    
    
    
    public void setElevatorNum(){
        this.elevatorNUM = Elevator.NUM;
        Elevator.NUM++;
    }
    
    public void currDirection(){
        if (this.allStops.isEmpty() && this.toFloor.equals(1)){
            this.currStatus = "IDLE";
            getInstance().updateElevator(this.elevatorNUM, this.currFloor, this.currCapacity, Direction.IDLE);
        } else {
            directionHelper();
            updateStatus();
        }
    }

	public void directionHelper() {
		up = false;
        down = false;
        
    	if (!this.toFloor.equals(1)) {
            if (this.toFloor > this.currFloor) {
                up = true;
            } else if (this.allStops.isEmpty()) {
                down = true;
            } else {
                this.toFloor = 1;
            }
        } else {
            for(Integer key : this.allStops.keySet()) {
                if (this.currFloor < key) {
                    up = true;
                } else if (this.currFloor > key) {
                    down = true;
                }
            }
        }
	}
	
	public void updateStatus() {
		 if (this.currFloor.equals(this.totalFloor)) {
             this.currStatus = "DOWN";
         } else if (this.currFloor.equals(1)) {
             this.currStatus = "UP";
         } else if ((this.currStatus.equals("UP") && up) 
        		 || (this.currStatus.equals("DOWN") && down) 
        		 || this.currStatus.equals("IDLE")) {
             // do nothing
         } else {
             if (this.currStatus.equals("UP")) {
                 this.currStatus = "DOWN";
             } else {
                 this.currStatus = "UP";
             }
         }
	}

	public List<People> exitPeople() throws InterruptedException{
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

        
        //change here
        	exit_waitlist_control(t);
        //end
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
    // Large Method/Long Method 
    // Extract method 
    public void exit_waitlist_control(long t) {
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
                          currDirection();
                      }
                  } else {
                      throw new InvalidParameterException("Elevator full, not allow to enter.");
                  }
              }
          }
          for (People p1: allPeople1){
              this.waitList.remove(p1);
          }
    }
    //end here
    

    public void addtoWaitList(People p){
        this.waitList.add(p);
    }

    public void entryNewPeople() throws InterruptedException {
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
                        currDirection();
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
    
    public long getFloorSec() {
    	return floorSec;
    }
}

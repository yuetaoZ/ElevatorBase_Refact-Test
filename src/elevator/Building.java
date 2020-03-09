package elevator;

import gui.ElevatorDisplay;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Building {
    private Integer floor_num;
    private List<Elevator> allElevator;

// 	seems like a dead field in this class
//  private ElevatorController control;


    public Building(Integer numFloor, Integer numElevator, Integer elevatorCap, Long flrSec, Long dorSec, Long idleSec) throws InvalidParameterException{
        setValidFloorNumber(numFloor);
        this.allElevator = new ArrayList<>();
        setNumOfElevator(numElevator, elevatorCap, flrSec, dorSec, idleSec);
    }

    private void setNumOfElevator(Integer numElevator, Integer elevatorCap, Long flrSec, Long dorSec, Long idleSec) throws InvalidParameterException{
        if (numElevator <= 0 || elevatorCap <= 0 || flrSec <= 0){
            throw new InvalidParameterException("No people exit!");
        }
        ElevatorDisplay.getInstance().initialize(floor_num);
        for(int i = 0; i < numElevator; i++){
            Elevator e = new Elevator(elevatorCap, floor_num, flrSec, dorSec, idleSec);
            Thread t = new Thread(e);
            t.start();
            this.allElevator.add(e);
        }
    }
    //create new method to set up floor number in this class - refactoring lazy class of Floor
    private void setValidFloorNumber(Integer num) {
        if (num <= 0){
            throw new InvalidParameterException("Invalid initialization of floor number.");
        }
        else {
        	floor_num = num;
        }
    }
    
    public List<Elevator> getElevators() {
    	return allElevator;
    }
}

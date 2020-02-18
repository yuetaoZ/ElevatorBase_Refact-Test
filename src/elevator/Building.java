package elevator;

import gui.ElevatorDisplay;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Building {
    private Floor floor;
    private List<Elevator> allElevator;
    private ElevatorController control;

    public Building(Integer numFloor, Integer numElevator, Integer elevatorCap, Long flrSec, Long dorSec, Long idleSec) throws InvalidParameterException{
        this.floor = new Floor(numFloor);
        this.allElevator = new ArrayList<>();
        setNumElevator(numElevator, elevatorCap, flrSec, dorSec, idleSec);
    }

    private void setNumElevator(Integer numElevator, Integer elevatorCap, Long flrSec, Long dorSec, Long idleSec) throws InvalidParameterException{
        if (numElevator <= 0 || elevatorCap <= 0 || flrSec <= 0){
            throw new InvalidParameterException("No people exit!");
        }
        ElevatorDisplay.getInstance().initialize(this.floor.numFloor);
        for(int i = 0; i < numElevator; i++){
            Elevator e = new Elevator(elevatorCap, this.floor.numFloor, flrSec, dorSec, idleSec);
            Thread t = new Thread(e);
            t.start();
            this.allElevator.add(e);
        }
    }
    
    public List<Elevator> getElevators() {
    	return allElevator;
    }
}

package elevator;

import java.security.InvalidParameterException;

public class People {

    private static Integer NUM = 1;
    public Integer startFloor;
    public Integer toFloor;
    public Integer peopleNUM;
    public String dir;
    public Long startTime;
    public Long waitTime;
    public Long rideTime;

    public People(Integer sFloor, Integer tFloor, Long sTime) throws InvalidParameterException
    {
        setStartFloor(sFloor);
        setToFloor(tFloor);
        setStartTime(sTime);
        setPeopleNum();
        if (this.startFloor < this.toFloor){
            this.dir = "UP";
        }else{
            this.dir = "DOWN";
        }
    }

    public void exitElevator(Long currTime){
        this.rideTime = currTime - (this.waitTime + this.startTime);
    }

    public void enterElevator(Long currTime){
        this.waitTime = currTime - this.startTime;
    }

    private void setStartFloor(Integer sFloor){
        if (sFloor <= 0 ){
            throw new InvalidParameterException("Invalid floor number");
        }
        this.startFloor = sFloor;
    }

    private void setPeopleNum(){
        this.peopleNUM = People.NUM;
        People.NUM++;
    }

    private void setToFloor(Integer tFloor){
        if (tFloor <= 0){
            throw new InvalidParameterException("Invalid floor number");
        }
        this.toFloor = tFloor;
    }

    private void setStartTime(Long sTime){
// dead code found
//        if (toFloor < 0){
//            throw new InvalidParameterException("Invalid floor number");
//        }
        this.startTime = sTime;
    }

}

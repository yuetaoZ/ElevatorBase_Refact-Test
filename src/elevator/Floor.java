package elevator;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

public class Floor {
    public Integer numFloor;
    public HashMap<Integer, List<People>> allFloor;

    public Floor(Integer num) throws InvalidParameterException{
        setNumFloor(num);
        this.allFloor = new HashMap<>();
    }

    private void setNumFloor(Integer num){
        if (num <= 0){
            throw new InvalidParameterException("Invalid floor number");
        }
        this.numFloor = num;
    }
}

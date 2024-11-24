package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

import java.util.List;

public class BloodFactory implements PropFactory{
    @Override
    public BaseProp createProps(int locationX, int locationY){
        return new BloodProp(locationX, locationY, 0, 5);
    }
}

package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

import java.util.List;

public class BombFactory implements PropFactory{
    @Override
    public BaseProp createProps(int locationX, int locationY){
        return new BombProp(locationX, locationY, 0, 5);
    }
}
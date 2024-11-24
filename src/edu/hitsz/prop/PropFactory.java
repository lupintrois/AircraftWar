package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

import java.util.List;

public interface PropFactory {
    public abstract BaseProp createProps(int locationX, int locationY);
}

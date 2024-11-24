package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootstrategy.CircleStrategy;
import edu.hitsz.shootstrategy.DispersedStrategy;
import edu.hitsz.shootstrategy.StraightStartegy;

public class PlusbulletProp extends BaseProp{
    public PlusbulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft aircraft) {
        System.out.println("PlusbulletProp active!");

        Runnable r = () -> {
            aircraft.setStrategy(new CircleStrategy());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            aircraft.setStrategy(new StraightStartegy());
        };
        new Thread(r).start();
        //aircraft.setStrategy(new CircleStrategy());
    }
}

package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootstrategy.DispersedStrategy;
import edu.hitsz.shootstrategy.StraightStartegy;

public class BulletProp extends BaseProp{
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft aircraft){
        System.out.println("FireSupply active!");
        Runnable r = () -> {
            aircraft.setStrategy(new DispersedStrategy());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            aircraft.setStrategy(new StraightStartegy());
        };
        new Thread(r).start();
        //aircraft.setStrategy(new DispersedStrategy());
    }
}

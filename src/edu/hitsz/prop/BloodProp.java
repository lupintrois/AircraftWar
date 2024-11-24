package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;

public class BloodProp extends BaseProp{
    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft aircraft){
        MusicThread blood_thread = new MusicThread("src/videos/get_supply.wav");
        blood_thread.setFlag(1);
        blood_thread.setCycle(0);
        blood_thread.start();
        //new MusicThread("src/videos/get_supply.wav").start();
        aircraft.increaseHp(60);
        System.out.println("Hp increase!");
    }
}

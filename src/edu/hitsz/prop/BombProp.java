package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;
import edu.hitsz.basic.Observer;

import java.util.ArrayList;
import java.util.List;

public class BombProp extends BaseProp{
    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    //建立观察者列表
    private List<Observer> observers = new ArrayList<>();

    //添加观察者
    public void attach(Observer observer) {
        observers.add(observer);
    }

    //删除观察者
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void active(HeroAircraft aircraft) {
        //new MusicThread("src/videos/bomb_explosion.wav").start();
        MusicThread bomb_thread = new MusicThread("src/videos/bomb_explosion.wav");
        bomb_thread.setFlag(1);
        bomb_thread.setCycle(0);
        bomb_thread.start();
        System.out.println("BombSupply active!");
        for (Observer observer: observers) {
            observer.update(aircraft);
        }
    }
}

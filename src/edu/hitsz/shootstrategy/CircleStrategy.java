package edu.hitsz.shootstrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class CircleStrategy implements ShootStrategy{
    @Override
    public List<BaseBullet> generatebullet(AbstractAircraft aircraft) {
        aircraft.setShootNum(20);
        List<BaseBullet> res = new LinkedList<>();
        int x0 = aircraft.getLocationX();
        int y0 = aircraft.getLocationY();
        BaseBullet bullet;
        int r = aircraft.getDirection() * 150;
        int v = aircraft.getDirection() * 5;
        for(int i=0; i<aircraft.getShootNum(); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            double angle = (i+1) * 360 / aircraft.getShootNum();
            int x = (int)(x0 + r*Math.cos(angle));
            int y = (int)(y0 + r*Math.sin(angle));
            int speedX = (int)(v*Math.cos(angle));
            int speedY = (int)(v*Math.sin(angle));
            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(x, y, speedX, speedY, aircraft.getPower());
                res.add(bullet);
            }
            else {
                bullet = new EnemyBullet(x, y, speedX, speedY, aircraft.getPower());
                res.add(bullet);
            }
        }
        return res;
    }
}

package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.basic.Observer;
import edu.hitsz.prop.*;
import edu.hitsz.shootstrategy.StraightStartegy;
import java.util.List;

public class EliteEnemy extends AbstractEnemy implements Observer {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        super.setPower(10);
        super.setDirection(1);
        super.setShootNum(1);
        super.setStrategy(new StraightStartegy());
        super.setScore(20);//击败精英敌机获得20分
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public void PropGenerate(AbstractEnemy eliteEnemy, List<BaseProp> props) {
        //将精英敌机中生成道具的方法改为工厂模式生成道具
        double probility = Math.random();
        if (probility < 0.3) {
            BloodFactory bloodFactory = new BloodFactory();
            props.add(bloodFactory.createProps(eliteEnemy.getLocationX(), eliteEnemy.getLocationY()));
        }
        else if (probility < 0.5) {
            BombFactory bombFactory = new BombFactory();
            props.add(bombFactory.createProps(eliteEnemy.getLocationX(), eliteEnemy.getLocationY()));
        }
        else if (probility < 0.7) {
            BulletFactory bulletFactory = new BulletFactory();
            props.add(bulletFactory.createProps(eliteEnemy.getLocationX(), eliteEnemy.getLocationY()));
        }
        else if (probility < 0.9) {
            PlusbulletFactory plusbulletFactory = new PlusbulletFactory();
            props.add(plusbulletFactory.createProps(eliteEnemy.getLocationX(), eliteEnemy.getLocationY()));
        }
    }

    @Override
    public void update(HeroAircraft herocraft) {
        this.isValid = false;
        herocraft.setScore(herocraft.getScore()+this.getScore());
    }
}

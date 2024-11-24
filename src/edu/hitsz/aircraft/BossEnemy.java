package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;
import edu.hitsz.basic.Observer;
import edu.hitsz.prop.*;
import edu.hitsz.shootstrategy.CircleStrategy;
import java.util.List;

public class BossEnemy extends AbstractEnemy implements Observer {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        super.setPower(10);
        super.setDirection(1);
        super.setShootNum(20);
        super.setStrategy(new CircleStrategy());
        super.setScore(50);//击败boss敌机获得50分
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
    public void update(HeroAircraft herocraft) {

    }

    @Override
    public void PropGenerate(AbstractEnemy bossEnemy, List<BaseProp> props) {
        //30%的概率生成1个道具，30%的概率生成2个道具， 30%的概率生成3个道具
        double num = Math.random();
        if (num < 0.3) {
            if (num < 0.1) {
                BloodFactory bloodFactory = new BloodFactory();
                props.add(bloodFactory.createProps(bossEnemy.getLocationX(), bossEnemy.getLocationY()));
            }
            else if (num >= 0.1 && num < 0.2) {
                BombFactory bombFactory = new BombFactory();
                props.add(bombFactory.createProps(bossEnemy.getLocationX(), bossEnemy.getLocationY()));
            }
            else {
                BulletFactory bulletFactory = new BulletFactory();
                props.add(bulletFactory.createProps(bossEnemy.getLocationX(), bossEnemy.getLocationY()));
            }
        }
        else if (num >= 0.3 && num < 0.6) {
            if (num < 0.4) {
                BloodFactory bloodFactory = new BloodFactory();
                props.add(bloodFactory.createProps(bossEnemy.getLocationX() - 50, bossEnemy.getLocationY()));
                BombFactory bombFactory = new BombFactory();
                props.add(bombFactory.createProps(bossEnemy.getLocationX() + 50, bossEnemy.getLocationY()));
            }
            else if (num >= 0.4 && num < 0.5) {
                BloodFactory bloodFactory = new BloodFactory();
                props.add(bloodFactory.createProps(bossEnemy.getLocationX() - 50, bossEnemy.getLocationY()));
                BulletFactory bulletFactory = new BulletFactory();
                props.add(bulletFactory.createProps(bossEnemy.getLocationX() + 50, bossEnemy.getLocationY()));
            }
            else {
                BombFactory bombFactory = new BombFactory();
                props.add(bombFactory.createProps(bossEnemy.getLocationX() - 50, bossEnemy.getLocationY()));
                BulletFactory bulletFactory = new BulletFactory();
                props.add(bulletFactory.createProps(bossEnemy.getLocationX() + 50, bossEnemy.getLocationY()));
            }
        }
        else if (num >= 0.6 && num < 0.9) {
            BloodFactory bloodFactory = new BloodFactory();
            props.add(bloodFactory.createProps(bossEnemy.getLocationX() - 50, bossEnemy.getLocationY()));
            BombFactory bombFactory = new BombFactory();
            props.add(bombFactory.createProps(bossEnemy.getLocationX(), bossEnemy.getLocationY()));
            BulletFactory bulletFactory = new BulletFactory();
            props.add(bulletFactory.createProps(bossEnemy.getLocationX() + 50, bossEnemy.getLocationY()));
        }
    }
}
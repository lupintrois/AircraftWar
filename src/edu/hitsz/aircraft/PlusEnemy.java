package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.basic.Observer;
import edu.hitsz.prop.*;
import edu.hitsz.shootstrategy.DispersedStrategy;
import java.util.List;

public class PlusEnemy extends AbstractEnemy implements Observer {

    public PlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        super.setPower(10);
        super.setDirection(1);
        super.setShootNum(3);
        super.setStrategy(new DispersedStrategy());
        super.setScore(30);//击败超级精英敌机获得30分
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
        this.decreaseHp(30);
        if (this.isValid == false) {
            //System.out.println("击败超级精英敌机，获得"+this.getScore()+"分");
            herocraft.setScore(herocraft.getScore()+this.getScore());
        }
    }

    @Override
    public void PropGenerate(AbstractEnemy plusEnemy, List<BaseProp> props) {
        //将精英敌机中生成道具的方法改为工厂模式生成道具
        double probility = Math.random();
        if (probility < 0.3) {
            BloodFactory bloodFactory = new BloodFactory();
            props.add(bloodFactory.createProps(plusEnemy.getLocationX(), plusEnemy.getLocationY()));
        }
        else if (probility < 0.5) {
            BombFactory bombFactory = new BombFactory();
            props.add(bombFactory.createProps(plusEnemy.getLocationX(), plusEnemy.getLocationY()));
        }
        else if (probility < 0.7) {
            BulletFactory bulletFactory = new BulletFactory();
            props.add(bulletFactory.createProps(plusEnemy.getLocationX(), plusEnemy.getLocationY()));
        }
        else if (probility < 0.9) {
            PlusbulletFactory plusbulletFactory = new PlusbulletFactory();
            props.add(plusbulletFactory.createProps(plusEnemy.getLocationX(), plusEnemy.getLocationY()));
        }
    }
}


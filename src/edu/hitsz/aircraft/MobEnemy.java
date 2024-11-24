package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.basic.Observer;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.shootstrategy.StraightStartegy;

import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemy implements Observer {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        super.setPower(0);
        super.setDirection(0);
        super.setShootNum(0);
        super.setStrategy(new StraightStartegy());
        super.setScore(10);//击败普通敌机获得10分
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
        this.isValid = false;
        //System.out.println("击败普通敌机，获得"+this.getScore()+"分");
        herocraft.setScore(herocraft.getScore()+this.getScore());
    }

    @Override
    public void PropGenerate(AbstractEnemy mobenemy, List<BaseProp> props){}
}

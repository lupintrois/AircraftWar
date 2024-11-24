package edu.hitsz.aircraft;

import edu.hitsz.prop.BaseProp;

import java.util.List;

public abstract class AbstractEnemy extends AbstractAircraft{
    private int score;

    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public int getSpeedX() {
        return super.speedX;
    }

    public void setSpeedX(int speedX) {
        super.speedX = speedX;
    }

    public int getSpeedY() {
        return super.speedY;
    }

    public void setSpeedY(int speedY) {
        super.speedY = speedY;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        super.hp = hp;
    }

    public abstract void PropGenerate(AbstractEnemy enemyaircraft, List<BaseProp> props);
}

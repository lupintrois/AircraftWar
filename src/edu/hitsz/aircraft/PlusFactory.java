package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class PlusFactory implements EnemyFactory{
    @Override
    public AbstractEnemy createEnemyAircraft() {
        double flag = Math.random();
        int ran = (int)(1+Math.random()*5);
        int speedX;
        if (flag <= 0.5) {
            speedX = ran;
        }
        else {
            speedX = -ran;
        }
        return new PlusEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                speedX,
                5,
                60);
    }
}
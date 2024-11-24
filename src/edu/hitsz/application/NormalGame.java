package edu.hitsz.application;

import edu.hitsz.aircraft.*;

import java.awt.*;
import java.time.LocalDateTime;

public class NormalGame extends Game{
    NormalGame() {
        super();
    }

    @Override
    public void generateEnemy() {
        //普通模式的最大敌机数量为7
        enemyMaxNumber = 7;
        if (enemyAircrafts.size() < enemyMaxNumber) {
            //用工厂模式生成普通敌机和精英敌机
            //并且将初始化信息封装起来
            //普通模式各种敌机的产生概率
            double probility = Math.random();
            if (probility <= 0.4) {
                MobFactory mobFactory = new MobFactory();
                AbstractEnemy enemy = mobFactory.createEnemyAircraft();
                //普通敌机的初始速度为10，随时间增加，最大到12
                int speedy = 10*(int)(1+timerate/2);
                if (speedy > 12) {
                    speedy = 12;
                }
                enemy.setSpeedY(speedy);
                enemyAircrafts.add(enemy);
            } else if (probility <= 0.8) {
                EliteFactory eliteFactory = new EliteFactory();
                AbstractEnemy enemy = eliteFactory.createEnemyAircraft();
                //增加精英敌机的血量为60
                enemy.setHp(60);
                //精英敌机的y轴初始速度为5，随时间增加，最大到10
                int speedy = 5*(int)(1+timerate);
                if (speedy > 10) {
                    speedy = 10;
                }
                enemy.setSpeedY(speedy);
                enemyAircrafts.add(enemy);
            } else {
                PlusFactory plusFactory = new PlusFactory();
                AbstractEnemy enemy = plusFactory.createEnemyAircraft();
                //精英敌机的y轴初始速度为5，随时间增加，最大到8
                int speedy = 5*(int)(1+timerate);
                if (speedy > 8) {
                    speedy = 8;
                }
                enemy.setSpeedY(speedy);
                enemyAircrafts.add(enemy);
            }
        }
    }


    @Override
    public void generateBoss() {
        //普通木事下的boss敌机每400分产生一次，且每次的血量固定位180
        if (heroAircraft.getScore() % 400 <= 50 && heroAircraft.getScore() >= 400  && flag == 1) {
            boss_thread = new MusicThread("src/videos/bgm_boss.wav");
            boss_thread.setFlag(1);
            boss_thread.setCycle(1);
            boss_thread.start();
            BossFactory bossFactory = new BossFactory();
            AbstractEnemy boss = bossFactory.createEnemyAircraft();
            enemyAircrafts.add(boss);
            System.out.println("当前boss敌机的血量为"+boss.getHp());
            flag = 0;
        }
    }

    @Override
    public void paintBG(Graphics g) {
        g.drawImage(ImageManager.BACKGROUND_IMAGE_NORMAL, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE_NORMAL, 0, this.backGroundTop, null);
    }

    @Override
    public void generateRankList() {
        String username = Main.name;
        Main.cardPanel.add(new RankListTable("NORMAL", heroAircraft.getScore(), username, LocalDateTime.now()).getMainPanel());
        Main.cardLayout.last(Main.cardPanel);
    }

    @Override
    public void timeIncrease() {
        timerate += 0.01;
    }
}

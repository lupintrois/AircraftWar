package edu.hitsz.application;

import edu.hitsz.aircraft.*;

import java.awt.*;
import java.time.LocalDateTime;

public class HardGame extends Game{
    private int cnt = 0;//用来记录产生了多少个boss敌机

    HardGame() {
        super();
    }

    @Override
    public void generateEnemy() {
        //困难模式的最大敌机数量为9
        enemyMaxNumber = 9;
        if (enemyAircrafts.size() < enemyMaxNumber) {
            //用工厂模式生成普通敌机和精英敌机
            //并且将初始化信息封装起来
            //普通模式各种敌机的产生概率
            double probility = Math.random();
            if (probility <= 0.3) {
                MobFactory mobFactory = new MobFactory();
                AbstractEnemy enemy = mobFactory.createEnemyAircraft();
                //增加普通敌机的血量为60
                enemy.setHp(60);
                //普通敌机的初始速度为10，随时间增加，最大到15
                int speedy = 10*(int)(1+timerate/2);
                if (speedy > 15) {
                    speedy = 15;
                }
                enemy.setSpeedY(speedy);
                enemyAircrafts.add(enemy);
            }
            else if (probility <= 0.6) {
                EliteFactory eliteFactory = new EliteFactory();
                AbstractEnemy enemy = eliteFactory.createEnemyAircraft();
                //增加精英敌机的血量为60
                enemy.setHp(60);
                //精英敌机的y轴初始速度为5，随时间增加，最大到13
                int speedy = 5*(int)(1+timerate);
                if (speedy > 10) {
                    speedy = 10;
                }
                enemy.setSpeedY(speedy);
                enemyAircrafts.add(enemy);
            }
            else {
                PlusFactory plusFactory = new PlusFactory();
                AbstractEnemy enemy = plusFactory.createEnemyAircraft();
                //增加超级精英敌机的血量为90
                enemy.setHp(90);
                //精英敌机的y轴初始速度为5，随时间增加，最大到13
                int speedy = 5*(int)(1+timerate);
                if (speedy > 10) {
                    speedy = 10;
                }
                enemy.setSpeedY(speedy);
                enemyAircrafts.add(enemy);
            }
        }
    }

    @Override
    public void generateBoss() {
        //困难模式下，每200分产生一次boss机，且每次boss机的血量都增加60
        if (heroAircraft.getScore() % 200 <= 50 && heroAircraft.getScore() >= 200  && flag == 1) {
            boss_thread = new MusicThread("src/videos/bgm_boss.wav");
            boss_thread.setFlag(1);
            boss_thread.setCycle(1);
            boss_thread.start();
            BossFactory bossFactory = new BossFactory();
            AbstractEnemy boss = bossFactory.createEnemyAircraft();
            boss.setHp(180+60*cnt);//每次生成的boss敌机的血量增加60
            enemyAircrafts.add(boss);
            System.out.println("当前boss的血量为"+boss.getHp());
            cnt += 1;
            flag = 0;
        }
    }

    @Override
    public void paintBG(Graphics g) {
        g.drawImage(ImageManager.BACKGROUND_IMAGE_HARD, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE_HARD, 0, this.backGroundTop, null);
    }

    @Override
    public void generateRankList() {
        String username = Main.name;
        Main.cardPanel.add(new RankListTable("HARD", heroAircraft.getScore(), username, LocalDateTime.now()).getMainPanel());
        Main.cardLayout.last(Main.cardPanel);
    }

    @Override
    public void timeIncrease() {
        timerate += 0.05;
    }
}

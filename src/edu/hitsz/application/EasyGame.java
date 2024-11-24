package edu.hitsz.application;

import edu.hitsz.aircraft.*;

import java.awt.*;
import java.time.LocalDateTime;

public class EasyGame extends Game{
    EasyGame() {
        super();
    }

    @Override
    public void generateEnemy() {
        enemyMaxNumber = 5;
        if (enemyAircrafts.size() < enemyMaxNumber) {
            //用工厂模式生成普通敌机和精英敌机
            //并且将初始化信息封装起来
            //简单模式的各种敌机产生概率
            double probility = Math.random();
            if (probility <= 0.5) {
                MobFactory mobFactory = new MobFactory();
                enemyAircrafts.add(mobFactory.createEnemyAircraft());
            }
            else if (probility <= 0.8) {
                EliteFactory eliteFactory = new EliteFactory();
                enemyAircrafts.add(eliteFactory.createEnemyAircraft());
            }
            else {
                PlusFactory plusFactory = new PlusFactory();
                enemyAircrafts.add(plusFactory.createEnemyAircraft());
            }
        }
    }

    @Override
    public void generateBoss() {
        //简单模式不产生boss敌机
    }

    @Override
    public void paintBG(Graphics g) {
        g.drawImage(ImageManager.BACKGROUND_IMAGE_EASY, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE_EASY, 0, this.backGroundTop, null);
    }

    @Override
    public void generateRankList() {
        String username = Main.name;
        Main.cardPanel.add(new RankListTable("EASY", heroAircraft.getScore(), username, LocalDateTime.now()).getMainPanel());
        Main.cardLayout.last(Main.cardPanel);
    }

    @Override
    public void timeIncrease() {
        //简单模式不随增加难度
    }
}

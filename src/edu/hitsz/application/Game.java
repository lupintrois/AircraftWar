package edu.hitsz.application;

import edu.hitsz.RankList.RankList;
import edu.hitsz.RankList.RankListDao;
import edu.hitsz.RankList.RankListDaoImpl;
import edu.hitsz.aircraft.*;
import edu.hitsz.basic.Observer;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.*;
import edu.hitsz.shootstrategy.StraightStartegy;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    protected int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    protected final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;

    protected static HeroAircraft heroAircraft;

    protected static List<AbstractEnemy> enemyAircrafts;
    protected static List<BaseBullet> heroBullets;
    protected static List<BaseBullet> enemyBullets;
    //创立道具列表
    protected static List<BaseProp> props;

    /**
     * 屏幕中出现的敌机最大数量
     */
    protected int enemyMaxNumber;

    /**
     * 当前时刻
     */
    protected int time = 0;

    //作为当前场景中有boss敌机的标志
    protected int flag = 1;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 600;
    protected int cycleTime = 0;

    protected double timerate = 0;

    protected static MusicThread boss_thread;

    /**
     * 游戏结束标志
     */
    protected boolean gameOverFlag = false;

    public Game() {
        //创建英雄机
        //单例模式
        //把初始化类的细节数据封装在类的内部
        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */

    //protected MusicThread boss_thread;

    public void action() {

        //开启音乐
        MusicThread bgm_thread = new MusicThread("src/videos/bgm.wav");
        bgm_thread.setFlag(1);
        bgm_thread.setCycle(1);
        bgm_thread.start();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);

                timeIncrease();

                for (AbstractEnemy enemyAircraft: enemyAircrafts) {
                    if (enemyAircraft instanceof BossEnemy) {
                        break;
                    }
                }
                //产生敌机的方法
                generateEnemy();
                //产生boss机的方法
                generateBoss();
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();//共用

            // 飞机移动
            aircraftsMoveAction();//共用

            //道具移动
            propMoveAction();//共用

            // 撞击检测
            try {
                crashCheckAction();//共用
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 后处理
            postProcessAction();//共用

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;

                //关闭音乐
                bgm_thread.setFlag(0);
                if (flag == 0) {
                    boss_thread.setFlag(0);
                }

                MusicThread over_thread = new MusicThread("src/videos/game_over.wav");
                over_thread.setFlag(1);
                over_thread.setCycle(0);
                over_thread.start();
                System.out.println("Game Over!");
                //产生排行榜
                generateRankList();
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    //生成排行榜的抽象方法
    public abstract void generateRankList();

    //生成敌机的抽象方法
    public abstract void generateEnemy();

    //生成boss机的抽象方法
    public abstract void generateBoss();

    //随时间增加难度
    public abstract void timeIncrease();

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    //射击方法
    private void shootAction() {
        // TODO 敌机射击
        for (AbstractEnemy enemyAircraft : enemyAircrafts){
            enemyBullets.addAll(enemyAircraft.shoot(enemyAircraft));
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot(heroAircraft));
    }

    //子弹运动方法
    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    //飞机运动方法
    private void aircraftsMoveAction() {
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    //道具运动方法
    private void propMoveAction() {
        for (BaseProp props: props) {
            props.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() throws InterruptedException {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    MusicThread hit_thread = new MusicThread("src/videos/bullet_hit.wav");
                    hit_thread.setFlag(1);
                    hit_thread.setCycle(0);
                    hit_thread.start();
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if (enemyAircraft instanceof BossEnemy) {
                            flag = 1;
                            boss_thread.setFlag(0);
                        }
                        // TODO 获得分数，产生道具补给
                        //score += 10;
                        //heroAircraft.setScore(heroAircraft.getScore()+10);
                        heroAircraft.setScore(heroAircraft.getScore()+enemyAircraft.getScore());
                        enemyAircraft.PropGenerate(enemyAircraft, props);
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (BaseProp baseProp : props){
            if(baseProp.notValid()) {
                continue;
            }
            //判断英雄机是否碰撞到道具
            if (heroAircraft.crash(baseProp)) {
                baseProp.vanish();
                if (baseProp instanceof BombProp) {
                    for (AbstractEnemy enemyaircraft: enemyAircrafts) {
                        ((BombProp) baseProp).attach((Observer)enemyaircraft);
                    }
                    for (BaseBullet enemyBullet: enemyBullets) {
                        ((BombProp) baseProp).attach((Observer) enemyBullet);
                    }

                }
                baseProp.active(heroAircraft);
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        paintBG(g);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, props);
        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    //绘制背景图的抽象方法
    public abstract void paintBG(Graphics g);

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        //g.drawString("SCORE:" + this.score, x, y);
        g.drawString("SCORE:" + heroAircraft.getScore(), x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
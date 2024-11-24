package edu.hitsz.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft heroAircraft;

    @BeforeEach
//在所有方法之前初始化
    void setUp() {
        //初始化测试对象
        heroAircraft = HeroAircraft.getInstance();
        heroAircraft.hp = 1000;
    }

    @AfterEach
//在sub之后清空
    void tearDown() {
        heroAircraft = null;
    }

    @DisplayName("Test decreaseHp method")
    @Test
    void decreaseHptest() {
        int decrease = 10;
        heroAircraft.decreaseHp(decrease);
        assertEquals(990, heroAircraft.getHp());
    }

    @DisplayName("Test getHp method")
    @Test
    void getHptest() {
        assertEquals(1000, heroAircraft.getHp());
    }

    @DisplayName("Test increaseHp method when maxhp")
    @Test
    void increaseHptest1() {
        int increase = 10;
        heroAircraft.increaseHp(increase);
        assertEquals(1000, heroAircraft.getHp());
    }

    @DisplayName("Test increaseHp method when notmaxhp")
    @Test
    void increaseHptest2() {
        heroAircraft.hp = 900;
        int increase = 10;
        heroAircraft.increaseHp(increase);
        assertEquals(910, heroAircraft.getHp());
    }
}
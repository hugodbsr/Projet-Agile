package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.Missile;


public class MissileTest {
    @Test
    void test_missile_delay(){
        assertEquals(Missile.CLASSIC.getDelay(), 0);
        assertEquals(Missile.RECO.getDelay(), 3);
        assertEquals(Missile.HEAVY.getDelay(), 3);
    }

    @Test
    void test_missile_zone(){
        assertArrayEquals(Missile.CLASSIC.getZone(), new boolean[][] {{true}});
        assertArrayEquals(Missile.RECO.getZone(), new boolean[][] {{true, true, true}, {true, true, true}, {true, true, true}});
        assertArrayEquals(Missile.HEAVY.getZone(), new boolean[][] {{false, true, false}, {false, true, false}, {false, true, false}});
    }
}

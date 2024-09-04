package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.Bateau;
import main.Cuirasser;

public class BateauTest {
    @Test
    void test_health_and_sinking_a_cuirasser(){

        Bateau b1 = new Cuirasser();

        assertEquals(4, b1.getHealth());
        assertFalse(b1.isSunk());
        b1.hit();
        assertEquals(3, b1.getHealth());
        b1.hit();
        b1.hit();
        b1.hit();
        assertTrue(b1.isSunk());
    }
}

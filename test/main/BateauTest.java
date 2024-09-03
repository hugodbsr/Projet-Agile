package main;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BateauTest {
    public Bateau b1,b2,b3,b4;

    @BeforeEach
    void initialisation(){
        b1 = new Cuirasser();
        b2 = new Destroyer();
        b3 = new Croiseur();
        b4 = new PorteAvion();
    }

    @Test
    void testHealthAndSinking(){
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

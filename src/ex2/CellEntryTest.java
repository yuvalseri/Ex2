package ex2;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class CellEntryTest {
    @Test
    void isValid() {
        CellEntry c1 =new CellEntry("A1");
        assertTrue(c1.isValid());

        CellEntry c2 =new CellEntry("G7");
        assertTrue(c2.isValid());

        CellEntry c3 =new CellEntry("F4");
        assertTrue(c3.isValid());

        CellEntry c4 =new CellEntry("99");
        assertFalse(c4.isValid());

        CellEntry c5 =new CellEntry("7B");
        assertFalse(c5.isValid());

        CellEntry c6 =new CellEntry("D111");
        assertFalse(c6.isValid());

    }

    @Test
    void getX() {
        CellEntry c7 =new CellEntry("A1");
        assertEquals(0, c7.getX());
        CellEntry c8 =new CellEntry("B5");
        assertEquals(1, c8.getX());
        CellEntry c9 =new CellEntry("E10");
        assertEquals(4, c9.getX());
    }

    @Test
    void getY() {
        CellEntry c7 =new CellEntry("A1");
        assertEquals(1, c7.getY());
        CellEntry c8 =new CellEntry("B5");
        assertEquals(5, c8.getY());
        CellEntry c9 =new CellEntry("E10");
        assertEquals(10, c9.getY());
    }
}
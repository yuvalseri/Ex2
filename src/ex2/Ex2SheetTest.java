package ex2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class Ex2SheetTest {

    @Test
    void value() {
        Ex2Sheet sheet = new Ex2Sheet();

        sheet.set(0, 0, "7");
        sheet.set(1, 0, "=A1+10");
        sheet.set(2, 0, "Hello");
        sheet.set(3, 0, "");
        sheet.set(4, 0, "=A1++10");

        assertEquals("7.0", sheet.value(0, 0));
        assertEquals("17.0", sheet.value(1, 0));
        assertEquals("Hello", sheet.value(2, 0));
        assertEquals(Ex2Utils.EMPTY_CELL, sheet.value(3, 0));
        assertEquals(Ex2Utils.ERR_FORM, sheet.value(4, 0));
    }

    @Test
    void get() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "Hello");
        sheet.set(2, 2, "=A1+4");

        assertEquals("5", sheet.get(0,0).toString());
        assertEquals("Hello",sheet.get(1,1).toString());
        assertEquals("=A1+4", sheet.get(2,2).toString());
    }

    @Test
    void testGet() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "Hello");
        sheet.set(2, 2, "=A1+4");
        sheet.set(3, 3, "");


        assertEquals("5", sheet.get("A1").toString());
        assertEquals("Hello", sheet.get("B2").toString());
        assertEquals("=A1+4", sheet.get("C3").toString());
        assertEquals(Ex2Utils.EMPTY_CELL, sheet.get("D4").toString());
        assertThrows(IllegalArgumentException.class, () -> {
            sheet.get("AB");
        });
    }

    @Test
    void width() {
        Ex2Sheet sheet = new Ex2Sheet(10, 10);
        assertEquals(10, sheet.width());
    }

    @Test
    void height() {
        Ex2Sheet sheet = new Ex2Sheet(10, 10);
        assertEquals(10, sheet.height());
    }

    @Test
    void set() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "8");
        assertEquals("8.0", sheet.value(0, 0));

        sheet.set(1, 1, "10");
        assertEquals("10.0", sheet.value(1, 1));

        sheet.set(2, 2, "=A1+B2");
        assertEquals("18.0", sheet.value(2, 2));
    }

    @Test
    void eval() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "7");
        sheet.set(1, 0, "=A1+10");
        sheet.set(2, 0, "Hello");
        sheet.set(3, 0, "");
        sheet.set(4, 0, "=A1++10");
        sheet.eval();

        assertEquals("7.0", sheet.value(0, 0));
        assertEquals("17.0", sheet.value(1, 0));
        assertEquals("Hello", sheet.value(2, 0));
        assertEquals(Ex2Utils.EMPTY_CELL, sheet.value(3, 0));
        assertEquals(Ex2Utils.ERR_FORM, sheet.value(4, 0));
    }

    @Test
    void isIn() {
        Ex2Sheet sheet = new Ex2Sheet(10, 10);
        assertTrue(sheet.isIn(0,7));
        assertTrue(sheet.isIn(1,3));

        assertFalse(sheet.isIn(11,3));
        assertFalse(sheet.isIn(100,11));
    }

    @Test
    void depth() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "2");
        sheet.set(1, 1, "3");
        sheet.set(2, 2, "=A1*B2+5");
        sheet.set(3, 3, "=A1*B2+C3+5");

        int[][] depths = sheet.depth();

        assertEquals(0, depths[0][0]);
        assertEquals(0, depths[1][1]);
        assertEquals(1, depths[2][2]);
        //assertEquals(2, depths[3][3]);

    }

    @Test
    void load() {
        Ex2Sheet sheet = new Ex2Sheet();
        try {
            sheet.load("test_save.csv");
        } catch (IOException e) {
            fail("Failed to load spreadsheet: " + e.getMessage());
        }

        assertEquals("5", sheet.get("A1").toString(), "The value in A1 should be 5.");
        assertEquals("=A1+10", sheet.get("B2").toString(), "The formula in B2 should be '=A1+10'.");
        assertEquals("Hello", sheet.get("C3").toString(), "The value in C3 should be 'Hello'.");
    }


    @Test
    void save() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "=A1+10");
        sheet.set(2, 2, "Hello");

        try {
            sheet.save("test_save.csv");
        } catch (IOException e) {
            fail("Failed to save spreadsheet: " + e.getMessage());
        }

        File file = new File("test_save.csv");
        assertTrue("The file should exist after saving.", file.exists());
    }


    @Test
    void testEval() {

        Ex2Sheet sheet = new Ex2Sheet(10, 10);

        sheet.set(0, 0, "=5+3*2");
        sheet.set(0, 1, "Hello");
        sheet.set(0, 2, "=1+2");
        sheet.set(0, 3, "=(1+2)*3/(2/5)");
        sheet.set(0, 4, "=(10-5*2)");
        sheet.set(0, 5, "9");
        sheet.set(0, 6, "#$^D");
        sheet.set(0, 7, "=A1+A3");
        sheet.set(0, 8, "=A3+A6");

        assertEquals("11.0", sheet.eval(0, 0));
        assertEquals("Hello", sheet.eval(0, 1));
        assertEquals("3.0", sheet.eval(0, 2));
        assertEquals("22.5", sheet.eval(0, 3));
        assertEquals("0.0", sheet.eval(0, 4));
        assertEquals("9", sheet.eval(0, 5));
        assertEquals("#$^D", sheet.eval(0, 6));
        assertEquals("14.0", sheet.eval(0, 7));
        assertEquals("12.0", sheet.eval(0, 8));
    }
    @Test
    void indOfMainOp(){
        assertEquals(-1, Ex2Sheet.indOfMainOp("=1"));
        assertEquals(-1, Ex2Sheet.indOfMainOp("5"));
        assertEquals(-1, Ex2Sheet.indOfMainOp("=DFGG"));
        assertEquals(3, Ex2Sheet.indOfMainOp("=A2+3"));
        assertEquals(7, Ex2Sheet.indOfMainOp("=(A2+3)/(7-A1)"));
        assertEquals(2, Ex2Sheet.indOfMainOp("=5*(1+7)"));
        assertEquals(4, Ex2Sheet.indOfMainOp("=5-1+7"));
    }
}
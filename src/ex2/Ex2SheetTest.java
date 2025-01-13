package ex2;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class Ex2SheetTest {

    @Test
    void value() {
    }

    @Test
    void get() {
    }

    @Test
    void testGet() {
    }

    @Test
    void width() {
    }

    @Test
    void height() {
    }

    @Test
    void set() {

    }

    @Test
    void eval() {

    }

    @Test
    void isIn() {
        //Ex2Sheet c= new Ex2Sheet();
        //assertTrue(Ex2Sheet.isIn());
    }

    @Test
    void depth() {
    }

    @Test
    void load() {
    }

    @Test
    void save() {
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
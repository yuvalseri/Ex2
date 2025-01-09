package ex2;

import org.junit.jupiter.api.Test;

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
    }
    @Test
    void indOfMainOp(){
        assertEquals(-1, Ex2Sheet.indOfMainOp("=1"));
        assertEquals(-1, Ex2Sheet.indOfMainOp("5"));
        assertEquals(-1, Ex2Sheet.indOfMainOp("=DFGG"));
        assertEquals(3, Ex2Sheet.indOfMainOp("=A2+3"));
        //assertEquals(6, Ex2Sheet.indOfMainOp("=(A2+3)/(7-A1)"));
    }
}
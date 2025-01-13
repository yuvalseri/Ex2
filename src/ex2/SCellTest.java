package ex2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
class SCellTest {

    @org.junit.jupiter.api.Test
    void isNumber() {
        assertTrue(SCell.isNumber("15"));
        assertTrue(SCell.isNumber("7.6"));
        assertTrue(SCell.isNumber("1"));

        assertFalse(SCell.isNumber("A1"));
        assertFalse(SCell.isNumber("1+5"));
        assertFalse(SCell.isNumber("1@2"));
    }

    @org.junit.jupiter.api.Test
    void isForm() {
        assertTrue(SCell.isForm("=A2+3"));
        assertTrue(SCell.isForm("=(1+2)*((3))-1"));
        assertTrue(SCell.isForm("=(0.2)"));
        assertTrue(SCell.isForm("=A3"));
        assertTrue(SCell.isForm("=a3"));
        assertTrue(SCell.isForm("=A1+A2"));
        assertTrue(SCell.isForm("=(2+A3)/A2"));
        assertTrue(SCell.isForm("=(g4+f5)-7"));
        assertTrue(SCell.isForm("=(g4+f5)/(H5+9)"));
        assertTrue(SCell.isForm("=(A2+3)/(7-A1)"));

        assertFalse(SCell.isForm("=(1+2)=((3))-1"));
        assertFalse(SCell.isForm("=(5"));
        assertFalse(SCell.isForm("1"));
        assertFalse(SCell.isForm(" "));
        assertFalse(SCell.isForm("=()"));
        assertFalse(SCell.isForm("=@"));
        assertFalse(SCell.isForm("=A176"));
        assertFalse(SCell.isForm("=AB"));
        assertFalse(SCell.isForm("=1+8+"));
        assertFalse(SCell.isForm("=1++8"));
        assertFalse(SCell.isForm("=1+-8"));
    }



    @org.junit.jupiter.api.Test
    void isText() {
        assertTrue(SCell.isText("A2+3"));
        assertTrue(SCell.isText("@#!"));
        assertTrue(SCell.isText("adf"));
        assertTrue(SCell.isText("#+7"));

        assertFalse(SCell.isText("1"));
        assertFalse(SCell.isText("=A6"));
        assertFalse(SCell.isText(" "));
    }

    @Test
    void getOrder() {
        SCell a1 = new SCell("10");
        SCell a2 = new SCell("20");
        SCell formulaCell = new SCell("=A1+A2");

        formulaCell.dependC = new ArrayList<>(List.of(a1, a2));
        assertEquals(1, formulaCell.getOrder());

        SCell a3 = new SCell("=A1+5");  //
        SCell a4 = new SCell("=A2+2");  //
        SCell c1 = new SCell("=A3+A4");

        c1.dependC = new ArrayList<>(List.of(a3, a4));
        assertEquals(2, c1.getOrder());

        SCell a5 = new SCell("=A1+A3*5");  //
        SCell a6 = new SCell("=A2+2");  //
        SCell c2 = new SCell("=A5*A6");

        c2.dependC = new ArrayList<>(List.of(a5, a6));
        assertEquals(2, c2.getOrder());

        SCell a7 = new SCell("5");
        SCell a8 = new SCell("10");
        SCell b1 = new SCell("=A1+2");
        SCell b2 = new SCell("=A2+3");
        SCell c3 = new SCell("=B1+B2");
        SCell d1 = new SCell("=C1*2");

        b1.dependC = new ArrayList<>(List.of(a7));
        b2.dependC = new ArrayList<>(List.of(a8));
        c3.dependC = new ArrayList<>(List.of(b1, b2));
        d1.dependC = new ArrayList<>(List.of(c3));

        assertEquals(3, d1.getOrder());

        SCell a9 = new SCell("=B1");
        SCell b9 = new SCell("=A1");

        a9.dependC = new ArrayList<>(List.of(b9));
        b9.dependC = new ArrayList<>(List.of(a9));
        assertThrows(IllegalStateException.class, () -> a9.getOrder(), "Expected Circular dependency detected!");



    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }

    @org.junit.jupiter.api.Test
    void setData() {
    }

    @org.junit.jupiter.api.Test
    void getData() {
    }

    @org.junit.jupiter.api.Test
    void getType() {
    }

    @org.junit.jupiter.api.Test
    void setType() {
    }

    @org.junit.jupiter.api.Test
    void setOrder() {
    }

    @Test
    void testGetDependencies() {

    }
}
package ex2;
import org.junit.jupiter.api.Test;

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
        //assertTrue(SCell.isForm("=(2+A3)/A2"));

        //assertFalse(SCell.isForm("=(1+2)=((3))-1"));
        assertFalse(SCell.isForm("=(5"));
        assertFalse(SCell.isForm("1"));
        assertFalse(SCell.isForm(" "));
        assertFalse(SCell.isForm("=()"));
        assertFalse(SCell.isForm("=@"));
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

    @org.junit.jupiter.api.Test
    void getOrder() {
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
}
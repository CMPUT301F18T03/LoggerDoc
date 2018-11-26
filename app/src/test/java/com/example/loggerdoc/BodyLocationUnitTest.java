package com.example.loggerdoc;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BodyLocationUnitTest {

    @Test
    public void TestDefault(){
        Bodylocation bodylocation = new Bodylocation();
        //Should be initialized to zero
        assertEquals(bodylocation.getBackX(), 0);
        assertEquals(bodylocation.getBackY(), 0);
        assertEquals(bodylocation.getFrontX(), 0);
        assertEquals(bodylocation.getFrontY(), 0);
    }
    @Test
    public void TestSetandGetBack(){
        Bodylocation bodylocation = new Bodylocation();
        bodylocation.setBackY(2);
        bodylocation.setBackX(3);

        assertEquals(2, bodylocation.getBackY());
        assertEquals(3, bodylocation.getBackX());

    }
    @Test
    public void TestSetandGetFront(){
        Bodylocation bodylocation = new Bodylocation();


        bodylocation.setFrontX(33);
        bodylocation.setFrontY(20);

        assertEquals(33, bodylocation.getFrontX());
        assertEquals(20, bodylocation.getFrontY());

    }
}

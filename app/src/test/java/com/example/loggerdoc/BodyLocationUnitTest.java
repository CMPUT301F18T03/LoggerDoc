package com.example.loggerdoc;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BodyLocationUnitTest {

    @Test
    public void TestDefault(){
        Bodylocation bodylocation = new Bodylocation();
        //Should be initialized to zero
        assertEquals(bodylocation.getBackTuple().get(0),bodylocation.getBackTuple().get(1));
    }
    @Test
    public void TestSetandGetBack(){
        Bodylocation bodylocation = new Bodylocation();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(20);

        bodylocation.setBackTuple(list.get(0), list.get(1));

        assertEquals(list.get(0), bodylocation.getBackTuple().get(0));
        assertEquals(list.get(1), bodylocation.getBackTuple().get(1));

    }
    @Test
    public void TestSetandGetFront(){
        Bodylocation bodylocation = new Bodylocation();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(20);

        bodylocation.setFrontTuple(list.get(0), list.get(1));

        assertEquals(list.get(0), bodylocation.getFrontTuple().get(0));
        assertEquals(list.get(1), bodylocation.getFrontTuple().get(1));

    }
}

package com.example.loggerdoc;

import android.widget.ArrayAdapter;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BodLocationTest {
    @Test
    public void TestBodylocation(){
        Bodylocation bodylocation = new Bodylocation();
        bodylocation.setFrontTuple(1,1);
        bodylocation.setBackTuple(2,2);
        ArrayList<Integer> back = new ArrayList<Integer>();
        back = bodylocation.getBackTuple();
        assertEquals(back.get(0),2);
    }
}

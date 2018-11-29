package com.example.loggerdoc;

import android.widget.ArrayAdapter;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BodLocationTest {
    @Test
    public void TestBodylocation(){
        Bodylocation bodylocation = new Bodylocation();
        bodylocation.setBackX(0);
        bodylocation.setBackY(0);
        bodylocation.setFrontX(0);
        bodylocation.setFrontY(0);

        assertEquals(bodylocation.getBackX(), bodylocation.getBackY());
        assertEquals(bodylocation.getFrontX(), bodylocation.getFrontY());

    }
}

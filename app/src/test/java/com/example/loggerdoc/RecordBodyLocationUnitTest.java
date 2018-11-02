package com.example.loggerdoc;

import org.junit.Test;

import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;

public class RecordBodyLocationUnitTest {

    @Test
    public void TestBodyLocation(){
        String side = "right";
        String location = "leg";
        Boolean front = TRUE;
        RecordBodyLocation bLocation = new RecordBodyLocation(side, front, location);
        assertEquals("side should be set", side, bLocation.Side);
        assertEquals("the location should be set", location, bLocation.location);
        assertEquals("the front should be set",front, bLocation.Front);

    }
}

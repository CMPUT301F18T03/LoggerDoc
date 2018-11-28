package com.example.loggerdoc;

import org.junit.Test;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;


public class CaregiverCommentUnitTest {

    @Test
    public void testSetDate(){
        CaregiverComment caregiverComment = new CaregiverComment ("Looks good");

        LocalDateTime expectedTime = LocalDateTime.now();
        caregiverComment.setDate(expectedTime);

        assertEquals(expectedTime, caregiverComment.getDate());
    }

    @Test
    public void testGetComment(){
        CaregiverComment caregiverComment = new CaregiverComment("Looks good");

        assertEquals("Looks good", caregiverComment.getComment());
    }

    @Test
    public void testSetComment(){
        CaregiverComment caregiverComment = new CaregiverComment("Looks good");

        caregiverComment.setComment("Not looking good");
        assertEquals("Not looking good", caregiverComment.getComment());
    }
}

package com.example.loggerdoc;

import org.junit.Test;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;


public class CaregiverCommentUnitTest {

    @Test
    public void testGetCaregiver(){
        CaregiverComment caregiverComment = new CaregiverComment(new CareGiver("test",
                "test", "test","test", new PatientList()),
                "Looks good");

        assertEquals("test", caregiverComment.getCaregiver());
    }

    @Test
    public void testSetCaregiver(){
        CaregiverComment caregiverComment = new CaregiverComment(new CareGiver("test",
                "test", "test","test", new PatientList()),
                "Looks good");

        CareGiver careGiver = new CareGiver("ABCDEFGHI",
                "yay", "yay","yay", new PatientList());

        caregiverComment.setCaregiver(careGiver);
        assertEquals(careGiver, caregiverComment.getCaregiver());
    }

    @Test
    public void testSetDate(){
        CaregiverComment caregiverComment = new CaregiverComment(new CareGiver("test",
                "test", "test","test", new PatientList()),
                "Looks good");

        LocalDateTime expectedTime = LocalDateTime.now();
        caregiverComment.setDate(expectedTime);

        assertEquals(expectedTime, caregiverComment.getDate());
    }

    @Test
    public void testGetComment(){
        CaregiverComment caregiverComment = new CaregiverComment(new CareGiver("test",
                "test", "test","test", new PatientList()),
                "Looks good");

        assertEquals("Looks good", caregiverComment.getComment());
    }

    @Test
    public void testSetComment(){
        CaregiverComment caregiverComment = new CaregiverComment(new CareGiver("test",
                "test", "test","test", new PatientList()),
                "Looks good");

        caregiverComment.setComment("Not looking good");
        assertEquals("Not looking good", caregiverComment.getComment());
    }
}

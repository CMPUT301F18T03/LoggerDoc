package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;


public class PatientUnitTest {

    @Test
    public void CreatePatientTest() {
        Patient testPatient1 = new Patient("test", "test", "test","test", new CareGiverList());
        Patient testPatient2 = new Patient("test", "test", "test","test", new CareGiverList());

        assertNotEquals("Check to see if the Patient objects are different", testPatient1, testPatient2);
    }

    @Test
    public void PatientUserIDTest() {
        Patient testPatient = new Patient("test", "test@ualberta.ca", "123455678","test", new CareGiverList());
        assertEquals("test", testPatient.getUserID());

        testPatient.setUserID("changing user ID");
        assertFalse("Changed the user ID so this should return False", testPatient.getUserID().equals("test"));
    }



}

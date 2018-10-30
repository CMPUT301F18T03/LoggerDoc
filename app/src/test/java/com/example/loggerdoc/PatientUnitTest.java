package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;


public class PatientUnitTest {

    @Test
    public void CreatePatientTest() {
        Patient patient1 = new Patient("test1", "test1", "test1", new CareGiverList());
        Patient patient2 = new Patient("test1", "test1", "test1", new CareGiverList());

        assertNotEquals("Check to see if the Patient objects are different", patient1, patient2);
    }

    @Test
    public void PatientUserIDTest() {
        Patient testPatient = new Patient("test", "test@ualberta.ca", "123455678", new CareGiverList());
        assertEquals("test", testPatient.getUserID());

        testPatient.setUserID("changing user ID");
        assertFalse("Changed the user ID so this should return False", testPatient.getUserID().equals("test"));
    }



}

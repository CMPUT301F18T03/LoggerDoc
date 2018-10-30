package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;

public class PatientListUnitTest {

    @Test
    public void TestAddPatient() {
        PatientList testList = new PatientList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

        Patient testPatient = new Patient("test", "test", "test", new CareGiverList());
        testList.addPatient(testPatient);
        assertTrue("Size should be 1", testList.size() == 1);

        assertTrue("Does the list contain the object we added", testList.contains(testPatient));

    }

    @Test
    public void TestRemovePatient() {
        PatientList testList = new PatientList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

        Patient testPatient = new Patient("test", "test", "test", new CareGiverList());
        testList.addPatient(testPatient);
        assertTrue("Size should be 1", testList.size() == 1);
        assertTrue("Does the list contain the object we added", testList.contains(testPatient));

        testList.removePatient(testPatient);
        assertTrue(testList.size() == 0);
        assertFalse(testList.contains(testPatient));


    }




}

package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;

public class CaregiverUnitTest {

    @Test
    public void TestAddCaregiver() {
        CareGiverList testList = new CareGiverList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

        CareGiver testCaregiver = new CareGiver("test", "test", "test","test", new PatientList());
        testList.addCareGiver(testCaregiver);
        assertTrue("Size should be 1", testList.size() == 1);

        assertTrue("Does the list contain the object we added", testList.contains(testCaregiver));

    }

    @Test
    public void TestRemoveCaregiver() {
        CareGiverList testList = new CareGiverList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

        CareGiver testCaregiver = new CareGiver("test", "test", "test","test", new PatientList());
        testList.addCareGiver(testCaregiver);
        assertTrue("Size should be 1", testList.size() == 1);
        assertTrue("Does the list contain the object we added", testList.contains(testCaregiver));

        testList.removeCareGiver(testCaregiver);
        assertTrue(testList.size() == 0);
        assertFalse(testList.contains(testCaregiver));


    }




}

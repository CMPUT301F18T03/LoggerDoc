package com.example.loggerdoc;


import org.junit.Test;

import static org.junit.Assert.*;

/** Made by Dylan on November 9, 2018 **/

public class UserListUnitTest {


    @Test
    public void TestAddPatient(){
        UserList testList = UserListController.getUserList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

        Patient testPatient = new Patient("test", "test", "test","role", new CareGiverList());
        testList.add_internal(testPatient);
        assertTrue("Size should be 1", testList.size() == 1);

        assertTrue("Does the list contain the object we added", testList.contains(testPatient));

        testList.remove(testPatient);

    }

    @Test
    public void TestRemovePatient(){
        UserList testList = UserListController.getUserList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

        Patient testPatient = new Patient("test", "test", "test","test", new CareGiverList());
        testList.add_internal(testPatient);
        assertTrue("Size should be 1", testList.size() == 1);

        assertTrue("Does the list contain the object we added", testList.contains(testPatient));

        testList.remove(testPatient);
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

    }

}

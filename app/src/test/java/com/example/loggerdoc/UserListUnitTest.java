package com.example.loggerdoc;


import org.junit.Test;

import static org.junit.Assert.*;

/** Made by Dylan on November 9, 2018 **/

public class UserListUnitTest {

    @Test
    public void TestAddUser(){
        UserList testList = UserListController.getUserList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

        User testUser = new User("test", "test", "test");
        testList.addUser(testUser);
        assertTrue("Size should be 1", testList.size() == 1);

        assertTrue("Does the list contain the object we added", testList.containsUser(testUser));

        testList.removeUser(testUser);

    }

    @Test
    public void TestAddPatient(){
        UserList testList = UserListController.getUserList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

        User testPatient = new Patient("test", "test", "test", new CareGiverList());
        testList.addUser(testPatient);
        assertTrue("Size should be 1", testList.size() == 1);

        assertTrue("Does the list contain the object we added", testList.containsUser(testPatient));

        testList.removeUser(testPatient);

    }

    @Test
    public void TestRemovePatient(){
        UserList testList = UserListController.getUserList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

        User testPatient = new Patient("test", "test", "test", new CareGiverList());
        testList.addUser(testPatient);
        assertTrue("Size should be 1", testList.size() == 1);

        assertTrue("Does the list contain the object we added", testList.containsUser(testPatient));

        testList.removeUser(testPatient);
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);

    }

}

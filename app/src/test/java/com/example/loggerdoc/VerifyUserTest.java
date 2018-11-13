package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;

public class VerifyUserTest {

    @Test
    public void TestVerifyUsername() {

        UserList testList = UserListController.getUserList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);
        Patient testUser0 = new Patient("test0", "test", "test", new CareGiverList());
        Patient testUser1 = new Patient("test1", "test", "test", new CareGiverList());
        testList.addUser(testUser0);
        testList.addUser(testUser1);


        assertTrue("Size should be 1", testList.size() == 2);

        for (User user : testList.getUsers()){
            if (user.getUserID() == "test2") {
                assertTrue("For loop should be returning correct user", testUser1 == user);
                break;
            }
        }

        testList.removeUser(testUser0);
        testList.removeUser(testUser1);

    }
}

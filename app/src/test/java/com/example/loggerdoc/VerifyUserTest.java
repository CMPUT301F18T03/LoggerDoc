package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;

public class VerifyUserTest {

    @Test
    public void TestVerifyUsername() {

        UserList testList = UserListController.getUserList();
        assertTrue("Size should be 0 since we have not added anything", testList.size() == 0);
        User testUser0 = new User("test0", "test", "test");
        User testUser1 = new User("test1", "test", "test");
        User testUser2 = new User("test2", "test", "test");
        User testUser3 = new User("test3", "test", "test");
        testList.addUser(testUser0);
        testList.addUser(testUser1);
        testList.addUser(testUser2);
        testList.addUser(testUser3);

        assertTrue("Size should be 1", testList.size() == 4);

        for (User user : testList.getUsers()){
            if (user.getUserID() == "test2") {
                assertTrue("For loop should be returning correct user", testUser2 == user);
                break;
            }
        }

        testList.removeUser(testUser0);
        testList.removeUser(testUser1);
        testList.removeUser(testUser2);
        testList.removeUser(testUser3);
    }
}

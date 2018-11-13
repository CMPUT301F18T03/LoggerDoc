package com.example.loggerdoc;

/** Created by Dylan on November 9th, 2018 **/

public class UserListController {

    private static UserList userList = null;

    static public UserList getUserList() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    public void addUser(User user) {
        getUserList().addUser(user);
    }

    public void removeUser(User user) {
        getUserList().removeUser(user);
    }

    public static boolean findUser(String id) {
        for (User user : userList.getUsers()) {
            if (user.getUserID().equals(id)) {
                return true;
            }
        }
        return false;
    }
}

package com.example.loggerdoc;

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


}

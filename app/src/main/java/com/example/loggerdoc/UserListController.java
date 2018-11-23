package com.example.loggerdoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Created by Dylan on November 9th, 2018 **/

public class UserListController {

    private static UserList userList = null;

    /**
     *
     * @return Returns the userList that the app will use. Lazy Singleton makes it so only one userList is ever created
     */
    static public UserList getUserList() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    public static void setList(UserList list) {
        getUserList().become(list);
    }

    public static ArrayList<Patient> getSpecificUserList(ArrayList<Integer> patients) {
        ArrayList<Patient> ret = new ArrayList<>();
        Map<Integer,Patient> userMap = new HashMap<>();
        for(User x : getUserList().getUsers()){
            userMap.put(x.getElasticID(),(Patient)x);
        }
        for(Integer tar : patients){
            ret.add(userMap.get(tar));
        }
        return ret;
    }

    public void addUser(User user) {
        getUserList().addUser(user);
    }

    public void removeUser(User user) {
        getUserList().removeUser(user);
    }

    /**
     *
     * @param id Unique username of the user that is being searched for
     * @return Returns True if user is in the userList, False if user is not in useList
     */
    public static boolean findUser(String id) {
        for (User user : userList.getUsers()) {
            if (user.getUserID().equals(id)) {
                return true;
            }
        }
        return false;
    }
}

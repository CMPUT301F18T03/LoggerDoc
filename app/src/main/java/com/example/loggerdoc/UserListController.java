package com.example.loggerdoc;

import android.util.SparseArray;
import java.util.ArrayList;


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

    public static void setList(ArrayList<User> list) {
        getUserList().load(list);
    }

    public static ArrayList<Patient> getSpecificUserList(ArrayList<Integer> patients) {
        ArrayList<Patient> ret = new ArrayList<>();

        SparseArray<Patient> userMap = new SparseArray<>();
        for(User x : getUserList().getArray()){
            userMap.put(x.getElasticID(),(Patient)x);
        }
        for(Integer tar : patients){
            ret.add(userMap.get(tar));
        }
        return ret;
    }

    /**
     *
     * @param id Unique username of the user that is being searched for
     * @return Returns True if user is in the userList, False if user is not in useList
     */
    public static boolean findUser(String id) {
        for (User user : userList.getArray()) {
            if (user.getUserID().equals(id)) {
                return true;
            }
        }
        return false;
    }
}



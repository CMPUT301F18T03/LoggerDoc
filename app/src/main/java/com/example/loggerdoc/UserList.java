/* Created 2018-11-01 by Alexandra Tyrrell
 *
 *  UserList represents a list of Users that have a profile with the app.
 *
 */
package com.example.loggerdoc;

import java.util.ArrayList;
import java.util.Collection;

public class UserList {

    protected ArrayList<User> userArrayList;

    public UserList(){
        userArrayList = new ArrayList<User>();
    }

    public Collection<User> getUsers() {
        return userArrayList;
    }

    public void addUser(User user){
        userArrayList.add(user);
    }

    public void removeUser (User user){
        userArrayList.remove(user);
    }

    public boolean containsUser (User user){
        return userArrayList.contains(user);
    }

    public int size(){
        return userArrayList.size();
    }
}

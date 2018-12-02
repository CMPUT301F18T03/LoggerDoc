/* Created 2018-11-01 by Alexandra Tyrrell
 *
 *  UserList represents a list of Users that have a profile with the app.
 *
 */
package com.example.loggerdoc;

import android.content.Context;
import com.example.loggerdoc.elasticclient.modifyUserTask;
import com.example.loggerdoc.elasticclient.removeUserTask;

import java.util.ArrayList;

public class UserList extends GenericList<User>{

    public void add(User data,Context context) {
        super.add_internal(data);
        new modifyUserTask(context).execute(data);
    }

    public void update(User data,Context context){
        add(data,context);
    }

    public ArrayList<User> getUsers(ArrayList<Integer> patientList) {
        ArrayList<User> ret = new ArrayList<>();
        for(Integer x: patientList){
            ret.add(datalist.get(x));
        }
        return ret;
    }

    public void remove(User data, Context context) {
        super.remove_internal(data);
        new removeUserTask(context).execute(data);
    }
}

package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.ElasticSearchController;
import com.example.loggerdoc.User;
import com.example.loggerdoc.UserList;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class uploadUsersTask extends AsyncTask<UserList, Void, Void> {
    private Context context;
    public uploadUsersTask(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(UserList... users) {
        //Because we for some reason take a list of userlists in....
        ArrayList<User> tosend =  users[0].getArray();
        Gson gson = new Gson();
        String jsonout;
        httphandler sender = ElasticSearchController.getHttpHandler();

        OutputStream fos;
        BufferedWriter out;
        for (User targ:tosend) {
            jsonout = gson.toJson(targ);
            sender.httpPUT("/user/_doc/"+targ.getElasticID().toString(),jsonout);
            try {

                fos = new FileOutputStream(new File(context.getFilesDir().getAbsolutePath()+"/Users/User"+targ.getElasticID()+".sav"));
                out = new BufferedWriter(new OutputStreamWriter(fos));
                out.write(jsonout);
                out.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    @Override
    protected void onPostExecute(Void v){
        context = null;
    }
}


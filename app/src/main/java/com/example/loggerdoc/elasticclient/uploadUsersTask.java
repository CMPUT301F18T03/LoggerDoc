package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.ElasticSearchController;
import com.example.loggerdoc.User;
import com.example.loggerdoc.UserList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
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
        ArrayList<User> tosend =  users[0].getUsers();
        Gson gson = new Gson();
        String jsonout;
        httphandler sender = ElasticSearchController.getHttpHandler();
        JSONArray store = new JSONArray();

        for (User targ:tosend) {
            jsonout = gson.toJson(targ);
            sender.httpPUT("/user/_doc/"+targ.getElasticID().toString(),jsonout);
            store.put(jsonout);
        }

        OutputStream fos = null;
        try {
            fos = context.openFileOutput("Users.sav", Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            JSONObject output = new JSONObject();
            try {
                output.put("Users",store);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            out.write(output.toString());

            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
    @Override
    protected void onPostExecute(Void v){
        context = null;
    }
}

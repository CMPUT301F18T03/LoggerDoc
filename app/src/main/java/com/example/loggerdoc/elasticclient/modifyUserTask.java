package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.ElasticSearchController;
import com.example.loggerdoc.User;
import com.example.loggerdoc.UserList;
import com.example.loggerdoc.UserListController;
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

public class modifyUserTask extends AsyncTask<User, Void, Void> {
    private Context context;
    public modifyUserTask(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(User... users) {
        User tosend =  users[0];

        Gson gson = new Gson();
        String jsonout;
        httphandler sender = ElasticSearchController.getHttpHandler();
        jsonout = gson.toJson(tosend);
        sender.httpPUT("/user/_doc/"+tosend.getElasticID().toString(),jsonout);

        JSONArray store = new JSONArray();
        ArrayList<User> allusers = UserListController.getUserList().getUsers();
        for (User targ:allusers) {
            jsonout = gson.toJson(targ);
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

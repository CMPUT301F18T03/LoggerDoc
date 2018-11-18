package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.CareGiver;
import com.example.loggerdoc.ElasticSearchController;
import com.example.loggerdoc.Patient;
import com.example.loggerdoc.UserList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class getUsersTask extends AsyncTask<Void, Void,UserList> {
    private Context context;
    private ElasticDataCallback<UserList> callback;
    public getUsersTask(Context context){
        this.context = context;
    }
    public getUsersTask(Context context,ElasticDataCallback<UserList> callback){
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected UserList doInBackground(Void... voids) {
        httphandler receiver = ElasticSearchController.getHttpHandler();
        Gson gson = new Gson();
        UserList ret = new UserList();
        String jsonin = receiver.httpGET("/user/_doc/_search?q=*:*&filter_path=hits.hits.*");
        if(jsonin == null){
            InputStream fis;
            try {
                fis = context.openFileInput("Users.sav");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            try {
                JSONObject input = new JSONObject(in.toString());
                JSONArray inputdata = input.getJSONArray("Users");
                for(int num = 0; num < inputdata.length();num++){
                    JSONObject currentuser = inputdata.getJSONObject(num).getJSONObject("_source");
                    if(currentuser.has("problems")){
                        ret.addUser(gson.fromJson(currentuser.toString(),Patient.class));
                    }
                    else if(currentuser.has("patients")){
                        ret.addUser(gson.fromJson(currentuser.toString(),CareGiver.class));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return ret;
        }
        try {
            JSONArray hits = new JSONObject(jsonin).getJSONObject("hits").getJSONArray("hits");
            for(int num = 0; num < hits.length();num++){
                JSONObject currentuser = hits.getJSONObject(num).getJSONObject("_source");
                if(currentuser.has("problems")){
                    ret.addUser(gson.fromJson(currentuser.toString(),Patient.class));
                }
                else if(currentuser.has("patients")){
                    ret.addUser(gson.fromJson(currentuser.toString(),CareGiver.class));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    protected void onPostExecute(UserList x){
        context = null;
        if(callback != null){
            callback.dataCallBack(x);
        }

    }
}

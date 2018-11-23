package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.loggerdoc.CareGiver;
import com.example.loggerdoc.ElasticSearchController;
import com.example.loggerdoc.Patient;
import com.example.loggerdoc.UserList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
        String jsonin = receiver.httpGET("/user/_doc/_search?q=*:*&filter_path=hits.hits.*&size=10000");
        if(jsonin != null){
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
        else {
            File datafile = new File(context.getFilesDir().getAbsolutePath()+"/Users/");
            if(datafile.exists()){
                File[] users = datafile.listFiles();
                BufferedReader in;
                for (File userdata: users){
                    try {
                        in = new BufferedReader(new FileReader(userdata));
                        JSONObject currentuser = new JSONObject(in.readLine());
                        if(currentuser.has("problems")){
                            ret.addUser(gson.fromJson(currentuser.toString(),Patient.class));
                        }
                        else if(currentuser.has("patients")){
                            ret.addUser(gson.fromJson(currentuser.toString(),CareGiver.class));
                        }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();//literally impossible
                    }
                }
                return ret;
            }
            else{
                //Complains that no one cares if the folder is actually made.
                datafile.mkdir();
                new File(context.getFilesDir().getAbsolutePath()+"/Records/").mkdir();
                new File(context.getFilesDir().getAbsolutePath()+"/Problems/").mkdir();
                new File(context.getFilesDir().getAbsolutePath()+"/Data/").mkdir();
                return null;

            }

        }

    }
    public void mkDirs(){
        File datafile = new File(context.getFilesDir().getAbsolutePath()+"/Users/");
        if(!datafile.exists()) {
            datafile.mkdir();
            new File(context.getFilesDir().getAbsolutePath()+"/Records/").mkdir();
            new File(context.getFilesDir().getAbsolutePath()+"/Problems/").mkdir();
            new File(context.getFilesDir().getAbsolutePath()+"/Data/").mkdir();
        }
    }
    @Override
    protected void onPostExecute(UserList x){
        context = null;
        if(callback != null){
            callback.dataCallBack(x);
        }

    }
}

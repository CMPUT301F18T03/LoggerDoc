package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.SparseArray;

import com.example.loggerdoc.CareGiver;
import com.example.loggerdoc.ElasticSearchController;
import com.example.loggerdoc.Patient;
import com.example.loggerdoc.User;
import com.example.loggerdoc.UserList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class getUsersTask extends AsyncTask<Void, Void,ArrayList<User>> {
    private Context context;
    private ElasticDataCallback<ArrayList<User>> callback;
    public getUsersTask(Context context){
        this.context = context;
    }

    public getUsersTask(Context context,ElasticDataCallback<ArrayList<User>> callback){
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected ArrayList<User> doInBackground(Void... voids) {
        httphandler receiver = ElasticSearchController.getHttpHandler();
        Gson gson = new Gson();
        ArrayList<User> ret = new ArrayList<>();
        String jsonin = receiver.httpGET("/user/_doc/_search?q=*:*&filter_path=hits.hits.*&size=10000");
        if(jsonin != null){
            try {
                JSONArray hits = new JSONObject(jsonin).getJSONObject("hits").getJSONArray("hits");
                for(int num = 0; num < hits.length();num++){
                    JSONObject currentuser = hits.getJSONObject(num).getJSONObject("_source");
                    if(currentuser.has("problems")){
                        ret.add(gson.fromJson(currentuser.toString(),Patient.class));
                    }
                    else if(currentuser.has("patients")){
                        ret.add(gson.fromJson(currentuser.toString(),CareGiver.class));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            OutputStream fos;
            BufferedWriter out;
            String jsonout;
            for (User targ:ret) {
                jsonout = gson.toJson(targ);
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
                            ret.add(gson.fromJson(currentuser.toString(),Patient.class));
                        }
                        else if(currentuser.has("patients")){
                            ret.add(gson.fromJson(currentuser.toString(),CareGiver.class));
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
                new File(context.getFilesDir().getAbsolutePath()+"/Uploads/").mkdir();
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
            new File(context.getFilesDir().getAbsolutePath()+"/Uploads/").mkdir();
        }
    }
    @Override
    protected void onPostExecute(ArrayList<User> x){
        context = null;
        if(callback != null){
            callback.dataCallBack(x);
        }

    }
}




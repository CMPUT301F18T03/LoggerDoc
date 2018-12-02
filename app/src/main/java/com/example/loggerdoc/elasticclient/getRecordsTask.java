package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.Record;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Class used to asynchronously get records from elasticsearch if there is server
 * connectivity and store the records into an arraylist of records usable by the
 * application. If nothing is received from the server, check memory instead
 */
public class getRecordsTask extends AsyncTask<Integer, Void,ArrayList<Record>> {
    private Context context;
    private ElasticDataCallback<ArrayList<Record>> callback;
    public getRecordsTask(Context context){
        this.context = context;
    }

    public getRecordsTask(Context context,ElasticDataCallback<ArrayList<Record>> callback){
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected ArrayList<Record> doInBackground(Integer... Integers) {
        ElasticSearchController.getCacheClient().sendCache(context);
        Integer EID = Integers[0];
        httphandler receiver = ElasticSearchController.getHttpHandler();
        Gson gson = new Gson();
        ArrayList<Record> ret = new ArrayList<>();

        //get records from server and store into jsonin string
        String jsonin = receiver.httpGET("/record/_doc/_search?q=ElasticID_Owner:"+ EID.toString()+"&filter_path=hits.hits.*&size=10000");
        if(jsonin != null){
            //convert the jsonin string to records and add to arraylist of records to return
            try {
                JSONArray hits = new JSONObject(jsonin).getJSONObject("hits").getJSONArray("hits");
                for(int num = 0; num < hits.length();num++){
                    JSONObject currentrecord = hits.getJSONObject(num).getJSONObject("_source");
                    ret.add(gson.fromJson(currentrecord.toString(),Record.class));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ret;
        }

        // if nothing is received from server, read from device memory instead
        else {
            //check if record parent directory exists
            File datafile = new File(context.getFilesDir().getAbsolutePath()+"/Records/");
            if(datafile.exists()){
                File problems = new File(context.getFilesDir().getAbsolutePath()+"/Records/records"+EID.toString()+".sav");
                try {
                    /*read from the wanted record file and convert Json to record objects, add record
                     *objects to return arraylist
                     */
                    BufferedReader in = new BufferedReader(new FileReader(problems));
                    String linein = in.readLine();
                    while(linein != null){
                        JSONObject currentrecord = new JSONObject(linein);
                        ret.add(gson.fromJson(currentrecord.toString(),Record.class));
                        linein = in.readLine();
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return ret;
            }
            /*if parent directory is not created, then create it and return null as there are no
             *records to add to the list to return
             */
            else{
                new File(context.getFilesDir().getAbsolutePath()+"/Records/").mkdir();
                return null;

            }

        }

    }

    //method called upon completion of the asynchronous task
    @Override
    protected void onPostExecute(ArrayList<Record> x){
        context = null;
        if(callback != null){
            callback.dataCallBack(x);
        }

    }
}

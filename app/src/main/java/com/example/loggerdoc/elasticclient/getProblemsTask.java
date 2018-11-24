package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;
import com.example.loggerdoc.ElasticSearchController;
import com.example.loggerdoc.Problem;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class getProblemsTask extends AsyncTask<Integer, Void,ArrayList<Problem>> {
    private Context context;
    private ElasticDataCallback<ArrayList<Problem>> callback;
    public getProblemsTask(Context context){
        this.context = context;
    }

    public getProblemsTask(Context context,ElasticDataCallback<ArrayList<Problem>> callback){
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected ArrayList<Problem> doInBackground(Integer... Integers) {

        Integer EID = Integers[0];
        httphandler receiver = ElasticSearchController.getHttpHandler();
        Gson gson = new Gson();
        ArrayList<Problem> ret = new ArrayList<>();
        String jsonin = receiver.httpGET("/problem/_doc/_search?q=ElasticID_Owner:"+ EID.toString()+"&filter_path=hits.hits.*&size=10000");
        if(jsonin != null){
            try {
                JSONArray hits = new JSONObject(jsonin).getJSONObject("hits").getJSONArray("hits");
                for(int num = 0; num < hits.length();num++){
                    JSONObject currentproblem = hits.getJSONObject(num).getJSONObject("_source");
                    ret.add(gson.fromJson(currentproblem.toString(),Problem.class));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ret;
        }
        else {
            File datafile = new File(context.getFilesDir().getAbsolutePath()+"/Problems/");
            if(datafile.exists()){
                File problems = new File(context.getFilesDir().getAbsolutePath()+"/Problems/problems"+EID.toString()+".sav");
                try {
                    BufferedReader in = new BufferedReader(new FileReader(problems));
                    String linein = in.readLine();
                    while(linein != null){
                        JSONObject currentproblem = new JSONObject(linein);
                        ret.add(gson.fromJson(currentproblem.toString(),Problem.class));
                        linein = in.readLine();
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return ret;
            }
            else{
                new File(context.getFilesDir().getAbsolutePath()+"/Problems/").mkdir();
                return null;

            }

        }

    }

    @Override
    protected void onPostExecute(ArrayList<Problem> x){
        context = null;
        if(callback != null){
            callback.dataCallBack(x);
        }

    }
}


package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.Bodylocation;
import com.example.loggerdoc.Record;
import com.example.loggerdoc.RecordGeoLocation;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class searchRecordsTask extends AsyncTask<Integer, Void,ArrayList<Integer>> {
    private Context context;
    private ElasticDataCallback<ArrayList<Integer>> callback;
    private String keywords;
    private RecordGeoLocation targgeo;
    private Bodylocation targloc;
    private Integer problemID;

    private final String querya = "{\"query\":{\"multi_match\":{\"query\":\"";
    private final String queryb = "\",\"fields\":[\"title\", \"description\"],\"operator\":\"and\"}}}";


    public searchRecordsTask(Context context, ElasticDataCallback<ArrayList<Integer>> callback, String keywords, RecordGeoLocation targgeo, Bodylocation targloc,Integer problemID){
        this.context = context;
        this.callback = callback;
        this.keywords = keywords;
        this.targgeo = targgeo;
        this.targloc = targloc;
        this.problemID = problemID;
    }


    /*
     * This method contains the process used in the background to search the elastic search server
     * for Records.
     */
    @Override
    protected ArrayList<Integer> doInBackground(Integer... Integers) {
        ElasticSearchController.getCacheClient().sendCache(context);
        Integer EID = Integers[0];
        httphandler receiver = ElasticSearchController.getHttpHandler();
        Gson gson = new Gson();
        ArrayList<Integer> ret = new ArrayList<>();


        // get a string representing Records from the server
        String jsonin = receiver.httpPOST("/record/_doc/_search",
                querya + keywords + queryb);

        //if string is successfully returned from server
        if (jsonin != null) {
            try {
                JSONArray hits = new JSONObject(jsonin).getJSONObject("hits").getJSONArray("hits");

                //add problems returned from elastic search to return problem arraylist
                for (int num = 0; num < hits.length(); num++) {
                    JSONObject currentproblem = hits.getJSONObject(num).getJSONObject("_source");
                    Record x = gson.fromJson(currentproblem.toString(), Record.class);
                    if(x.getElasticID_Owner().equals(EID) && x.getElasticID_OwnerProblem().equals(problemID)) ret.add(x.getElasticID());

                }
                return ret;


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }
        return null;


    }


    /*
     * This method is called once an asynchronous doInBackground task finishes
     * and returns the arraylist
     */
    @Override
    protected void onPostExecute(ArrayList<Integer> x){
        context = null;
        if(callback != null){
            callback.dataCallBack(x);
        }

    }
}

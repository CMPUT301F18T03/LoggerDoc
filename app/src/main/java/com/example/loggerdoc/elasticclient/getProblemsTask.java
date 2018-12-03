package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.Problem;
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
/*
 * This class defines the asynchronous method used to read problems from the elastic search server
 * and return them in a list of problems.
 */
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


    /*
     * This method contains the process used in the background to read in problems from the server
     * and write them to device memory, as well as adding them to an arraylist of problems that is
     * returned. If nothing is returned from the server, device memory is used to add problems to
     * the problem list
     */
    @Override
    protected ArrayList<Problem> doInBackground(Integer... Integers) {
        ElasticSearchController.getCacheClient().sendCache(context);
        Integer EID = Integers[0];
        httphandler receiver = ElasticSearchController.getHttpHandler();
        Gson gson = new Gson();
        ArrayList<Problem> ret = new ArrayList<>();

        // get a string representing problems from the server
        String jsonin = receiver.httpGET("/problem/_doc/_search?q=ElasticID_Owner:"+ EID.toString()+"&filter_path=hits.hits.*&size=10000");

        //if string is successfully returned from server
        if(jsonin != null){
            try {
                JSONArray hits = new JSONObject(jsonin).getJSONObject("hits").getJSONArray("hits");

                //add problems returned from elastic search to return problem arraylist
                for(int num = 0; num < hits.length();num++){
                    JSONObject currentproblem = hits.getJSONObject(num).getJSONObject("_source");
                    ret.add(gson.fromJson(currentproblem.toString(),Problem.class));
                }

                OutputStream fos;
                BufferedWriter out;

                //define path to write problems to
                fos = new FileOutputStream(new File(context.getFilesDir().getAbsolutePath()+"/Problems/problem"+EID.toString()+".sav"));
                out = new BufferedWriter(new OutputStreamWriter(fos));

                //write problems to device memory at the path defined earlier
                for(Problem x:ret){
                    out.write(gson.toJson(x));
                    out.newLine();
                }
                out.flush();
                fos.close();

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return ret;

        }
        // string not properly returned from server, load in problems from device memory
        else {
            //check if parent problems file exists
            File datafile = new File(context.getFilesDir().getAbsolutePath()+"/Problems/");
            if(datafile.exists()){

                //get file with problems, read file and add problems to return problem arraylist
                File problems = new File(context.getFilesDir().getAbsolutePath()+"/Problems/problem"+EID.toString()+".sav");
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
            //parent problem file doesnt exist, create the file
            else{
                new File(context.getFilesDir().getAbsolutePath()+"/Problems/").mkdir();
                return null;

            }
        }

    }


    /*
     * This method is called once an asynchronous doInBackground task finishes
      * and returns the arraylist
     */
    @Override
    protected void onPostExecute(ArrayList<Problem> x){
        context = null;
        if(callback != null){
            callback.dataCallBack(x);
        }

    }
}



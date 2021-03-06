package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.Problem;
import com.example.loggerdoc.ProblemRecordListController;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/*
 * Class used to create asynchronous task to remove a problem from the server and from
 * device memory
 */

public class removeProblemTask extends AsyncTask<Problem, Void, Void> {
    private Context context;

    public removeProblemTask(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Problem... problems) {
        Gson gson = new Gson();//new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        Problem tosend = problems[0];
        ArrayList<Problem> ret = ProblemRecordListController.getProblemList().getArray();
        httphandler sender = ElasticSearchController.getHttpHandler();
        OutputStream fos;
        BufferedWriter out;
        ElasticSearchController.getCacheClient().sendCache(context);

        // delete problem from server, if server does not respond then delete from cache
        String serverResponse = sender.httpDELETE("/problem/_doc/"+tosend.getElasticID().toString());
        if(serverResponse == null){
            ElasticSearchController.getCacheClient().cacheToDelete("/problem/_doc/",tosend.getElasticID(),context);
        }

        /*
         * Write the updated problem list to memory at the specified path. This list no longer
         * contains the problem that is being removed.
         */
        try {
            fos = new FileOutputStream(new File(context.getFilesDir().getAbsolutePath()+"/Problems/problem"+tosend.getElasticID_Owner().toString()+".sav"));
            out = new BufferedWriter(new OutputStreamWriter(fos));
            for(Problem x:ret){
                out.write(gson.toJson(x));
                out.newLine();
            }
            out.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();

        }


        return null;
    }

    //method that is called when asynchronous task is completed
    @Override
    protected void onPostExecute(Void v){
        context = null;
    }
}

package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

public class modifyProblemTask extends AsyncTask<Problem, Void, Void> {
    private Context context;
    public modifyProblemTask(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(Problem... problems) {
        Gson gson = new Gson();//new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        String jsonout;
        Problem tosend = problems[0];
        ArrayList<Problem> ret = ProblemRecordListController.getProblemList().getArray();
        httphandler sender = ElasticSearchController.getHttpHandler();
        OutputStream fos;
        BufferedWriter out;
        jsonout = gson.toJson(tosend);
        ElasticSearchController.getCacheClient().sendCache(context);
        String serverResponse = sender.httpPUT("/problem/_doc/"+tosend.getElasticID().toString(),jsonout);
        if(serverResponse == null){
            ElasticSearchController.getCacheClient().cacheToSend("/problem/_doc/"+tosend.getElasticID().toString(),jsonout,context);
        }

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



        Log.d ("The problem date is in the modifyProblemTask", problems[0].getTimestamp().toString());
        Log.d ("The jsonout value is", jsonout);

        return null;
    }
    @Override
    protected void onPostExecute(Void v){
        context = null;
    }
}



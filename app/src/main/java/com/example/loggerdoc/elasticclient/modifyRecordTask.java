package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.ProblemRecordListController;
import com.example.loggerdoc.Record;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/*
 * Class used to asynchronously handle when a record list is modified. Updates ElasticSearch and then
 * writes to device memory. If no response is received from the ElasticSearch server,
 * write to cache instead
 */

public class modifyRecordTask extends AsyncTask<Record, Void, Void> {
    private Context context;
    public modifyRecordTask(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(Record... records) {
        Gson gson = new Gson();
        String jsonout;
        Record tosend = records[0];
        ArrayList<Record> ret = ProblemRecordListController.getRecordList().getArray();
        httphandler sender = ElasticSearchController.getHttpHandler();
        OutputStream fos;
        BufferedWriter out;
        jsonout = gson.toJson(tosend);
        ElasticSearchController.getCacheClient().sendCache(context);

        //send to elasticSearch server
        String serverResponse = sender.httpPUT("/record/_doc/"+tosend.getElasticID().toString(),jsonout);

        //if no response received, write to cache instead
        if(serverResponse == null){
            ElasticSearchController.getCacheClient().cacheToSend("/record/_doc/"+tosend.getElasticID().toString(),jsonout,context);
        }

        //write to memory at the path for record storage
        try {

            fos = new FileOutputStream(new File(context.getFilesDir().getAbsolutePath()+"/Records/records"+tosend.getElasticID_Owner().toString()+".sav"));
            out = new BufferedWriter(new OutputStreamWriter(fos));
            for(Record x:ret){
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

    //called after asynchronous task is complete
    @Override
    protected void onPostExecute(Void v){
        context = null;
    }
}





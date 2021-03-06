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
 * Class used to create asynchronous task to remove a record from the server and from
 * device memory
 */

public class removeRecordTask extends AsyncTask<Record, Void, Void> {
    private Context context;

    public removeRecordTask(Context context){
        this.context = context;
    }
    @Override

    protected Void doInBackground(Record... records) {
        Gson gson = new Gson();
        Record tosend = records[0];
        ArrayList<Record> ret = ProblemRecordListController.getRecordList().getArray();
        httphandler sender = ElasticSearchController.getHttpHandler();
        OutputStream fos;
        BufferedWriter out;
        ElasticSearchController.getCacheClient().sendCache(context);

        //delete a record from the server. If the server does not respond, delete from cache instead
        String serverResponse = sender.httpDELETE("/record/_doc/"+tosend.getElasticID().toString());
        if(serverResponse == null){
            ElasticSearchController.getCacheClient().cacheToDelete("/record/_doc/",tosend.getElasticID(),context);
        }
        try {
            /*
             * Write the updated record list to memory at the specified path. This list no longer
             * contains the record that is being removed.
             */
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

    //method that is called when asynchronous task is completed
    @Override
    protected void onPostExecute(Void v){
        context = null;
    }
}

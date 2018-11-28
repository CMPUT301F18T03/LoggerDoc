package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.ElasticSearchController;
import com.example.loggerdoc.Problem;
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
        //File datafile = new File(context.getFilesDir().getAbsolutePath()+"/Problems/");
        ArrayList<Record> ret = ProblemRecordListController.getRecordList().getArray();
        httphandler sender = ElasticSearchController.getHttpHandler();
        OutputStream fos;
        BufferedWriter out;
        jsonout = gson.toJson(tosend);
        sender.httpPUT("/record/_doc/"+tosend.getElasticID().toString(),jsonout);

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
    @Override
    protected void onPostExecute(Void v){
        context = null;
    }
}

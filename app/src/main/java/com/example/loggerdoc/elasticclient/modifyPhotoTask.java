package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.BodyLocationPhoto;
import com.example.loggerdoc.ProblemRecordListController;
import com.example.loggerdoc.Record;
import com.example.loggerdoc.RecordPhoto;
import com.example.loggerdoc.RecordPhotoList;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class modifyPhotoTask extends AsyncTask<RecordPhoto, Void, Void> {
    private Context context;
    public modifyPhotoTask(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(RecordPhoto... records) {
        Gson gson = new Gson();
        String jsonout;
        RecordPhoto tosend = records[0];
        httphandler sender = ElasticSearchController.getHttpHandler();
        photodata data  = new photodata();

        try {
            data.img =  new Scanner(tosend.getPhoto()).useDelimiter("\\Z").next();
            data.ElasticID = tosend.getElasticID();
            data.ElasticID_OwnerRecord = tosend.getElasticID_OwnerRecord();
            if(tosend.getClass() == BodyLocationPhoto.class){
                //Now this is podracing!
                data.lbl = ((BodyLocationPhoto)tosend).getLabel();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String serverResponse;

        /*
        jsonout = gson.toJson(tosend);
        ElasticSearchController.getCacheClient().sendCache(context);
        serverResponse = sender.httpPUT("/photo/_doc/"+tosend.getElasticID().toString(),jsonout);
        if(serverResponse == null){
            ElasticSearchController.getCacheClient().cacheToSend("/photo/_doc/"+tosend.getElasticID().toString(),jsonout,context);
        }*/

        jsonout = gson.toJson(data);
        serverResponse = sender.httpPUT("/photodata/_doc/"+tosend.getElasticID().toString(),jsonout);
        if(serverResponse == null){
            ElasticSearchController.getCacheClient().cacheToSend("/photodata/_doc/"+tosend.getElasticID().toString(),jsonout,context);
        }

        try {
            OutputStream fos;
            BufferedWriter out;
            fos = new FileOutputStream(new File(context.getFilesDir().getAbsolutePath()+"/Data/"+tosend.getElasticID().toString()+".dat"));
            out = new BufferedWriter(new OutputStreamWriter(fos));
            out.write(gson.toJson(data));
            out.flush();
            fos.close();
            fos = new FileOutputStream(new File(context.getFilesDir().getAbsolutePath()+"/Data/"+tosend.getElasticID().toString()+".jpg"));
            out = new BufferedWriter(new OutputStreamWriter(fos));
            out.write(data.img);
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

    /**
     * A handy box to stuff into elastic search
     */
    class photodata{
        public String img;
        public Integer ElasticID;
        public Integer ElasticID_OwnerRecord;
        public String lbl;
    }
}



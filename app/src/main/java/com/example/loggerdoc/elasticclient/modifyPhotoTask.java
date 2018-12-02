package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;
import java.util.Base64;

import com.example.loggerdoc.BodyLocationPhoto;
import com.example.loggerdoc.RecordPhoto;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;

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
            data.img = new String(Base64.getEncoder().encode(Files.readAllBytes(tosend.getPhoto().toPath())));
            data.ElasticID = tosend.getElasticID();
            data.ElasticID_OwnerRecord = tosend.getElasticID_OwnerRecord();
            if(tosend.getClass() == BodyLocationPhoto.class){
                //Now this is podracing!
                data.lbl = ((BodyLocationPhoto)tosend).getLabel();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String serverResponse;

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
            fos.write(Base64.getDecoder().decode(data.img));
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



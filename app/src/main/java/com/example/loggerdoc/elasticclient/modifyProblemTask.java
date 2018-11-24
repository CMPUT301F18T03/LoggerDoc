package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.ElasticSearchController;
import com.example.loggerdoc.User;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class modifyProblemTask extends AsyncTask<User, Void, Void> {
    private Context context;
    public modifyProblemTask(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(User... users) {
        Gson gson = new Gson();
        String jsonout;
        httphandler sender = ElasticSearchController.getHttpHandler();
        OutputStream fos;
        BufferedWriter out;
        for(User tosend : users){
            jsonout = gson.toJson(tosend);
            sender.httpPUT("/user/_doc/"+tosend.getElasticID().toString(),jsonout);
            try {

                fos = new FileOutputStream(new File(context.getFilesDir().getAbsolutePath()+"/Users/User"+tosend.getElasticID()+".sav"));
                out = new BufferedWriter(new OutputStreamWriter(fos));
                out.write(jsonout);
                out.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }



        return null;
    }
    @Override
    protected void onPostExecute(Void v){
        context = null;
    }
}

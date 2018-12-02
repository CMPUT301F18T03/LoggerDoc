package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.User;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/*
 * Class used to asynchronously handle when a user list is modified. Updates ElasticSearch and then
 * writes to device memory. If no response is received from the ElasticSearch server,
 * write to cache instead
 */

public class modifyUserTask extends AsyncTask<User, Void, Void> {
    private Context context;

    public modifyUserTask(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(User... users) {
        Gson gson = new Gson();
        String jsonout;
        httphandler sender = ElasticSearchController.getHttpHandler();
        OutputStream fos;
        BufferedWriter out;
        ElasticSearchController.getCacheClient().sendCache(context);

        //send each user to elasticSearch
        for(User tosend : users){
            jsonout = gson.toJson(tosend);
            String serverResponse = sender.httpPUT("/user/_doc/"+tosend.getElasticID().toString(),jsonout);

            //if no response is received from the server, write to cache instead
            if(serverResponse == null){
                ElasticSearchController.getCacheClient().cacheToSend("/user/_doc/"+tosend.getElasticID().toString(),jsonout,context);
            }

            //write to memory at the directory used to store the updated user
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

    //called after asynchronous task is complete
    @Override
    protected void onPostExecute(Void v){
        context = null;
    }
}



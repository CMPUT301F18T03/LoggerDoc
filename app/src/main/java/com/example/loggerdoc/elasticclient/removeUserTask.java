package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;
import com.example.loggerdoc.User;
import com.example.loggerdoc.UserListController;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;

public class removeUserTask extends AsyncTask<User, Void, Void> {
    private Context context;
    public removeUserTask(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(User... users) {
        Gson gson = new Gson();
        User tosend = users[0];
        ArrayList<User> ret = UserListController.getUserList().getArray();
        httphandler sender = ElasticSearchController.getHttpHandler();
        OutputStream fos;
        BufferedWriter out;
        ElasticSearchController.getCacheClient().sendCache(context);
        String serverResponse = sender.httpDELETE("/user/_doc/"+tosend.getElasticID().toString());
        if(serverResponse == null){
            ElasticSearchController.getCacheClient().cacheToDelete("/user/_doc/",tosend.getElasticID(),context);
        }

        File file = new File(context.getFilesDir().getAbsolutePath()+"/Users/user"+tosend.getElasticID()+".sav");
        file.delete();

        return null;
    }
    @Override
    protected void onPostExecute(Void v){
        context = null;
    }
}

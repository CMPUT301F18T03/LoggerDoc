package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.CareGiver;
import com.example.loggerdoc.ElasticSearchController;
import com.example.loggerdoc.Patient;
import com.example.loggerdoc.User;
import com.example.loggerdoc.UserList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;

public class ElasticCacheClient {
    private httphandler handler;
    private ReentrantLock lock = new ReentrantLock();

    public ElasticCacheClient(String host, OkHttpClient client) {
        handler = new httphandler(client,host);
    }

    /**
     * Attempt to send any data in the Elastic Cache
     * @param context a context to use to open files with.
     */
    public void sendCache(Context context) {
        lock.lock();
        File datafile = new File(context.getFilesDir().getAbsolutePath()+"/Data/Cache.sav");
        if(!datafile.exists()){
            lock.unlock();
            return;
        }
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(datafile));
            String Line = in.readLine();
            String address;
            String data;
            String returnval;
            while(Line != null && !Line.equals("")){
                if(Line.substring(0,1).equals("s")){
                    address = Line.substring(1);
                    data = in.readLine();
                    returnval = handler.httpPUT(address,data);
                }
                else if(Line.substring(0,1).equals("d")){
                    address = Line.substring(1);
                    data = in.readLine();
                    returnval = handler.httpDELETE(address+data);
                }
                else{
                    //panic
                    lock.unlock();
                    return;
                }
                if(returnval != null){
                    Line = in.readLine();
                }
                else{
                    //If we fail half way, we read in the rest of the cache, and rewrite it to disk.
                    StringBuilder failed = new StringBuilder();
                    failed.append(Line).append("\n").append(data).append("\n");
                    Line = in.readLine();
                    while(Line != null){
                        failed.append(Line).append("\n");
                        Line = in.readLine();
                    }
                    in.close();
                    OutputStream fos;
                    BufferedWriter out;
                    fos = new FileOutputStream(datafile);
                    out = new BufferedWriter(new OutputStreamWriter(fos));
                    out.write(failed.toString());
                    out.flush();
                    fos.close();
                    lock.unlock();
                    return;

                }
            }
            datafile.delete();

        } catch ( IOException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }


    }

    /**
     * Adds an object to the cache, that will be fowarded to the server as soon as possible
     * @param jsonout the json string to add
     * @param context a context to open files with.
     */
    void cacheToSend(String path,String jsonout, Context context) {
        cache(path,jsonout,context,"s");
    }

    void cacheToDelete(String path,Integer toDelete,Context context){
        cache(path,toDelete.toString(),context,"d");
    }

    /**
     * Actually saves the cache to disk
     * @param path The string to be saved to disk. Normally an address on the elastic server
     * @param jsonout the string to be saved to disk. Normally a json string to send to elastic search
     * @param context a context to open files with.
     * @param ident A symbole that is used to decide the action taken per line
     */
    private void cache(String path, String jsonout, Context context, String ident){
        File savefile = new File(context.getFilesDir().getAbsolutePath()+"/Data/Cache.sav");
        OutputStream fos;
        BufferedWriter out;
        if(savefile.exists()){
            try {
                fos = new FileOutputStream(savefile,true);
                out = new BufferedWriter(new OutputStreamWriter(fos));
                out.write(ident+ path + "\n" +jsonout + "\n");
                out.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                fos = new FileOutputStream(savefile);
                out = new BufferedWriter(new OutputStreamWriter(fos));
                out.write(ident+ path + "\n" + jsonout + "\n");
                out.flush();
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


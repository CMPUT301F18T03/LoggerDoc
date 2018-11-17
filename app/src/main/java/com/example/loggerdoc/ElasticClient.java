package com.example.loggerdoc;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

class ElasticClient {
    //private OkHttpClient client;
    //private String host;
    private httphandler handler;
    private final String endpointstr = "/test/";


    ElasticClient(String host,OkHttpClient client) {
        //this.host = host;
        //this.client = client;
        handler = new httphandler(client,host);
    }

    public String getrecord(String address){
        String x = handler.httpGET(endpointstr + address);

        Gson gson = new Gson(); // Library to save objects

        searchResponse values = gson.fromJson(x, searchResponse.class);
        if(values.found){
            return values._source.toString();
        }
        return null;

    }
    public String rawhttpPUT(String address,String payload){
        return handler.httpPUT(endpointstr+address,payload);
    }
    public searchResponse eSearch(String address){
        String x = handler.httpGET(endpointstr + address);

        Gson gson = new Gson(); // Library to save objects

        return gson.fromJson(x, searchResponse.class);

    }
    public static class uploadUsersTask extends AsyncTask<UserList, Void, Void> {


        @Override
        protected Void doInBackground(UserList... users) {
            //Because we for some reason take a list of userlists in....
            ArrayList<User> tosend =  users[0].getUsers();
            Gson gson = new Gson();
            String jsonout;
            httphandler sender = ElasticSearchController.getHttpHandler();

            for (User targ:tosend) {
                jsonout = gson.toJson(targ);
                sender.httpPUT("/user/_doc/"+targ.getElasticID().toString(),jsonout);
            }
            return null;
        }
    }
    public class basicResponse{
        public String _index;
        public String _type;
        public String _id;
    }
    public class searchResponse extends basicResponse{
        public boolean found;
        public String _version;
        public JsonObject _source;

    }
    private class blankResponse {
        public String name;
        public String cluster_name;
        public String cluster_uuid;
        public String version;
    }
}



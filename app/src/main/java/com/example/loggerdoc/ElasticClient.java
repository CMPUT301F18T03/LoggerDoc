package com.example.loggerdoc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
    /*public String getElasticVersion(){
        Request request = new Request.Builder()
                .url(host)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();

            ResponseBody x = response.body();
            return x.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
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



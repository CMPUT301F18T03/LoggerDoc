package com.example.loggerdoc.elasticclient;

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

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class ElasticClient {
    private httphandler handler;

    public ElasticClient(String host,OkHttpClient client) {
        handler = new httphandler(client,host);
    }



    /*public class basicResponse{
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
    }*/
}


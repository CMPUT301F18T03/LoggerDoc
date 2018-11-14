package com.example.loggerdoc;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

class ElasticClient {
    private String host;
    ElasticClient(String host) {
        this.host = host;
    }
    public TreeMap httpGET(String data){
        HttpURLConnection conn;
        TreeMap result;
        try {
            URL targ = new URL(host + data);
            conn = (HttpURLConnection) targ.openConnection();
            InputStreamReader inreader = new InputStreamReader(conn.getInputStream());
            result = new Gson().fromJson(inreader, TreeMap.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;


    }

    public String getElasticVersion(){
        /*HttpURLConnection conn;
        String version = "";
        try {
            conn = (HttpURLConnection) host.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        try {
            InputStream in = new BufferedInputStream(conn.getInputStream());
            InputStreamReader inreader = new InputStreamReader(in);
            TreeMap result = new Gson().fromJson(inreader, TreeMap.class);
            version = (String)((com.google.gson.internal.LinkedTreeMap)result.get("version")).get("number");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return version;*/
        return "2";
    }
}

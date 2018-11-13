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
    private URL host;
    ElasticClient(String host) {
        try {
            this.host = new URL(host);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getElasticVersion(){
        HttpURLConnection conn;
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
        return version;
    }
}

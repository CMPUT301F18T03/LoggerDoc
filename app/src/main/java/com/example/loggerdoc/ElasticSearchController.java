package com.example.loggerdoc;

import com.example.loggerdoc.elasticclient.ElasticCacheClient;
import com.example.loggerdoc.elasticclient.httphandler;

import okhttp3.OkHttpClient;

public class ElasticSearchController {

    private static final String HOST = "http://104.237.6.208:9200";
    private static ElasticCacheClient singletonclient;
    private static OkHttpClient client;

    public static ElasticCacheClient getCacheClient(){
        if(singletonclient == null){
            client = getHttpClient();
            singletonclient = new ElasticCacheClient(HOST,client);
        }
        return singletonclient;
    }

    public static OkHttpClient getHttpClient(){
        //Just incase we need this somewhere else
        if(singletonclient == null){
            client = new OkHttpClient();
        }
        return client;
    }

    public static httphandler getHttpHandler(){
        return new httphandler(getHttpClient(),HOST);
    }
}

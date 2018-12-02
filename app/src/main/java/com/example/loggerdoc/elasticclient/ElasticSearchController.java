package com.example.loggerdoc.elasticclient;

import com.example.loggerdoc.elasticclient.ElasticCacheClient;
import com.example.loggerdoc.elasticclient.httphandler;

import okhttp3.OkHttpClient;

public class ElasticSearchController {

    private static final String HOST = "http://104.237.6.208:9200";
    private static ElasticCacheClient singletonclient;
    private static OkHttpClient client;

    /*
     * this method returns the elastic cache client, or creates a new one if it
     * doesnt currently exists. It is implemented as a singleton, insuring that only one
     * elasticCacheClient object exists.
     */
    public static ElasticCacheClient getCacheClient(){
        if(singletonclient == null){
            client = getHttpClient();
            singletonclient = new ElasticCacheClient(HOST,client);
        }
        return singletonclient;
    }

    /*
     * this method returns a new http client. If the elastic cache client has been created, then
     * the http client has already been created and can be returned. Otherwise, create a new http
     * client
     */
    public static OkHttpClient getHttpClient(){
        //Just incase we need this somewhere else
        if(singletonclient == null){
            client = new OkHttpClient();
        }
        return client;
    }

    /*
     *this method returns a http handler using the http client and the host ip specified above
     */
    public static httphandler getHttpHandler(){
        return new httphandler(getHttpClient(),HOST);
    }
}

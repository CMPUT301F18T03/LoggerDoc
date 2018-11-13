package com.example.loggerdoc;

public class ElasticSearchController {

    private static final String HOST = "http://104.237.6.208:9200";
    private static ElasticClient singletonclient;

    public static ElasticClient getClient(){
        if(singletonclient == null){
            singletonclient = new ElasticClient(HOST);
        }
        return singletonclient;
    }
}

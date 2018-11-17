package com.example.loggerdoc;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ElasticTest {
    private httphandler handler;
    @Before
    public void init(){
        handler = new httphandler(ElasticSearchController.getHttpClient(),"http://104.237.6.208:9200");
    }
    @Test
    public void Testput(){
        handler.httpDELETE("/test/");
        assertEquals("{\"error\":{\"root_cause\":[{\"type\":\"index_not_found_exception\",\"reason\":\"no such index\",\"resource.type\":\"index_expression\",\"resource.id\":\"test\",\"index_uuid\":\"_na_\",\"index\":\"test\"}],\"type\":\"index_not_found_exception\",\"reason\":\"no such index\",\"resource.type\":\"index_expression\",\"resource.id\":\"test\",\"index_uuid\":\"_na_\",\"index\":\"test\"},\"status\":404}",handler.httpGET("/test/_doc/2"));
        handler.httpPUT("/test/_doc/2","{\"name\": \"Jane Doe\"}");
        assertEquals("{\"_index\":\"test\",\"_type\":\"_doc\",\"_id\":\"2\",\"_version\":1,\"found\":true,\"_source\":{\"name\": \"Jane Doe\"}}",handler.httpGET("/test/_doc/2"));
        handler.httpDELETE("/test/_doc/2");
        assertEquals("{\"_index\":\"test\",\"_type\":\"_doc\",\"_id\":\"2\",\"found\":false}",handler.httpGET("/test/_doc/2"));

        /*System.out.println(testclient.httpGET(""));
        System.out.println(testclient.httpGET("_doc/2"));
        System.out.println(testclient.httpPUT("_doc/2","{\"name\": \"Jane Doe\"}"));

        System.out.println(testclient.httpGET("_doc/2"));

        System.out.println(testclient.eSearch("_doc/2")._version);

        System.out.println(testclient.httpDELETE("_doc/2"));
        System.out.println(testclient.httpGET("_doc/2"));*/
    }
}

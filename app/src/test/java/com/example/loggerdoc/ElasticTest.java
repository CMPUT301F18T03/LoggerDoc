package com.example.loggerdoc;

import android.util.Log;

import org.junit.Test;

import java.util.TreeMap;

import static junit.framework.TestCase.assertTrue;

public class ElasticTest {
    @Test
    public void TestElasticsearchConnection() {
        ElasticClient testclient = ElasticSearchController.getClient();
        //String vnum = testclient.getElasticVersion();
        //assertTrue(vnum != null);
    }
    @Test
    public void Testput(){
        ElasticClient testclient = ElasticSearchController.getClient();
        /*System.out.println(testclient.httpGET(""));
        System.out.println(testclient.httpGET("/test/_doc/2"));
        System.out.println(testclient.httpPUT("/test/_doc/2","{\"name\": \"Jane Doe\"}"));

        System.out.println(testclient.httpGET("/test/_doc/2"));

        System.out.println(testclient.eSearch("/test/_doc/2")._version);

        System.out.println(testclient.httpDELETE("/test/_doc/2"));
        System.out.println(testclient.httpGET("/test/_doc/2"));*/
    }
}

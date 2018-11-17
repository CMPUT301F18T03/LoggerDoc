package com.example.loggerdoc;

import android.util.Log;

import org.junit.Test;

import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

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
        System.out.println(testclient.getrecord("_doc/2"));
        System.out.println(testclient.rawhttpPUT("_doc/2","{\"name\": \"Jane Doe\"}"));
        System.out.println(testclient.getrecord("_doc/2"));

        /*System.out.println(testclient.httpGET(""));
        System.out.println(testclient.httpGET("_doc/2"));
        System.out.println(testclient.httpPUT("_doc/2","{\"name\": \"Jane Doe\"}"));

        System.out.println(testclient.httpGET("_doc/2"));

        System.out.println(testclient.eSearch("_doc/2")._version);

        System.out.println(testclient.httpDELETE("_doc/2"));
        System.out.println(testclient.httpGET("_doc/2"));*/
    }
    /*@Test //This test cannot work, it relies on android
    public void Testuserlist(){
        ElasticClient testclient = ElasticSearchController.getClient();
        UserList x = UserListController.getUserList();
        x.addUser(new CareGiver("asdfghjkl","1@3.ca","12312","farmer"));
        x.addUser(new CareGiver("asdfghjkl2","1@3.ca","12312","farmer"));
        x.addUser(new CareGiver("asdfghjkl3","1@3.ca","12312","farmer"));
        x.addUser(new CareGiver("asdfghjkl4","1@3.ca","12312","farmer"));
        x.addUser(new Patient("qwerty1","4@3.ca","423423","nou"));
        x.addUser(new Patient("qwerty2","4@3.ca","423423","nou"));
        x.addUser(new Patient("qwerty3","4@3.ca","423423","nou"));
        x.addUser(new Patient("qwerty4","4@3.ca","423423","nou"));
        x.addUser(new Patient("qwerty5","4@3.ca","423423","nou"));
        ElasticClient.uploadUsersTask upload =  new ElasticClient.uploadUsersTask();
        upload.execute(x);
        try {
            upload.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}

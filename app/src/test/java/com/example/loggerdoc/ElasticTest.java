package com.example.loggerdoc;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ElasticTest {
    @Test
    public void TestElasticsearchConnection() {
        ElasticClient testclient = ElasticSearchController.getClient();
        String vnum = testclient.getElasticVersion();
        assertTrue(vnum.equals("6.4.2"));
    }
}

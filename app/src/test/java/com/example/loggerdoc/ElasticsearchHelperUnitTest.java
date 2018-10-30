package com.example.loggerdoc;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ElasticsearchHelperUnitTest {

    @Test
    public void TestElasticsearchConnection() {
        RestHighLevelClient client = ElasticsearchHelper.getClient();

        Boolean connected;
        try {
            connected = client.ping(RequestOptions.DEFAULT);
        } catch (IOException e) {
            connected = false;
        }
        assertTrue("Elasticsearch ping should be successful", connected);
    }
}

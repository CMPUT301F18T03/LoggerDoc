/* Created 2018-10-29 by Nick Hoskins
 *
 * This is a helper class designed to help with the handling of Elasticsearch queries. It contains
 * several named constants to improve code quality, along with helper methods to construct
 * ElasticSearch queries.
 */

package com.example.loggerdoc;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticsearchHelper {

    // the location of the Elasticsearch server
    public static String HOST = "104.237.6.208";
    public static Integer PORT = 9200;

    // returns a RestHighLevelClient object, representing a connection to the Elasticsearch server
    public static RestHighLevelClient getClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(ElasticsearchHelper.HOST, ElasticsearchHelper.PORT)
                )
        );
    }
}

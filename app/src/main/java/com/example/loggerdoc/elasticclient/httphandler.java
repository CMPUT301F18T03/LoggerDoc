package com.example.loggerdoc.elasticclient;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class httphandler{
    private OkHttpClient client;
    private String host;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public httphandler(OkHttpClient client,String host) {
        this.client = client;
        this.host = host;
    }

    /**
     * Sends a put request to the elastic server.
     * @param address the address to send too, not including the actual IP and port.
     * @param payload the data to send, as a json string.
     * @return the response from the server, if it is reached
     */
    public String httpPUT(String address,String payload){
        RequestBody body = RequestBody.create(JSON, payload);
        Request request = new Request.Builder()
                .url(host+address)
                .put(body)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();

            ResponseBody x = response.body();
            if(x == null)return null;
            return x.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Gets data from the elastic server
     * @param address the address to get from, not including ip and port.
     * @return the data recevied from the server
     */
    public String httpGET(String address){
        Request request = new Request.Builder()
                .url(host+address)
                .get()
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();

            ResponseBody x = response.body();
            if(x == null)return null;
            return x.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Sends a delete request to the elastic server
     * @param address the address to delete on the elastic server
     * @return the response from the server.
     */
    public String httpDELETE(String address){
        Request request = new Request.Builder()
                .url(host+address)
                .delete()
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();

            ResponseBody x = response.body();
            if(x == null)return null;
            return x.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Posts data to elastic search
     * @param address the address to send too, not including the actual IP and port.
     * @param payload the data to send, as a json string.
     * @return the response from the server, if it is reached
     */
    public String httpPOST(String address,String payload) {
        RequestBody body = RequestBody.create(JSON, payload);
        Request request = new Request.Builder()
                .url(host+address)
                .post(body)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();

            ResponseBody x = response.body();
            if(x == null)return null;
            return x.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

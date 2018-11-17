package com.example.loggerdoc;

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

    httphandler(OkHttpClient client,String host) {
        this.client = client;
        this.host = host;
    }

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
            return x.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String httpGET(String address){
        Request request = new Request.Builder()
                .url(host+address)
                .get()
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();

            ResponseBody x = response.body();
            return x.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String httpDELETE(String address){
        Request request = new Request.Builder()
                .url(host+address)
                .delete()
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();

            ResponseBody x = response.body();
            return x.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}

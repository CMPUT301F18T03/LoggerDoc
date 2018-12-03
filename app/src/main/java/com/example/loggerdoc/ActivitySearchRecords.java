package com.example.loggerdoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.loggerdoc.elasticclient.ElasticCallback;
import com.example.loggerdoc.elasticclient.ElasticDataCallback;

import java.util.ArrayList;

public class ActivitySearchRecords extends AppCompatActivity implements ElasticDataCallback<ArrayList<Integer>> {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_records);
    }

    public void searchButtonPress(View view) {
        // do something to update ProblemRecordListController

        finish();
    }


    @Override
    public void dataCallBack(ArrayList<Integer> data) {

    }
}

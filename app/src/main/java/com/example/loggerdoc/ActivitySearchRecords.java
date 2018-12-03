package com.example.loggerdoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.loggerdoc.elasticclient.ElasticCallback;
import com.example.loggerdoc.elasticclient.ElasticDataCallback;
import com.example.loggerdoc.elasticclient.searchProblemsTask;
import com.example.loggerdoc.elasticclient.searchRecordsTask;

import java.util.ArrayList;

public class ActivitySearchRecords extends AppCompatActivity implements ElasticDataCallback<ArrayList<Integer>> {


    private EditText recordKeywordEditText;
    private EditText recordGeoLocationEditText;
    private EditText searchRecordBodyLocationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_records);
        recordKeywordEditText = findViewById(R.id.searchRecordKeywordsEditText);
        recordGeoLocationEditText = findViewById(R.id.searchRecordGeolocationKeywordsEditText);
        searchRecordBodyLocationEditText = findViewById(R.id.searchRecordBodyLocationKeywordsEditText);
    }

    public void searchButtonPress(View view) {
        String keywords = recordKeywordEditText.getText().toString();
        String geoLocation = recordGeoLocationEditText.getText().toString();
        String bodyLocation = searchRecordBodyLocationEditText.getText().toString();

        searchRecordsTask searchRecords = new searchRecordsTask(getApplicationContext(),
                this, keywords, null, null);
        searchRecords.execute(UserListController.getCurrentUserID());

    }

    @Override
    public void dataCallBack(ArrayList<Integer> data) {
        Intent intent = new Intent();
        intent.putExtra("searchResult", data);
        setResult(RESULT_OK, intent);
        finish();

    }
}
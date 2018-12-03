package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.loggerdoc.elasticclient.ElasticDataCallback;
import com.example.loggerdoc.elasticclient.searchProblemsTask;

import java.util.ArrayList;

public class ActivitySearchProblems extends AppCompatActivity implements ElasticDataCallback<ArrayList<Integer>> {


    private EditText problemKeywordEditText;
    private EditText problemGeoLocationEditText;
    private EditText searchProblemBodyLocationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_problems);
        problemKeywordEditText = findViewById(R.id.searchRecordKeywordsEditText);
        problemGeoLocationEditText = findViewById(R.id.searchProblemGeolocationKeywordsEditText);
        searchProblemBodyLocationEditText = findViewById(R.id.searchRecordBodyLocationKeywordsEditText);
    }

    public void searchButtonPress(View view) {
        String keywords = problemKeywordEditText.getText().toString();
        String geoLocation = problemGeoLocationEditText.getText().toString();
        String bodyLocation = searchProblemBodyLocationEditText.getText().toString();

        searchProblemsTask searchProblems = new searchProblemsTask(getApplicationContext(),
                this, keywords, null, null);
        searchProblems.execute();

    }

    @Override
    public void dataCallBack(ArrayList<Integer> data) {
        Intent intent = new Intent();
        intent.putExtra("searchResult", data);
        setResult(RESULT_OK, intent);
        finish();

    }
}

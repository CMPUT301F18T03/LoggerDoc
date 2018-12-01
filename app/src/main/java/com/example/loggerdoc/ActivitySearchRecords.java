package com.example.loggerdoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ActivitySearchRecords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_records);
    }

    public void searchButtonPress(View view) {
        // do something to update ProblemRecordListController

        finish();
    }
}

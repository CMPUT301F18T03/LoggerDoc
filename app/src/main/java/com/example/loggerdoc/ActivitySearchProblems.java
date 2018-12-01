package com.example.loggerdoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivitySearchProblems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_problems);
    }

    public void searchButtonPress(View view) {
        // do something to update ProblemRecordListController

        finish();
    }
}

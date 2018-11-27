package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ActivityProblemMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_map);

        Intent intent = getIntent();
        int patientID = intent.getIntExtra("Patient",0);

        Patient patient = (Patient) UserListController.getUserList().get(patientID);

        ArrayList<RecordGeoLocation> geoLocationArrayList = new ArrayList<RecordGeoLocation>();

        //for (Record record : ProblemRecordListController.getRecordList().get)
    }
}

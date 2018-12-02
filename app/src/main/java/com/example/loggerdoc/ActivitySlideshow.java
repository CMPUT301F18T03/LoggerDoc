package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ActivitySlideshow extends AppCompatActivity {

    private ArrayList<RecordPhoto> recordPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = getIntent();
        int problemID = intent.getIntExtra("Problem", 0);

        ArrayList<Record> records = ProblemRecordListController.getRecordList().getRecords(problemID);

        for (Record record : records){
            for (int photo : record.getRecordPhotoList()){
                recordPhotos.add(ProblemRecordListController.getRecordPhotoList().get(photo));
            }
        }
    }


}

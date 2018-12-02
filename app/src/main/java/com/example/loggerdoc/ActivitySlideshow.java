package com.example.loggerdoc;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class ActivitySlideshow extends AppCompatActivity {

    private ArrayList<File> recordPhotos;
    private static ViewPager imagePager;

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
        recordPhotos = new ArrayList<File>();

        for (Record record : records){
            for (RecordPhoto photo : ProblemRecordListController.getRecordPhotoList().getRecordPhotos(record.getElasticID())){
                recordPhotos.add(photo.getPhoto());
            }
        }

    }

}

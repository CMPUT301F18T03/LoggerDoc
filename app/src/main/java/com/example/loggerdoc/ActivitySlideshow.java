package com.example.loggerdoc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class ActivitySlideShow extends AppCompatActivity {
    private int index;// = 0;
    private ImageView image;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        Button Back = (Button) findViewById(R.id.Back);
        Button Next = (Button) findViewById(R.id.Next);
        Button returnButton = (Button) findViewById(R.id.returnSlideshow);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        image = (ImageView) findViewById(R.id.imageView);
        context  = ActivitySlideShow.this;
        index = 0;

        Intent intent = getIntent();
        int problemID = intent.getIntExtra("Problem", 0);

        ArrayList<Record> records = ProblemRecordListController.getRecordList().getRecords(problemID);
        ProblemRecordListController.getRecordPhotoList().loadMultipleRecord(records, getApplicationContext());
        final ArrayList<RecordPhoto> photoList = ProblemRecordListController.getRecordPhotoList().getRecordPhotos();

        GlideApp
                .with(context)
                .load(photoList.get(index).getPhoto())
                .override(1000, 1300)
                .centerCrop()
                .into(image);

        Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (index + 1 < photoList.size()){
                    index =  index + 1;
                    GlideApp
                            .with(context)
                            .load(photoList.get(index).getPhoto())
                            .override(1000, 1300)
                            .centerCrop()
                            .into(image);
                }
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (index > 0){
                    index = index -1;
                    GlideApp
                            .with(context)
                            .load(photoList.get(index).getPhoto())
                            .override(1000, 1300)
                            .centerCrop()
                            .into(image);
                }
            }
        });
    }
}

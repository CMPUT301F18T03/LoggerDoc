package com.example.loggerdoc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class ActivityPhotoGrid extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid);
        GridView gridView = (GridView) findViewById(R.id.photo_grid);
        ArrayList<RecordPhoto> photoList = ProblemRecordListController.getRecordPhotoList().getRecordPhotos();
        requestStoragePermission();

        gridView.setAdapter(
                new AdapterPhotoGrid(
                        ActivityPhotoGrid.this, photoList)
        );
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(ActivityPhotoGrid.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityPhotoGrid.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }
    }
}

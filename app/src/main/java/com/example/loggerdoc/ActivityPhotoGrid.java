package com.example.loggerdoc;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class ActivityPhotoGrid extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid);
        /*
        GridView gridView = (GridView) findViewById(R.id.photo_grid);
        ArrayList<RecordPhoto> photoList = ActivityViewRecord.photoList.getRecordPhotos();

        gridView.setAdapter(
                new AdapterPhotoGrid(
                        ActivityPhotoGrid.this, photoList)
        );
    }*/
}

}

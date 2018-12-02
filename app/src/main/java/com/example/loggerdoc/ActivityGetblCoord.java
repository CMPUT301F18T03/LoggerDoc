package com.example.loggerdoc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ActivityGetblCoord extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getbl_coord);
        String path =   getIntent().getStringExtra("TheImage");
        File path1 = new File(path);
        final ArrayList<Integer> location = new ArrayList<Integer>();
        location.add(0);
        location.add(0);


        image =  (ImageView) findViewById(R.id.imageView2);
        GlideApp
                .with(this)
                .load(path1)
                .override(1000, 1200)
                .centerCrop()
                .into(image);
        final int[] viewCoordsFront = new int[2];
        image.getLocationOnScreen(viewCoordsFront);

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int touchx = (int) event.getX();
                int touchy = (int) event.getY();

                int imagex = touchx - viewCoordsFront[0];
                int imagey = touchy - viewCoordsFront[1];

                location.set(0,imagex);
                location.set(1,imagey);
                setResult(RESULT_OK);
                Intent intent1 = new Intent();
                intent1.putExtra("BODYLOCATION", location);
                setResult(Activity.RESULT_OK,intent1);
                finish();
                return false;
            }
        });
    }


}

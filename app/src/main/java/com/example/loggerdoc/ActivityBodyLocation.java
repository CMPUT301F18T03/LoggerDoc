package com.example.loggerdoc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class ActivityBodyLocation extends Activity {
    public Bodylocation bodylocation;

    private ImageView FrontPic;
    private ImageView BackPic;
    private Button Save;
    private Context context = ActivityBodyLocation.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_location);
        Intent intent = getIntent();

        final ArrayList<Integer> location = new ArrayList<Integer>();
        location.add(0);
        location.add(0);
        location.add(0);
        location.add(0);

        FrontPic = (ImageView) findViewById(R.id.FrontPic);
        BackPic = (ImageView) findViewById(R.id.BackPic);
        Save = (Button)findViewById(R.id.BodLocSave);




        FrontPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                location.set(0,x);
                location.set(1,y);
                return false;
            }
        });

        BackPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                location.set(2,x);
                location.set(3,y);
                return false;
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to intent with bodylocation
                setResult(RESULT_OK);
                Intent intent1 = new Intent();
                intent1.putExtra("BODYLOCATION", location);
                setResult(Activity.RESULT_OK,intent1);
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}

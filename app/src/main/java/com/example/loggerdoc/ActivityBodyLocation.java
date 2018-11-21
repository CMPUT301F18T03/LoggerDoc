package com.example.loggerdoc;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
        bodylocation = new Bodylocation();
        FrontPic = (ImageView) findViewById(R.id.FrontPic);
        BackPic = (ImageView) findViewById(R.id.BackPic);
        Save = (Button)findViewById(R.id.BodLocSave);

        GlideApp
                .with(context)
                .load(R.drawable.cutout_650x500)
                .into(FrontPic);
        GlideApp
                .with(context)
                .load(R.drawable.cutout_650x500)
                .into(BackPic);

    }

    @Override
    protected void onResume() {
        super.onResume();

        FrontPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                bodylocation.setFrontTuple(x,y);
                return false;
            }
        });

        BackPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                bodylocation.setBackTuple(x,y);
                return false;
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to intent with bodylocation
            }
        });



    }

}

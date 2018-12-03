package com.example.loggerdoc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

        final int[] viewCoordsFront = new int[2];
        FrontPic.getLocationOnScreen(viewCoordsFront);

        final int[] viewCoordsBack = new int[2];
        BackPic.getLocationOnScreen(viewCoordsBack);




        FrontPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int touchx = (int) event.getX();
                int touchy = (int) event.getY();

                int imagex = touchx - viewCoordsFront[0];
                int imagey = touchy - viewCoordsFront[1];

                location.set(0,imagex);
                location.set(1,imagey);
                Toast.makeText(ActivityBodyLocation.this, "Good Click", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        BackPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int touchx = (int) event.getX();
                int touchy = (int) event.getY();

                int imagex = touchx - viewCoordsBack[0];
                int imagey = touchy - viewCoordsBack[1];

                location.set(2,imagex);
                location.set(3,imagey);
                Toast.makeText(ActivityBodyLocation.this, "Pin successfully added!", Toast.LENGTH_SHORT).show();

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

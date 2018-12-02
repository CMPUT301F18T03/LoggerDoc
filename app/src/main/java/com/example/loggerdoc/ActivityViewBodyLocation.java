package com.example.loggerdoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityViewBodyLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_body_location);
        ArrayList<BodyLocationPhoto> list = ProblemRecordListController.getRecordPhotoList().getBodyLocationPhotos();
        Bodylocation bodylocation = ActivityViewRecord.bodylocation;

        ImageView bl1 = (ImageView) findViewById(R.id.BL1);
        ImageView bl2 = (ImageView) findViewById(R.id.BL2);
        TextView label1 = (TextView) findViewById(R.id.label1);
        TextView label2 = (TextView) findViewById(R.id.label2);
        Log.i("THIS_TAG", list.get(0).getLabel() + list.get(0).getLabel());


        if (list.size() == 1){
            if (bodylocation.getFrontX() !=0 || bodylocation.getFrontY() != 0){
                GlideApp
                        .with(this)
                        .load(list.get(0).getPhoto())
                        .override(500,750)
                        .centerCrop()
                        .into(bl2);
                label2.setText(list.get(0).getLabel());

                //TODO mask for other image
            }
            else{
                GlideApp
                        .with(this)
                        .load(list.get(0).getPhoto())
                        .override(500,750)
                        .centerCrop()
                        .into(bl1);
                label1.setText(list.get(0).getLabel());

                //TODO mask for other image

            }
        }
        else if (list.size() == 2){
            GlideApp
                    .with(this)
                    .load(list.get(0).getPhoto())
                    .override(500,750)
                    .centerCrop()
                    .into(bl1);
            label1.setText(list.get(0).getLabel());

            GlideApp
                    .with(this)
                    .load(list.get(1).getPhoto())
                    .override(500,750)
                    .centerCrop()
                    .into(bl2);
            label2.setText(list.get(1).getLabel());
        }

        else{
            //TODO stuff for mask the stock images
        }
    }
}

package com.example.loggerdoc;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityViewBodyLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_body_location);
        BodyLocationPhotoList list = ActivityViewRecord.blPhotolist;
        Bodylocation bodylocation = ActivityViewRecord.bodylocation;
        ImageView bl1 = (ImageView) findViewById(R.id.BL1);
        ImageView bl2 = (ImageView) findViewById(R.id.BL2);
        TextView label1 = (TextView) findViewById(R.id.label1);
        TextView label2 = (TextView) findViewById(R.id.label2);

        if (list.size() == 1){
            if (bodylocation.getFrontX() !=0 || bodylocation.getFrontY() != 0){
                GlideApp
                        .with(this)
                        .load(list.getPhoto(0).getPhoto())
                        .into(bl2);
                label2.setText(list.getPhoto(0).getLabel());

                //TODO mask for other image
            }
            else{
                GlideApp
                        .with(this)
                        .load(list.getPhoto(0).getPhoto())
                        .into(bl1);
                label1.setText(list.getPhoto(0).getLabel());

                //TODO mask for other image

            }
        }
        else if (list.size() == 2){
            GlideApp
                    .with(this)
                    .load(list.getPhoto(0).getPhoto())
                    .into(bl1);
            label1.setText(list.getPhoto(0).getLabel());

            GlideApp
                    .with(this)
                    .load(list.getPhoto(1).getPhoto())
                    .into(bl2);
            label2.setText(list.getPhoto(1).getLabel());
        }

        else{
            //TODO stuff for mask the stock images
        }
    }
}

package com.example.loggerdoc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityViewBodyLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_body_location);
        BodyLocationPhotoList list = ActivityViewRecord.blPhotoList;
        Bodylocation bodylocation = ActivityViewRecord.bodylocation;

        ImageView bl1 = (ImageView) findViewById(R.id.BL1);
        ImageView bl2 = (ImageView) findViewById(R.id.BL2);
        TextView label1 = (TextView) findViewById(R.id.label1);
        TextView label2 = (TextView) findViewById(R.id.label2);

        Bitmap Front = BitmapFactory.decodeResource(getResources(), R.drawable.cutout);
        Bitmap Back = BitmapFactory.decodeResource(getResources(),R.drawable.cutout);
        Bitmap colorimage = BitmapFactory.decodeResource(getResources(),R.drawable.redsquare);

        colorimage = colorimage.copy(Bitmap.Config.ARGB_8888, true);
        Front = Front.copy(Bitmap.Config.ARGB_8888, true);
        Back = Back.copy(Bitmap.Config.ARGB_8888, true);

        int color = colorimage.getPixel(0,0);


        if (list.size() == 1){
            if (bodylocation.getFrontX() !=0 || bodylocation.getFrontY() != 0) {
                GlideApp
                        .with(this)
                        .load(list.getPhoto(0).getPhoto())
                        .override(500, 750)
                        .centerCrop()
                        .into(bl2);
                label2.setText(list.getPhoto(0).getLabel());

                for (int i=bodylocation.getFrontX(); i<bodylocation.getFrontX() + 20 && i < Front.getWidth(); i++){
                    for (int j=bodylocation.getFrontY(); j<bodylocation.getFrontY() +20 && i < Front.getHeight(); j++){
                        Front.setPixel(i,j,color);
                    }
                }
                GlideApp
                        .with(this)
                        .load(Front)
                        .override(500, 750)
                        .centerCrop()
                        .into(bl1);
            }
            else{
                GlideApp
                        .with(this)
                        .load(list.getPhoto(0).getPhoto())
                        .override(500,750)
                        .centerCrop()
                        .into(bl1);
                label1.setText(list.getPhoto(0).getLabel());

                for(int i = bodylocation.getBackX(); i<bodylocation.getBackX() + 20 && i < Back.getWidth(); i++){
                    for (int j = bodylocation.getBackY(); j<bodylocation.getBackY() +20 && j<Back.getHeight(); j++){
                        Back.setPixel(i,j,color);

                    }
                }
            GlideApp
                    .with(this)
                    .load(Back)
                    .override(500,750)
                    .centerCrop()
                    .into(bl2);

            }
        }
        else if (list.size() == 2){
            GlideApp
                    .with(this)
                    .load(list.getPhoto(0).getPhoto())
                    .override(500,750)
                    .centerCrop()
                    .into(bl1);
            label1.setText(list.getPhoto(0).getLabel());

            GlideApp
                    .with(this)
                    .load(list.getPhoto(1).getPhoto())
                    .override(500,750)
                    .centerCrop()
                    .into(bl2);
            label2.setText(list.getPhoto(1).getLabel());
        }

        else{
            // redraws images with red dot where the body location is located
 
            for (int i=bodylocation.getFrontX(); i<bodylocation.getFrontX() + 20 && i < Front.getWidth(); i++){
                for (int j=bodylocation.getFrontY(); j<bodylocation.getFrontY() +20 && i < Front.getHeight(); j++){
                    Front.setPixel(i,j,color);
                }
            }

            for(int i = bodylocation.getBackX(); i<bodylocation.getBackX() + 20 && i < Back.getWidth(); i++){
                for (int j = bodylocation.getBackY(); j<bodylocation.getBackY() +20 && j<Back.getHeight(); j++){
                    Back.setPixel(i,j,color);

                }
            }

            GlideApp
                    .with(this)
                    .load(Front)
                    .override(500,750)
                    .centerCrop()
                    .into(bl1);
            GlideApp
                    .with(this)
                    .load(Back)
                    .override(500,750)
                    .centerCrop()
                    .into(bl2);

            Log.i("THISTAG", String.valueOf(Front.getPixel(bodylocation.getFrontX(),bodylocation.getFrontY())+Color.RED));


        }
    }
}

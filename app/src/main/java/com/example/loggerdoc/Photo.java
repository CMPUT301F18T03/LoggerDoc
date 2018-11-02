package com.example.loggerdoc;

import android.graphics.Bitmap;

public interface Photo {

    //interface for all things related to photots

    public Bitmap getPhoto(String filename);

    public String setPhoto(Bitmap newPhoto);// saveing picture aswell
    public void Delete();

}

package com.example.loggerdoc;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class RecordPhoto implements Serializable {
    private Uri path;


    public Uri getPhoto() {
        return this.path;
    }

    public void setPhoto(Uri newPhoto) {
        this.path = newPhoto;
    }
}

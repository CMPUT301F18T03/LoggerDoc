package com.example.loggerdoc;

import android.graphics.Bitmap;

public class RecordPhoto implements Photo {
    private String file;

    @Override
    public String getPhoto() {
        return this.file;
    }

    @Override
    public void setPhoto(String newPhoto) {
        this.file = newPhoto;
    }
}

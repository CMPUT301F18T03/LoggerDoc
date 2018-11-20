package com.example.loggerdoc;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class RecordPhoto implements Serializable {
    /**
     * @param path the uri object for the images
     */
    private transient Uri path;

    /**
     *
     * @return uri object for the image
     */

    public Uri getPhoto() {
        return this.path;
    }

    /**
     *
     * @param newPhoto photo that you want to be here
     */

    public void setPhoto(Uri newPhoto) {
        this.path = newPhoto;
    }
}

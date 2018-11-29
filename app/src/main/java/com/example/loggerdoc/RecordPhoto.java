package com.example.loggerdoc;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.Serializable;

public class RecordPhoto implements Serializable {
    /**
     * @param path the uri object for the images
     */
    private File path;

    /**
     *
     * @return uri object for the image
     */

    public File getPhoto() {
        return this.path;
    }

    /**
     *
     * @param newPhoto photo that you want to be here
     */

    public void setPhoto(File newPhoto) {
        this.path = newPhoto;
    }
}

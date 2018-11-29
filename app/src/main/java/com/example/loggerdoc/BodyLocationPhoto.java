package com.example.loggerdoc;

import android.net.Uri;

import java.io.File;

public class BodyLocationPhoto {

    private File photo;
    private String label = "";

    public String getLabel(){ return this.getLabel(); }
    public void setLabel(String newlabel){this.label = newlabel;}

    public File getPhoto() {
        return this.photo;
    }


    public void setPhoto(File newPhoto) {
        this.photo = newPhoto;
    }
}
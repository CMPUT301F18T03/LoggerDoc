package com.example.loggerdoc;

import android.net.Uri;

import com.example.loggerdoc.elasticclient.ElasticID;

import java.io.File;
import java.io.Serializable;

public class BodyLocationPhoto implements Serializable,ElasticID {

    private File photo;
    private String label;
    private Integer ElasticID;
    public String getLabel(){ return this.label; }
    public void setLabel(String newlabel){this.label = newlabel;}

    public File getPhoto() {
        return this.photo;
    }


    public void setPhoto(File newPhoto) {
        this.photo = newPhoto;
    }

    @Override
    public Integer getElasticID() {
        return ElasticID;
    }
}
package com.example.loggerdoc;

import android.net.Uri;

import com.example.loggerdoc.elasticclient.ElasticID;

import java.io.File;
import java.io.Serializable;

public class BodyLocationPhoto extends RecordPhoto implements Serializable,ElasticID {

    private String label;
    public String getLabel(){ return this.label; }
    public void setLabel(String newlabel){this.label = newlabel;}

}
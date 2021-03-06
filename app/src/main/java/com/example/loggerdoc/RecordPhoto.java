package com.example.loggerdoc;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.loggerdoc.elasticclient.ElasticID;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecordPhoto implements Serializable,ElasticID {
    /**
     * @param path the uri object for the images
     */
    private transient File path;
    private Integer ElasticID;
    private Integer ElasticID_OwnerRecord;
    private LocalDateTime timestamp;

    public RecordPhoto(Integer elasticID_OwnerRecord) {
        this.ElasticID_OwnerRecord = elasticID_OwnerRecord;
        ElasticID = this.hashCode();
        timestamp = LocalDateTime.now();
    }

    public RecordPhoto(){

    }

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

    public LocalDateTime getTimestamp(){
        return this.timestamp;
    }

    @Override
    public Integer getElasticID() {
        return this.ElasticID;
    }
    public Integer getElasticID_OwnerRecord(){
        return this.ElasticID_OwnerRecord;
    }

    public void setElasticID_OwnerRecord(Integer elasticID_ownerRecord) {
        this.ElasticID_OwnerRecord = elasticID_ownerRecord;
    }

    public void setElasticID(Integer elasticID) {
        this.ElasticID = elasticID;
    }

    public void genID() {
        if(ElasticID == null){
            ElasticID = this.hashCode();
        }
    }

}

package com.example.loggerdoc;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.loggerdoc.elasticclient.ElasticID;

import java.io.File;
import java.io.Serializable;

public class RecordPhoto implements Serializable,ElasticID {
    /**
     * @param path the uri object for the images
     */
    private File path;
    private Integer ElasticID;
    private Integer ElasticID_OwnerRecord;

    public RecordPhoto(Integer elasticID_OwnerRecord) {
        this.ElasticID_OwnerRecord = elasticID_OwnerRecord;
        ElasticID = this.hashCode();
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

    @Override
    public Integer getElasticID() {
        return this.ElasticID;
    }
    public Integer getElasticID_OwnerRecord(){
        return this.ElasticID_OwnerRecord;
    }
}

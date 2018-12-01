package com.example.loggerdoc;

import android.content.Context;

import com.example.loggerdoc.elasticclient.ElasticDataCallback;
import com.example.loggerdoc.elasticclient.getRecordPhotosTask;
import com.example.loggerdoc.elasticclient.modifyPhotoTask;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;

public class RecordPhotoList extends GenericList<RecordPhoto> implements ElasticDataCallback<ArrayList<RecordPhoto>> {


    /**
     *
     * @return the record phot list
     */
    public ArrayList<RecordPhoto> getRecordPhotos() {
        return getArray();
    }

    /**
     *
     * @param photo record photo to add_internal to list
     */
    public void addPhoto(RecordPhoto photo,Context context) {
        Record x = ProblemRecordListController.getRecordList().get(photo.getElasticID_OwnerRecord());
        x.addRecordPhoto(photo);
        super.add_internal(photo);
        new modifyPhotoTask(context).execute(photo);
    }

    public void loadRecord(Record toload,Context context){
        datalist.clear();
        new getRecordPhotosTask(context,this).execute(toload);
    }


    /**
     *
     * @param photo record photo object to be removed
     */
    public void removePhoto(RecordPhoto photo) {
        super.remove_internal(photo);
    }

    /**
     *
     * @param position the index for array
     * @return return the record photo at specified index
     */
    public RecordPhoto getPhoto(int position) {
        return getArray().get(position);
    }

    /**
     *
     * @param photo record photo to find
     * @return returns a bool if the list has the record photo
     */
    public boolean containsPhoto(RecordPhoto photo) {
        return contains(photo);
    }


    @Override
    public void dataCallBack(ArrayList<RecordPhoto> data) {
        super.load(data);
    }
}
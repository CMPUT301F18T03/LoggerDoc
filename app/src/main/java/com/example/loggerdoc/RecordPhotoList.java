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
import java.util.Collections;
import java.util.Comparator;

public class RecordPhotoList extends GenericList<RecordPhoto> implements ElasticDataCallback<ArrayList<RecordPhoto>> {


    /**
     *
     * @return the record photo list
     */
    public ArrayList<RecordPhoto> getRecordPhotos() {
        ArrayList<RecordPhoto> dataArrayList = new ArrayList<>();
        for(int num = 0;num < datalist.size();num++){
            if(datalist.valueAt(num).getClass() == RecordPhoto.class){
                dataArrayList.add(datalist.valueAt(num));
            }
        }
        return dataArrayList;
    }

    /**
     * This method adds a photo to a the RecordPhotoList
     *
     * @param photo record photo to add to list
     */
    public void addPhoto(RecordPhoto photo,Context context) {
        super.add_internal(photo);
        new modifyPhotoTask(context).execute(photo);
    }


    public void loadRecord(Record toload,Context context){
        datalist.clear();
        new getRecordPhotosTask(context,this).execute(toload);
    }

    public void loadMultipleRecord(ArrayList<Record> toload,Context context){
        datalist.clear();
        Record[] records = toload.toArray(new Record[0]);
        new getRecordPhotosTask(context,this).execute(records);
    }


    /**
     * This method removes the photo from the record photo list if it is in the list
     *
     * @param photo record photo object to be removed
     */
    public void removePhoto(RecordPhoto photo) {
        super.remove_internal(photo);
    }

    /**
     * This method returns the photo at the index represented by the position parameter
     *
     * @param position the index for array
     * @return return the record photo at specified index
     */
    public RecordPhoto getPhoto(int position) {
        return getArray().get(position);
    }

    /**
     *This method returns true if the input photo is in the RecordPhotoList, and false otherwise
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

    public ArrayList<BodyLocationPhoto> getBodyLocationPhotos() {
        ArrayList<BodyLocationPhoto> dataArrayList = new ArrayList<>();
        if(datalist.size() == 0){
            return dataArrayList;
        }
        for(int num = 0;num < datalist.size();num++){
            if(datalist.valueAt(num).getClass() == BodyLocationPhoto.class){
                dataArrayList.add((BodyLocationPhoto) datalist.valueAt(num));
            }
        }
        return dataArrayList;
    }

    /**
     * @author = Alexandra Tyrrell
     *
     * This method returns an arraylist of recordphotos that is sorted by the most recent timestamp.
     *
     * @return ArrayList<Problem> a sorted array list
     */

    public ArrayList<RecordPhoto> sort (){
        ArrayList<RecordPhoto> problems = this.getArray();
        Collections.sort(problems, new Comparator<RecordPhoto>() {
            @Override
            public int compare(RecordPhoto o1, RecordPhoto o2) {
                return o2.getTimestamp().compareTo(o1.getTimestamp());
            }
        });
        return problems;
    }

}
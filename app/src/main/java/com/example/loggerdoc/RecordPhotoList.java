package com.example.loggerdoc;

import java.io.Serializable;
import java.util.ArrayList;

public class RecordPhotoList implements Serializable {
    private ArrayList<RecordPhoto> recordPhotos;

    /**
     * constructor for list
     */
    public RecordPhotoList() {
        recordPhotos = new ArrayList<RecordPhoto>();
    }

    /**
     *
     * @return the record phot list or creates a new list of none exist
     */
    public ArrayList<RecordPhoto> getRecordPhotos() {
        if (this.recordPhotos == null) {
            recordPhotos = new ArrayList<RecordPhoto>();
        }
        return this.recordPhotos;
    }

    /**
     *
     * @param photo record photo to add_internal to list
     */
    public void addPhoto(RecordPhoto photo) {
        recordPhotos.add(photo);
    }

    /**
     *
     * @param photo record photo object to be removed
     */
    public void removePhoto(RecordPhoto photo) {
        recordPhotos.remove(photo);
    }

    /**
     *
     * @param position the index for array
     * @return return the record photo at specified index
     */
    public RecordPhoto getPhoto(int position) {
        return this.recordPhotos.get(position);
    }

    /**
     *
     * @param photo record photo to find
     * @return returns a bool if the list has the record photo
     */
    public boolean containsPhoto(RecordPhoto photo) {
        return recordPhotos.contains(photo);
    }

    /**
     *
     * @param list the list that you want to set as the new list
     */
    public void setList(ArrayList<RecordPhoto> list){
        this.recordPhotos = list;
    }

    /**
     *
     * @return length of array
     */
    public int size() {
        return recordPhotos.size();
    }
}
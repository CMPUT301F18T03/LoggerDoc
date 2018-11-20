package com.example.loggerdoc;

import java.io.Serializable;
import java.util.ArrayList;

public class RecordPhotoList implements Serializable {
    private ArrayList<RecordPhoto> recordPhotos;

    public RecordPhotoList() {
        recordPhotos = new ArrayList<RecordPhoto>();
    }

    public ArrayList<RecordPhoto> getRecordPhotos() {
        if (this.recordPhotos == null) {
            recordPhotos = new ArrayList<RecordPhoto>();
        }
        return this.recordPhotos;
    }

    public void addPhoto(RecordPhoto photo) {
        recordPhotos.add(photo);
    }

    public void removePhoto(RecordPhoto photo) {
        recordPhotos.remove(photo);
    }

    public RecordPhoto getPhoto(int position) {
        return this.recordPhotos.get(position);
    }

    public boolean containsPhoto(RecordPhoto photo) {
        return recordPhotos.contains(photo);
    }

    public void setList(ArrayList<RecordPhoto> list){
        this.recordPhotos = list;
    }

    public int size() {
        return recordPhotos.size();
    }
}
package com.example.loggerdoc;

import java.util.ArrayList;

public class RecordPhotoList {
    private ArrayList<RecordPhoto> recordPhotos;

    public RecordPhotoList() {
        recordPhotos = new ArrayList<RecordPhoto>();
    }

    public void add(RecordPhoto photo) {
        recordPhotos.add(photo);
    }

    public void remove(RecordPhoto photo) {
        recordPhotos.remove(photo);
    }

    public boolean contains(RecordPhoto photo) {
        return recordPhotos.contains(photo);
    }

    public int size() {
        return recordPhotos.size();
    }
}

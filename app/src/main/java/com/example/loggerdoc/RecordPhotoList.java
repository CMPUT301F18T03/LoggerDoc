package com.example.loggerdoc;

import java.io.Serializable;
import java.util.ArrayList;

public class RecordPhotoList extends GenericList<RecordPhoto> {


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
    public void addPhoto(RecordPhoto photo) {
        super.add_internal(photo);
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
}
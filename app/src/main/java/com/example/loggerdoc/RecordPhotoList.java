package com.example.loggerdoc;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
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
    public void addPhoto(RecordPhoto photo,Context context) {
        Record x = ProblemRecordListController.getRecordList().get(photo.getElasticID_OwnerRecord());
        x.addRecordPhoto(photo);
        photo.setPhoto(validatefileloc(photo,context));
        super.add_internal(photo);

    }

    private File validatefileloc(RecordPhoto photo,Context context) {
        File datafile = new File(context.getFilesDir().getAbsolutePath()+"/Data/");
        //validate its in our directory
        if(!photo.getPhoto().toPath().startsWith(datafile.toPath())){
            //If not, move it
            datafile = new File(context.getFilesDir().getAbsolutePath()+"/Data/"+photo.getElasticID()+".jpg");
            try {
                Files.move(photo.getPhoto().toPath(),datafile.toPath());
                return datafile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return photo.getPhoto();
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
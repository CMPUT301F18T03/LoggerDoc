package com.example.loggerdoc;

import android.content.Context;

import com.example.loggerdoc.elasticclient.ElasticCallback;

public class ProblemRecordListController {

    private static ProblemList problemList = null;
    private static RecordList recordList = null;
    private static RecordPhotoList photolist = null;
    private static Integer Loaded_Patient = -1;


    /**
     *
     * @return Returns the ProblemList that the app will use. Lazy Singleton makes it so only one userList is ever created
     */
    static public ProblemList getProblemList() {
        if (problemList == null) {
            problemList = new ProblemList();
        }
        return problemList;
    }

    /**
     *
     * @return The record list.
     */
    static public RecordList getRecordList() {
        if (recordList == null) {
            recordList = new RecordList();
        }
        return recordList;
    }


    /**
     *
     * @return Returns the RecordPhotoList that the app will use. We keep reusing the same list over and over again.
     */
    static public RecordPhotoList getRecordPhotoList() {
        if (photolist == null) {
            photolist = new RecordPhotoList();
        }
        return photolist;
    }

    /**
     * load a users data into the lists
     * @param ElasticID the ID of the user
     * @param context a context to load files with.
     */
    static public void loadUser(Integer ElasticID, Context context){
        Loaded_Patient = ElasticID;
        getProblemList().download(ElasticID,context);
        getRecordList().download(ElasticID,context);
    }

    static public void loadUser(Integer ElasticID, Context context, ElasticCallback callback){
        Loaded_Patient = ElasticID;
        getProblemList().download(ElasticID,context,callback);
        getRecordList().download(ElasticID,context);
    }

    public static Integer getUserID() {
        return Loaded_Patient;
    }
    
}

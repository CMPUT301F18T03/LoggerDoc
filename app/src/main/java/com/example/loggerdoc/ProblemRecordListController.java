package com.example.loggerdoc;

public class ProblemRecordListController {

    private static ProblemList problemList = null;
    private static RecordList recordList = null;
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

    static public void loadUser(Integer ElasticID){
        Loaded_Patient = ElasticID;
    }

    public static Integer getUserID() {
        return Loaded_Patient;
    }

    /*public static void setList(ArrayList<Problem> list) {
        getList().load(list);
    }*/



}

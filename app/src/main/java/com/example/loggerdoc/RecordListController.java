package com.example.loggerdoc;

import java.util.ArrayList;

public class RecordListController {

    private static RecordList dataList = null;

    /**
     *
     * @return Returns the userList that the app will use. Lazy Singleton makes it so only one userList is ever created
     */
    static public RecordList getList() {
        if (dataList == null) {
            dataList = new RecordList();
        }
        return dataList;
    }

    public static void setList(ArrayList<Record> list) {
        getList().load(list);
    }



}



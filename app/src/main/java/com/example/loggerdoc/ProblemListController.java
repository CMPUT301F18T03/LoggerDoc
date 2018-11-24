package com.example.loggerdoc;

import java.util.ArrayList;

public class ProblemListController {

    private static ProblemList dataList = null;

    /**
     *
     * @return Returns the userList that the app will use. Lazy Singleton makes it so only one userList is ever created
     */
    static public ProblemList getList() {
        if (dataList == null) {
            dataList = new ProblemList();
        }
        return dataList;
    }

    public static void setList(ArrayList<Problem> list) {
        getList().load(list);
    }



}

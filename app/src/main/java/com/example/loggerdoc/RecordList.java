/* Created 2018-10-24 by Nick Hoskins
 *
 * RecordList represents an ordered list of Record objects. The list of records associated with
 * each problem is represented by a RecordList.
 */

package com.example.loggerdoc;

import java.util.ArrayList;

public class RecordList {

    private ArrayList<Record> recordArrayList;

    public RecordList() {
        recordArrayList = new ArrayList<>();
    }

    public void add(Record record) {
        recordArrayList.add(record);
    }

    public void remove(Record record) {
        recordArrayList.remove(record);
    }

    public boolean contains(Record record) {
        return recordArrayList.contains(record);
    }

    public int size() {
        return recordArrayList.size();
    }

}

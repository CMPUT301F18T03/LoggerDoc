/* Created 2018-10-24 by Nick Hoskins
 *
 * RecordList represents an ordered list of Record objects. The list of records associated with
 * each problem is represented by a RecordList.
 */

package com.example.loggerdoc;

import java.io.Serializable;
import java.util.ArrayList;

public class RecordList implements Serializable {

    private ArrayList<Record> recordArrayList;

    /**
     * Record list is a list that stores objects of type Record. Every Problem will have a list of records
     * associated with it.
     */
    public RecordList() {
        recordArrayList = new ArrayList<>();
    }

    /**
     *
     * @return Returns an ArrayList of Record objects
     */
    public ArrayList<Record> getRecordArrayList (){
        return this.recordArrayList;
    }

    /**
     *
     * @param record Adds record to the Record List
     */
    public void add(Record record) {
        recordArrayList.add(record);
    }

    /**
     *
     * @param record Removes record from the Record List
     */
    public void remove(Record record) {
        recordArrayList.remove(record);
    }

    /**
     *
     * @param record Searches Record List to see if record is in RecordList
     * @return Returns True if record is in Record List, False if record is not in Record List
     */
    public boolean contains(Record record) {
        return recordArrayList.contains(record);
    }

    /**
     *
     * @return Returns and int that corresponds to the size of the Record List
     */
    public int size() {
        return recordArrayList.size();
    }

}

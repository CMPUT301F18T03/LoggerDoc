/* Created 2018-10-24 by Nick Hoskins
 *
 * RecordList represents an ordered list of Record objects. The list of records associated with
 * each problem is represented by a RecordList.
 */
package com.example.loggerdoc;


import android.content.Context;

public class RecordList extends GenericList<Record> {

    protected void add(Record data,Context context) {
        super.add(data);
    }

}

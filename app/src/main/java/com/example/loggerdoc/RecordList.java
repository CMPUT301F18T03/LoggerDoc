/* Created 2018-10-24 by Nick Hoskins
 *
 * RecordList represents an ordered list of Record objects. The list of records associated with
 * each problem is represented by a RecordList.
 */
package com.example.loggerdoc;


import android.content.Context;

import com.example.loggerdoc.elasticclient.ElasticDataCallback;
import com.example.loggerdoc.elasticclient.getRecordsTask;

import java.util.ArrayList;

public class RecordList extends GenericList<Record> implements ElasticDataCallback<ArrayList<Record>> {

    protected void add(Record data,Context context) {
        super.add(data);
    }

    public void download(Integer elasticID, Context context) {
        new getRecordsTask(context,this).execute(elasticID);
    }

    public void dataCallBack(ArrayList<Record> data) {
        super.load(data);
    }
}

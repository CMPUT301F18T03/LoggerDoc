/* Created 2018-10-24 by Nick Hoskins
 *
 * RecordList represents an ordered list of Record objects. The list of records associated with
 * each problem is represented by a RecordList.
 */
package com.example.loggerdoc;


import android.content.Context;
import com.example.loggerdoc.elasticclient.ElasticDataCallback;
import com.example.loggerdoc.elasticclient.getRecordsTask;
import com.example.loggerdoc.elasticclient.modifyRecordTask;

import java.util.ArrayList;

public class RecordList extends GenericList<Record> implements ElasticDataCallback<ArrayList<Record>> {


    protected void add(Record data,Context context) {
        super.add_internal(data);
        new modifyRecordTask(context).execute(data);
    }

    public void download(Integer elasticID, Context context) {
        new getRecordsTask(context,this).execute(elasticID);
    }

    public void dataCallBack(ArrayList<Record> data) {
        super.load(data);
    }

    public Integer getRecordCount(Integer ProblemID){
        Integer Count = 0;
        for(int num = 0;num < datalist.size();num++){
            if(datalist.valueAt(num).getElasticID_OwnerProblem().equals(ProblemID)){
                Count++;
            }
        }
        return Count;
    }
}

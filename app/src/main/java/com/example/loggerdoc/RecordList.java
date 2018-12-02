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
import com.example.loggerdoc.elasticclient.removeRecordTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecordList extends GenericList<Record> implements ElasticDataCallback<ArrayList<Record>> {

    /**
     * This method adds the input Record 'data' to the RecordList
     *
     * @param data the Record to add
     *
     */
    protected void add(Record data,Context context) {
        super.add_internal(data);
        new modifyRecordTask(context).execute(data);
    }


    /**
     * See "add" for explanation. This method was implemented to reduce confusion as
     * adding and updating Records is done by the same method.
     *
     * @param data the Record to update
     *
     */
    protected void update(Record data,Context context){
        add(data,context);
    }


    /**
     * This method removes the input Record 'data' from the RecordList
     *
     * @param data the Record to remove
     *
     */
    protected void remove(Record data,Context context){
        super.remove_internal(data);
        new removeRecordTask(context).execute(data);
    }


    /**
     * This method creates an async task to download a recordlist from elastic or
     * read it from memory if no connectivity
     *
     * @param elasticID the elastic id of the user to get the record list for
     *
     */
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

    /**
     * Returns the records associated with a given problem ID
     * @param ProblemID the problem in question
     * @return an arraylist of records
     */
    public ArrayList<Record> getRecords(Integer ProblemID){
        ArrayList<Record> ret = new ArrayList<>();
        for(int num = 0;num < datalist.size();num++){
            if(datalist.valueAt(num).getElasticID_OwnerProblem().equals(ProblemID)){
                ret.add(datalist.valueAt(num));
            }
        }

        Collections.sort(ret, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o2.getTimestamp().compareTo(o1.getTimestamp());
            }
        });

        return ret;
    }

}

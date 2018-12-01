/* ProblemList.class
 *
 * Version 1.0
 *
 * Created 2018-10-24 by Nick Hoskins
 *
 * November 15, 2018
 */

package com.example.loggerdoc;

import android.content.Context;
import com.example.loggerdoc.elasticclient.ElasticCallback;
import com.example.loggerdoc.elasticclient.ElasticDataCallback;
import com.example.loggerdoc.elasticclient.getProblemsTask;
import com.example.loggerdoc.elasticclient.modifyProblemTask;
import com.example.loggerdoc.elasticclient.removeProblemTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProblemList extends GenericList<Problem> implements ElasticDataCallback<ArrayList<Problem>> {

    ElasticCallback callback = null;
    
    protected void add(Problem data,Context context) {
        super.add_internal(data);
        new modifyProblemTask(context).execute(data);
    }

    protected void update(Problem data,Context context){
        add(data,context);
    }

    protected void remove(Problem data,Context context){
        super.remove_internal(data);
        new removeProblemTask(context).execute(data);
    }

    public void download(Integer elasticID, Context context) {
        new getProblemsTask(context,this).execute(elasticID);
    }
    public void download(Integer elasticID, Context context, ElasticCallback callback) {
        download(elasticID,context);
        this.callback = callback;
    }

    public void dataCallBack(ArrayList<Problem> data) {
        super.load(data);
        if(callback !=null){
            callback.callBack("Problems");
            callback=null;
        }
    }

    /**
     * @author = Alexandra Tyrrell
     *
     * This method returns an arraylist of problems that is sorted by the most recent timestamp.
     * 
     * @return ArrayLIst<Problem> a sorted array list
     */

    public ArrayList<Problem> sort (){
        ArrayList<Problem> problems = this.getArray();
        Collections.sort(problems, new Comparator<Problem>() {
            @Override
            public int compare(Problem o1, Problem o2) {
                return o2.getTimestamp().compareTo(o1.getTimestamp());
            }
        });
        return problems;
    }

}

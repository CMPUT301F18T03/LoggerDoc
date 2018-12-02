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


    /**
     * This method adds the input problem 'data' to the ProblemList
     *
     * @param data the Problem to add
     *
     */
    protected void add(Problem data,Context context) {
        super.add_internal(data);
        new modifyProblemTask(context).execute(data);
    }

    /**
     * See "add" for explanation. This method was implemented to reduce confusion as
     * adding and updating Problems is done by the same method.
     *
     * @param data the Problem to update
     *
     */
    protected void update(Problem data,Context context){
        add(data,context);
    }

    /**
     * This method removes the input Problem 'data' from the ProblemList
     *
     * @param data the Problem to remove
     *
     */
    protected void remove(Problem data,Context context){
        super.remove_internal(data);
        new removeProblemTask(context).execute(data);
    }

    /**
     * This method creates an async task to download a problemlist from elastic or
     * read it from memory if no connectivity
     *
     * @param elasticID the elastic id of the user to get the problem list for
     *
     */
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
     * @return ArrayList<Problem> a sorted array list
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

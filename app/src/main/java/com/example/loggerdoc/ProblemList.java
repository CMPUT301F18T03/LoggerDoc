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

import com.example.loggerdoc.elasticclient.ElasticDataCallback;
import com.example.loggerdoc.elasticclient.getProblemsTask;

import java.util.ArrayList;

public class ProblemList extends GenericList<Problem> implements ElasticDataCallback<ArrayList<Problem>> {

    protected void add(Problem data,Context context) {
        super.add_internal(data);
    }

    public void download(Integer elasticID, Context context) {
        new getProblemsTask(context,this).execute(elasticID);
    }

    public void dataCallBack(ArrayList<Problem> data) {
        super.load(data);
    }
}



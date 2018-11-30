package com.example.loggerdoc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * This class is to be used by ActivityBrowseProblems.class to create a custom ArrayAdapter and
 * ListView. The ListView will show the title, date and the number of records of a particular
 * problem.
 */

public class AdapterListProblems extends ArrayAdapter<Problem> {

    private ArrayList<Problem> problemList;
    private Context problemContext;

    /**
     * @author Alexandra Tyrrell
     *
     * Constructor for the AdapterListProblem.
     *
     * @param context Context
     * @param list ArrayList
     */
    public AdapterListProblems (@NonNull Context context, ArrayList<Problem> list){
        super (context, 0, list);
        this.problemContext = context;
        this.problemList = list;
    }

    public void refresh(ArrayList<Problem> a) {
        this.problemList.clear();
        this.problemList = a;
    }

    /**
     * @author Alexandra Tyrrell
     *
     * This creates a custom ListView which displays the problem title, date of discovery and the
     * number of records for that particular problem.
     *
     * @param position int
     * @param convertView View
     * @param parent View Group
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem = convertView;

        if (listitem == null){
            listitem = LayoutInflater.from(problemContext).inflate(R.layout.browse_problem_list_view,
                    parent,false);
        }
        Problem currentProblem = problemList.get(position);

        TextView problemTitle = (TextView) listitem.findViewById(R.id.UserNameDisplay);
        problemTitle.setText(currentProblem.getTitle());

        TextView date = (TextView) listitem.findViewById(R.id.DateView);
        date.setText(currentProblem.getTimestamp().toString());

        TextView numberRecords = (TextView) listitem.findViewById(R.id.DescriptionView);
        numberRecords.setText("This problem has " + ProblemRecordListController.getRecordList()
                .getRecordCount(currentProblem.getElasticID()) + " records");

        return listitem;
    }

}

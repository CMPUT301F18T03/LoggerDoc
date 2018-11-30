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
 * This class is to be used by ActivityViewProblem.class to create a custom ArrayAdapter and
 * ListView. The ListView will show the comment and the date of a caregiver comment for a problem.
 */

public class AdapterListComments extends ArrayAdapter<CaregiverComment> {

    private ArrayList<CaregiverComment> commentList;
    private Context commentContext;

    /**
     * @author Alexandra Tyrrell
     *
     * Constructor for the AdapterListComments.
     *
     * @param context Context
     * @param list ArrayList
     */
    public AdapterListComments (@NonNull Context context, ArrayList<CaregiverComment> list){
        super (context, 0, list);
        commentContext = context;
        commentList = list;
    }

    public void refresh(ArrayList<CaregiverComment> a) {
        this.commentList = a;
    }
    
    /**
     * @author Alexandra Tyrrell
     *
     * This creates a custom ListView which displays the comment and the date of a caregiver comment
     * for a problem.
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
            listitem = LayoutInflater.from(commentContext).inflate(R.layout.caregiver_comment_listview,parent,false);
        }
        CaregiverComment currentComment = commentList.get(position);

        TextView date = (TextView) listitem.findViewById(R.id.DateView);
        date.setText(currentComment.getDate().toString());

        TextView description = (TextView) listitem.findViewById(R.id.CommentView);
        description.setText(currentComment.getComment());

        return listitem;
    }
}

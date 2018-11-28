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

public class AdapterListComments extends ArrayAdapter<CaregiverComment> {

    private ArrayList<CaregiverComment> commentList;
    private Context commentContext;

    public AdapterListComments (@NonNull Context context, ArrayList<CaregiverComment> list){
        super (context, 0, list);
        commentContext = context;
        commentList = list;
    }

    public void refresh(ArrayList<CaregiverComment> a) {
        this.commentList = a;
    }

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

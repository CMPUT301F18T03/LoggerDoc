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

public class AdapterListRecords extends ArrayAdapter<Record> {
    private ArrayList<Record> recordsList;
    private Context commentContext;

    public AdapterListRecords (@NonNull Context context, ArrayList<Record> list){
        super (context, 0, list);
        commentContext = context;
        recordsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem = convertView;

        if (listitem == null){
            listitem = LayoutInflater.from(commentContext).inflate(R.layout.caregiver_comment_listview,parent,false);
        }
        Record currentRecords = recordsList.get(position);

        TextView TitleView = (TextView) listitem.findViewById(R.id.DateView);
        TitleView.setText(currentRecords.getTitle());

        TextView date = (TextView) listitem.findViewById(R.id.CommentView);
        date.setText(currentRecords.getTimestamp().toString());

        return listitem;
    }
}

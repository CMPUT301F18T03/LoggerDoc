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
 * This class is to be used by ActivityViewRecordList.class to create a custom ArrayAdapter
 * and ListView. The ListView will show the title and the date that the record was created of
 * a particular record.
 */

public class AdapterListRecords extends ArrayAdapter<Record> {
    private ArrayList<Record> recordsList;
    private Context commentContext;

    /**
     * @author Alexandra Tyrrell
     *
     * Constructor for the AdapterListRecord
     *
     * @param context Context
     * @param list ArrayList
     */
    public AdapterListRecords (@NonNull Context context, ArrayList<Record> list){
        super (context, 0, list);
        commentContext = context;
        recordsList = list;
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
            listitem = LayoutInflater.from(commentContext).inflate(R.layout.record_view_list,parent,false);
        }
        Record currentRecords = recordsList.get(position);

        TextView TitleView = (TextView) listitem.findViewById(R.id.DateView);
        TitleView.setText(currentRecords.getTitle());

        TextView date = (TextView) listitem.findViewById(R.id.DescriptionView);
        date.setText(currentRecords.getTimestamp().toString());

        return listitem;
    }
}

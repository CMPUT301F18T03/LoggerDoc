package com.example.loggerdoc;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class AdapterPhotoGrid extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;

    private ArrayList<RecordPhoto> photoList;

    public AdapterPhotoGrid(Context context, ArrayList<RecordPhoto> list) {
        super(context, R.layout.grid_view_image, list);
        this.context = context;
        this.photoList = list;

        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.grid_view_image, parent, false);
        }

        GlideApp
                .with(context)
                .load(photoList.get(position).getPhoto())
                .override(400,400)
                .centerCrop()
                .into((ImageView) convertView);


        return convertView;
    }
}

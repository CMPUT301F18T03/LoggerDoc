package com.example.loggerdoc;

import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class AdapterSlidingImage extends PagerAdapter {

    private ArrayList<RecordPhoto>recordPhotoArrayList;
    private LayoutInflater inflater;
    private Context context;

    public AdapterSlidingImage(Context context, ArrayList<RecordPhoto> recordPhotoArrayList) {
        this.context = context;
        this.recordPhotoArrayList = recordPhotoArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isViewFromObject (View view, Object object){
        return view.equals(object);
    }

    @Override
    public int getCount(){
        return this.recordPhotoArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position){
        View imageLayout = inflater.inflate (R.layout.slideshowlayout, view, false);

        assert (imageLayout!=null);

        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.slideshowImage);
        imageView.setImageResource(recordPhotoArrayList.get(position).getPhoto());

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

}

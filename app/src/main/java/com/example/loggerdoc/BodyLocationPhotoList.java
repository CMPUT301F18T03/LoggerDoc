package com.example.loggerdoc;

import java.io.Serializable;
import java.util.ArrayList;

public class BodyLocationPhotoList extends GenericList<BodyLocationPhoto> {
    private ArrayList<BodyLocationPhoto> bodyLocationlist;

    public BodyLocationPhotoList() {
        bodyLocationlist = new ArrayList<>();
    }

    public void add(BodyLocationPhoto photo) {
        bodyLocationlist.add(photo);
    }

    public void remove(BodyLocationPhoto photo) {
        bodyLocationlist.remove(photo);
    }

    public BodyLocationPhoto getPhoto(int position){return this.bodyLocationlist.get(position);}

    public boolean contains(BodyLocationPhoto photo) {
        return bodyLocationlist.contains(photo);
    }

    public int size() {
        return bodyLocationlist.size();
    }

}


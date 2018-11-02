package com.example.loggerdoc;

import java.util.ArrayList;

public class BodyLocationPhotoList {
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

    public boolean contains(BodyLocationPhoto photo) {
        return bodyLocationlist.contains(photo);
    }

    public int size() {
        return bodyLocationlist.size();
    }

}


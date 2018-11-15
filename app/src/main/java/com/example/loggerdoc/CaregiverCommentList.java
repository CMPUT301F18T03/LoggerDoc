package com.example.loggerdoc;

import java.util.ArrayList;

public class CaregiverCommentList {

    private ArrayList <CaregiverComment> comments;

    public CaregiverCommentList(){
        this.comments = new ArrayList<CaregiverComment>();
    }

    public ArrayList<CaregiverComment> getComments(){
        return this.comments;
    }

    public void addComment (CaregiverComment comment){
        this.comments.add(comment);
    }

    public void deleteComment (CaregiverComment comment){
        this.comments.remove(comment);
    }

    public int getSizeOfComments(){
        return this.comments.size();
    }
}

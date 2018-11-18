package com.example.loggerdoc;

import java.io.Serializable;
import java.util.ArrayList;

public class CaregiverCommentList implements Serializable {

    private ArrayList <CaregiverComment> comments;

    /**
     * Caregiver comment list is a list of comments that a specific CareGiver has made
     */
    public CaregiverCommentList(){
        this.comments = new ArrayList<CaregiverComment>();
    }

    /**
     *
     * @return Returns the comment list that the CareGiver has
     */
    public ArrayList<CaregiverComment> getComments(){
        return this.comments;
    }

    public boolean containsComment (CaregiverComment comment){
        return this.comments.contains(comment);
    }

    /**
     *
     * @param comment comment that the CareGiver adds. Gets added to the CareGivers comment list.
     */
    public void addComment (CaregiverComment comment){
        this.comments.add(comment);
    }

    /**
     *
     * @param comment The comment that the CareGiver wants to delete from their comment list
     */
    public void deleteComment (CaregiverComment comment){
        this.comments.remove(comment);
    }

    /**
     *
     * @return Returns the size of the comment list that a specific CareGiver has
     */
    public int getSizeOfComments(){
        return this.comments.size();
    }
}

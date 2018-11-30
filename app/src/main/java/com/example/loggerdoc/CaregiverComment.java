package com.example.loggerdoc;

import java.time.LocalDateTime;

public class CaregiverComment {

    private CareGiver caregiver;
    private LocalDateTime date;
    private String comment;

    /**
     *
     * @param comment Comment is a string that the CareGiver is making as a comment
     */
    public CaregiverComment (CareGiver caregiver, String comment){
        this.caregiver = caregiver;
        this.date = LocalDateTime.now();
        this.comment = comment;
    }

    public CareGiver getCaregiver(){
        return this.caregiver;
    }

    public void setCaregiver (CareGiver newCaregiver){
        this.caregiver = newCaregiver;
    }

    /**
     *
     * @return Returns the date of when the comment was posted
     */
    public LocalDateTime getDate(){
        return this.date;
    }

    public void setDate (LocalDateTime newDate){
        this.date = newDate;
    }

    /**
     *
     * @return Returns the comment that the CareGiver made
     */
    public String getComment(){
        return this.comment;
    }

    public void setComment(String newComment){
        this.comment = newComment;
    }
}

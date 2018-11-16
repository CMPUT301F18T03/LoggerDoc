package com.example.loggerdoc;

import java.time.LocalDateTime;

public class CaregiverComment {

    private CareGiver caregiver;
    private LocalDateTime date;
    private String comment;

    public CaregiverComment (CareGiver caregiver, String comment){
        this.caregiver = caregiver;
        this.date = LocalDateTime.now();
        this.comment = comment;
    }

    public CareGiver getCaregiver() {
        return this.caregiver;
    }

    public void setCaregiver (CareGiver newCaregiver){
        this.caregiver = newCaregiver;
    }

    public LocalDateTime getDate(){
        return this.date;
    }

    public void setDate (LocalDateTime newDate){
        this.date = newDate;
    }

    public String getComment(){
        return this.comment;
    }

    public void setComment(String newComment){
        this.comment = newComment;
    }
}

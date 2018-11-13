package com.example.loggerdoc;


import java.io.Serializable;

public class Patient extends User implements Serializable {

    private CareGiverList careGivers;

    public Patient(String patientID, String emailAddress, String phoneNumber, CareGiverList careGivers) {
        super(patientID, emailAddress, phoneNumber);
        this.careGivers = careGivers;
    }


}
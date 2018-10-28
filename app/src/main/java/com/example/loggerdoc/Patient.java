package com.example.loggerdoc;


public class Patient extends User {

    private CareGiverList careGivers;

    public Patient(String patientID, String emailAddress, String phoneNumber, CareGiverList careGivers) {
        super(patientID, emailAddress, phoneNumber);
        this.careGivers = careGivers;
    }


}
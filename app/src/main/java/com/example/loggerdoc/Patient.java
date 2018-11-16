package com.example.loggerdoc;


import java.io.Serializable;

public class Patient extends User implements Serializable {

    private CareGiverList careGivers;

    public Patient(String patientID, String emailAddress, String phoneNumber, String role, CareGiverList careGivers) {
        super(patientID, emailAddress, phoneNumber, role);
        this.careGivers = careGivers;
    }

    public CareGiverList getCareGivers() {
        return careGivers;
    }

    public void setCareGivers(CareGiverList careGivers) {
        this.careGivers = careGivers;
    }
}
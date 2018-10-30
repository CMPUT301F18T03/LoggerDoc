package com.example.loggerdoc;

import java.util.ArrayList;

public class CareGiver extends User {

    // An array list to store all of the patients that a caregiver has
    // Each CareGiver will have a unique set of patients
    private PatientList patients;

    // CareGiver constructor with an ID, email address, phone number and list of patients
    public CareGiver(String careGiverID, String emailAddress, String phoneNumber, PatientList patients) {
        super(careGiverID, emailAddress, phoneNumber);
        this.patients = patients;
    }

}
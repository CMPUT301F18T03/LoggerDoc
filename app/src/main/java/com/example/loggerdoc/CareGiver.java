package com.example.loggerdoc;

import java.io.Serializable;
import java.util.ArrayList;

public class CareGiver extends User implements Serializable {

    // An array list to store all of the patients that a caregiver has
    // Each CareGiver will have a unique set of patients
    private ArrayList<Integer> patients;

    /**
     *
     * @param careGiverID Unique username that the user picks. Must be at least 8 characters
     * @param emailAddress email address of the user
     * @param phoneNumber phone number of the user
     * @param role role will always be "Caregiver" when making a CareGiver object
     * @param patients A CareGiver will have a list of patients that they are connected too
     */
    // CareGiver constructor with an ID, email address, phone number and list of patients
    public CareGiver(String careGiverID, String emailAddress, String phoneNumber, String role, PatientList patients) {
        super(careGiverID, emailAddress, phoneNumber, role);
        this.patients = patients.getPatients();
    }
    public CareGiver(String careGiverID, String emailAddress, String phoneNumber, String role) {
        super(careGiverID, emailAddress, phoneNumber, role);
        this.patients = new ArrayList<>();
    }

    /**
     *
     * @return returns the Patient List that the user has
     */
    public ArrayList<Integer> getPatientList(){
        return patients;
    }

    public void addPatient(Patient patientToAdd) {
        patients.add(patientToAdd.getElasticID());
    }
}

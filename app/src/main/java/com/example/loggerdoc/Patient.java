package com.example.loggerdoc;


import java.io.Serializable;
import java.util.ArrayList;

public class Patient extends User implements Serializable {

    private ArrayList<Integer> careGivers;

    private ArrayList<Integer> problems;

    /**
     *
     * @param patientID Unique username that the user picks. Must be at least 8 characters
     * @param emailAddress email address of the user
     * @param phoneNumber phone number of the user
     * @param role role will always be "Patient" when making a Patient object
     * @param careGivers A CareGiver will have a list of CareGivers that they are connected too
     */
    public Patient(String patientID, String emailAddress, String phoneNumber, String role, CareGiverList careGivers) {
        super(patientID, emailAddress, phoneNumber, role);
        this.careGivers = new ArrayList<>();//TODO: Fix caregiverlists in general
        problems = new ArrayList<>();
    }
    public Patient(String patientID, String emailAddress, String phoneNumber, String role) {
        super(patientID, emailAddress, phoneNumber, role);
        this.careGivers = new ArrayList<>();
        problems = new ArrayList<>();
    }

    /**
     *
     * @return Returns the CareGiverList that is associated with a specific Patient object
     */
    public ArrayList<Integer> getCareGivers() {
        return careGivers;
    }

    /**
     *
     * @return Returns the list of problems that a Patient has
     */
    public ArrayList<Integer> getProblems(){
        return this.problems;
    }


}
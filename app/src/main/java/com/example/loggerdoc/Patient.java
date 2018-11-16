package com.example.loggerdoc;


import java.io.Serializable;
import java.util.ArrayList;

public class Patient extends User implements Serializable {

    private CareGiverList careGivers;

    private ProblemList problems;

    public Patient(String patientID, String emailAddress, String phoneNumber, String role, CareGiverList careGivers) {
        super(patientID, emailAddress, phoneNumber, role);
        this.careGivers = careGivers;
        problems = new ProblemList();
    }

    public CareGiverList getCareGivers() {
        return careGivers;
    }
    public ProblemList getProblems(){
        return this.problems;
    }

    public void setCareGivers(CareGiverList careGivers) {
        this.careGivers = careGivers;
    }
}
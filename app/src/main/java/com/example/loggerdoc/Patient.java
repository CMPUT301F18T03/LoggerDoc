package com.example.loggerdoc;


import java.io.Serializable;
import java.util.ArrayList;

public class Patient extends User implements Serializable {

    private CareGiverList careGivers;

    private ProblemList problems;

    public Patient(String patientID, String emailAddress, String phoneNumber, CareGiverList careGivers) {
        super(patientID, emailAddress, phoneNumber);
        this.careGivers = careGivers;
        problems = new ProblemList();
    }

    public ProblemList getProblems(){
        return this.problems;
    }

}
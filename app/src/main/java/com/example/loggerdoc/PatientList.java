package com.example.loggerdoc;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

// Class that handles a list of patients
public class PatientList implements Serializable {

    private ArrayList<Patient> patientList;

    //PatientList constructor
    public PatientList(){
        patientList = new ArrayList<Patient>();
    }

    public Collection<Patient> getPatients(){
        return patientList;
    }

    // add a careGiver to the list of careGivers
    public void addPatient(Patient patient) {
        patientList.add(patient);
    }

    // remove a careGiver from the list of careGivers
    public void removePatient(Patient patient) {
        patientList.remove(patient);
    }

    public int size(){
        return patientList.size();
    }

    public boolean contains(Patient patient){
        return patientList.contains(patient);
    }

}

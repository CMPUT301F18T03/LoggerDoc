package com.example.loggerdoc;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

// Class that handles a list of patients
public class PatientList implements Serializable {

    private ArrayList<Integer> patientList;

    //PatientList constructor
    public PatientList(){
        patientList = new ArrayList<>();
    }

    /**
     *
     * @return Returns a list of patients
     */
    public ArrayList<Integer> getPatients(){
        return patientList;
    }

    /**
     *
     * @param patient adds patient to the patientList
     */
    // add_internal a careGiver to the list of careGivers
    public void addPatient(Patient patient) {
        patientList.add(patient.getElasticID());
    }

    /**
     *
     * @param patient Removes patient from the patientList
     */
    // remove a careGiver from the list of careGivers
    public void removePatient(Patient patient) {
        patientList.remove(patient.getElasticID());
    }

    /**
     *
     * @return Returns the size of the patientList
     */
    public int size(){
        return patientList.size();
    }

    /**
     *
     * @param patient Patient to search patientList for
     * @return Returns true if patient is in patientList, otherwise return false
     */
    public boolean contains(Patient patient){
        return patientList.contains(patient.getElasticID());
    }

}

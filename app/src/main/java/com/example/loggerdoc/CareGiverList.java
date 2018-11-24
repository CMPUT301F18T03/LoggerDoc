package com.example.loggerdoc;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class CareGiverList implements Serializable {

    private ArrayList<Integer> careGiverList;

    //PatientList constructor
    public CareGiverList(){
        careGiverList = new ArrayList<>();
    }

    /**
     *
     * @return Returns a collection of CareGivers
     */
    public ArrayList<Integer> getCareGivers(){
        return careGiverList;
    }

    /**
     *
     * @param careGiver CareGiver object that wil be added to a Patients CareGiverList
     */
    // add_internal a careGiver to the list of careGivers
    public void addCareGiver(CareGiver careGiver) {
        careGiverList.add(careGiver.getElasticID());
    }

    /**
     *
     * @param careGiver Removes careGiver from a Patients CareGiverList
     */
    // remove a careGiver from the list of careGivers
    public void removeCareGiver(CareGiver careGiver) {
        careGiverList.remove(careGiver.getElasticID());
    }

    /**
     *
     * @return Returns the size of a Patients CareGiverList
     */
    public int size(){
        return careGiverList.size();
    }

    /**
     *
     * @param careGiver careGiver is the CareGiver that is being searched for in a CareGiverList
     * @return Returns true or false depending on whether careGiver is in the list
     */
    public boolean contains(CareGiver careGiver){
        return careGiverList.contains(careGiver.getElasticID());
    }

}

package com.example.loggerdoc;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class CareGiverList implements Serializable {

    private ArrayList<CareGiver> careGiverList;

    //PatientList constructor
    public CareGiverList(){
        careGiverList = new ArrayList<CareGiver>();
    }

    public Collection<CareGiver> getCareGivers(){
        return careGiverList;
    }

    // add a careGiver to the list of careGivers
    public void addCareGiver(CareGiver careGiver) {
        careGiverList.add(careGiver);
    }

    // remove a careGiver from the list of careGivers
    public void removeCareGiver(CareGiver careGiver) {
        careGiverList.remove(careGiver);
    }

    public int size(){
        return careGiverList.size();
    }

    public boolean contains(CareGiver careGiver){
        return careGiverList.contains(careGiver);
    }

}

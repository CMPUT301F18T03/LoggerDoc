package com.example.loggerdoc;

import android.util.SparseArray;
import com.example.loggerdoc.elasticclient.ElasticID;

import java.util.ArrayList;


public class GenericList<T extends ElasticID>{

    protected SparseArray<T> datalist;

    public GenericList(){
        datalist = new SparseArray<>();
    }
    /**
     * Add data to the generic list.
     *
     * @param data The data that needs to be added to the generic list.
     */
    public void add(T data) {
        datalist.put(data.getElasticID(),data);
    }

    /**
     * Load data to the generic list.
     *
     * @param data A SparseArray that will overwrite the existing internal data.
     */
    public void load(ArrayList<T> data) {
        datalist = new SparseArray<>();
        for(T x: data){
            datalist.put(x.getElasticID(),x);
        }
    }

    /**
     * Get data from the generic list.
     *
     * @param ElasticID The ID of the data that needs to be fetched to the generic list.
     */
    public T get(Integer ElasticID) {
        return datalist.get(ElasticID);
    }

    /**
     * Remove data from the generic list.
     *
     * @param data The data that needs to be updated from the generic list.
     */
    public void remove(T data) {
        datalist.remove(data.getElasticID());
    }


    /**
     * Checks if the generic list contains a specified data. Returns true if yes and false
     * otherwise.
     *
     * @param data The data that needs to be checked.
     * @return a boolean value (True or False)
     */
    public boolean contains(T data) {
        return datalist.get(data.getElasticID()) != null;
    }

    /**
     * Returns how many problems are stored in the ProblemList Object.
     * @return the size of the ProblemList.
     */
    public int size() {
        return datalist.size();
    }

    /**
     * Gets a copy of the stored data as an arraylist
     *
     * @return ArrayList of data type.
     */
    public ArrayList<T> getArray() {
        ArrayList<T> dataArrayList = new ArrayList<>();
        for(int num = 0;num < datalist.size();num++){
            dataArrayList.add(datalist.valueAt(num));
        }
        return dataArrayList;
    }
}

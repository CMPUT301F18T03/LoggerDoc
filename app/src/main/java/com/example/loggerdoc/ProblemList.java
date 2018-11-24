/* ProblemList.class
 *
 * Version 1.0
 *
 * Created 2018-10-24 by Nick Hoskins
 *
 * November 15, 2018
 */

package com.example.loggerdoc;

import com.example.loggerdoc.elasticclient.ElasticID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ProblemList implements Serializable {

    private ArrayList<Problem> problemArrayList;

    /**
     * Return a ProblemList object.
     */
    public ProblemList() {
        problemArrayList = new ArrayList<>();
    }

    /**
     * Return the ArrayList of ProblemList.
     *
     * @return The ArrayList of ProblemList.
     */
    public ArrayList <Problem> getProblemArrayList(){
        return this.problemArrayList;
    }

    /**
     * Add a problem to the ProblemList.
     *
     * @param problem The problem that needs to be added to the ProblemList.
     */
    public void add(Problem problem) {
        problemArrayList.add(problem);
    }

    /**
     * Remove a problem from the ProblemList.
     *
     * @param problem The problem needs to be removed from the ProblemList.
     */
    public void remove(Problem problem) {
        problemArrayList.remove(problem);
    }

    /**
     * Checks if the ProblemList contains a specified problem. Returns true if yes and false
     * otherwise.
     *
     * @param problem The problem that needs to be checked.
     * @return a boolean value (True or False)
     */
    public boolean contains(Problem problem) {
        return problemArrayList.contains(problem);
    }

    /**
     * Returns how many problems are stored in the ProblemList Object.
     * @return the size of the ProblemList.
     */
    public int size() {
        return problemArrayList.size();
    }
}



/* Created 2018-10-24 by Nick Hoskins
 *
 * ProblemList represents a list of a patient's problems.
 */

package com.example.loggerdoc;

import java.util.ArrayList;

public class ProblemList {

    private ArrayList<Problem> problemArrayList;

    public ProblemList() {
        problemArrayList = new ArrayList<>();
    }

    public ArrayList <Problem> getProblemArrayList(){
        return this.problemArrayList;
    }

    public void add(Problem problem) {
        problemArrayList.add(problem);
    }

    public void remove(Problem problem) {
        problemArrayList.remove(problem);
    }

    public boolean contains(Problem problem) {
        return problemArrayList.contains(problem);
    }

    public int size() {
        return problemArrayList.size();
    }
}

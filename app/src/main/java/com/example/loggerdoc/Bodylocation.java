package com.example.loggerdoc;

import java.io.Serializable;
import java.util.ArrayList;

public class Bodylocation implements Serializable {
    private ArrayList<Integer> FrontTuple;
    private ArrayList<Integer> BackTuple;

    public Bodylocation(){
        this.FrontTuple = new ArrayList<Integer>();
        this.BackTuple = new ArrayList<Integer>();

        this.FrontTuple.add(0);
        this.FrontTuple.add(0);

        this.BackTuple.add(0);
        this.BackTuple.add(0);
    }

    public void setFrontTuple(Integer x, Integer y) {
        this.FrontTuple.set(0,x);
        this.FrontTuple.set(1,y);

    }

    public void setBackTuple(Integer x,Integer y ){
        this.BackTuple.set(0,x);
        this.BackTuple.set(1,y);
    }

    public ArrayList<Integer> getFrontTuple(){
        return this.FrontTuple;
    }

    public ArrayList<Integer> getBackTuple() {
        return BackTuple;
    }
}

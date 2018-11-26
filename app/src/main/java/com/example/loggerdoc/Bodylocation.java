package com.example.loggerdoc;

import java.io.Serializable;
import java.util.ArrayList;

public class Bodylocation implements Serializable {
    private int FrontX;
    private int FrontY;
    private int BackX;
    private int BackY;

    public Bodylocation(){
        this.FrontX = 0;
        this.FrontY = 0;
        this.BackX = 0;
        this.BackX = 0;
    }

    public int getBackX() {
        return this.BackX;
    }

    public int getBackY(){
        return this.BackY;
    }

    public int getFrontX() {
        return this.FrontX;
    }

    public int getFrontY(){
        return this.FrontY;
    }

    public void setBackX(int backX) {
        this.BackX = backX;
    }

    public void setBackY(int backY) {
        this.BackY = backY;
    }

    public void setFrontX(int frontX) {
        this.FrontX = frontX;
    }

    public void setFrontY(int frontY) {
        this.FrontY = frontY;
    }
}

package com.example.loggerdoc;

import android.net.Uri;

import com.example.loggerdoc.elasticclient.ElasticID;

import java.io.File;
import java.io.Serializable;

/**
 * this class is responsible for if the user whats to use a body location from there device
 */
public class BodyLocationPhoto extends RecordPhoto implements Serializable,ElasticID {

    private String label;
    private int x = 0;
    private int y = 0;
    public String getLabel(){ return this.label; }
    public void setLabel(String newlabel){this.label = newlabel;}


    /**
     * setters for the x and y coordinate for the bl photo
     * @param newX
     */
    public void setX(int newX){ this.x = newX; }
    public void setY(int newY){ this.y = newY; }

    /**
     * getters for the x and y coordunate for the bl photo
     * @return
     */
    public int getX(){return this.x;}
    public int getY(){return this.y;}

}
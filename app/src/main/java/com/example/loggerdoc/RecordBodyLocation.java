package com.example.loggerdoc;

public class RecordBodyLocation {

    public boolean Front;
    public String Side;
    public String location;
    // has left or right side of the body a boolean for the front or the back and than a location like arm or leg
    // implementation may need to change a rough idea of how it could work

    public RecordBodyLocation(String side, Boolean front, String location) {
    }

    public boolean isFront(){ return Front;}
}

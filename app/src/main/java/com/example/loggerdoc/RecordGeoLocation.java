/* Created 2018-11-01 by Alexandra Tyrrell
 *
 *
 */

package com.example.loggerdoc;

public class RecordGeoLocation {

    private double latitude;
    private double longitude;

    RecordGeoLocation (double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public void setLatitude (double newLatitude){
        this.latitude = newLatitude;
    }

    public void setLongitude (double newLongitude){
        this.longitude = newLongitude;
    }
}

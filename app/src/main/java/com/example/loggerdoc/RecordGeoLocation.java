/* Created 2018-11-01 by Alexandra Tyrrell
 *
 *
 */

package com.example.loggerdoc;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class RecordGeoLocation implements Serializable {

    private double latitude;
    private double longitude;

    /**
     *
     * @param latLng Latitude of the geolocation
     */
    RecordGeoLocation (LatLng latLng){
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }

    /**
     *
     * @return Returns the latitude of a specific geolocation
     */
    public double getLatitude(){
        return this.latitude;
    }

    /**
     *
     * @return Returns the Longitude of the specific geolocation
     */
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

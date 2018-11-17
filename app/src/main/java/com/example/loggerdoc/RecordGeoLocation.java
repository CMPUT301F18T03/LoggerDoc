/* Created 2018-11-01 by Alexandra Tyrrell
 *
 *
 */

package com.example.loggerdoc;

public class RecordGeoLocation {

    private double latitude;
    private double longitude;

    /**
     *
     * @param latitude Latitude of the geolocation
     * @param longitude longitude of the geoocation
     */
    RecordGeoLocation (double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
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

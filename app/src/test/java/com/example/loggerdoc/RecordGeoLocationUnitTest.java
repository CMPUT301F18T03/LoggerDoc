package com.example.loggerdoc;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;


public class RecordGeoLocationUnitTest {

    private static final double VALID_LATITUDE = 80.00001;
    private static final double VALID_LONGITUDE = 80.00001;

    @Test
    public void testGetLongitude(){
        RecordGeoLocation geoLocation = new RecordGeoLocation(new LatLng(VALID_LATITUDE,VALID_LONGITUDE));
        assertEquals(80.00001, geoLocation.getLongitude());
    }

    @Test
    public void testGetLatitude(){
        RecordGeoLocation geoLocation = new RecordGeoLocation(new LatLng(VALID_LATITUDE, VALID_LONGITUDE));
        assertEquals(80.00001, geoLocation.getLatitude());
    }

    @Test
    public void testSetLongitude(){
        RecordGeoLocation geoLocation = new RecordGeoLocation(new LatLng(VALID_LATITUDE, VALID_LONGITUDE));
        geoLocation.setLongitude(30.001);
        assertEquals(30.001, geoLocation.getLongitude());
    }

    @Test
    public void testSetLatitude(){
        RecordGeoLocation geoLocation = new RecordGeoLocation(new LatLng(VALID_LATITUDE, VALID_LONGITUDE));
        geoLocation.setLatitude(40.0001);
        assertEquals(40.001, geoLocation.getLatitude());
    }
}

package com.example.loggerdoc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/*
 * This activity allows the user to select a geolocation to add_internal to a record. The activity will show
 * a google map on the screen with a marker set to either the device's current location or a default
 * location. The user can then drag the marker to their desired location and click the save button.
 */

public class ActivityAddGeolocation extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap locationMap;
    private  MarkerOptions options;
    private FusedLocationProviderClient mapFusedLocationProviderClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String TAG = "ActivityAddGeolocation";
    private static final LatLng DEFAULT_LOCATION = new LatLng(53.5232, -113.5263);
    private boolean mapLocationPermissionsGranted = false;
    private static final float DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_geolocation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Get the objects that were passed from the previous activity
        Intent firstIntent = getIntent();
        final Patient patient = (Patient) firstIntent.getSerializableExtra("Patient");
        final Record record = (Record) firstIntent.getSerializableExtra("Record");
        final int position = (int) firstIntent.getSerializableExtra("Position");

        //Check the location permissions
        checkLocationPermissions();

        //Initialize the save button and the onclick method for it.
        Button saveButton = (Button) findViewById(R.id.saveGeolocationButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return RecordGeoLocation to previous Activity
                RecordGeoLocation geoLocation = new RecordGeoLocation(options.getPosition());
                Intent intent = new Intent();
                intent.putExtra("geoLocation", geoLocation);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    //Check if we have permission to access fine location and coarse location
    private void checkLocationPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //if we have both locations then the map can be intialized
                mapLocationPermissionsGranted = true;
                initializeMap();
            } else {
                //We don't have permission to access coarse location so we request it
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            //We don't have permission to access fine locations so we resuest it
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    private void initializeMap() {
        //intialize the map object
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(ActivityAddGeolocation.this);
    }

    /*
     *Callback for the result of requesting permissions. If we don't have permissions than
     * mapLocationPermissionsGranted is set to false. If we have the permissions than
     * mapLocationPermissionsGranted is set to true.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mapLocationPermissionsGranted = false;

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        mapLocationPermissionsGranted = false;
                        return;
                    }
                }
                mapLocationPermissionsGranted = true;
                initializeMap();
            }
        }
    }

    /*
     * Callback interface for when the map is ready to be used. This method calls getDeviceLocation()
     * in an attempt to get the device's current location. Then the map will set the location and
     * zoom controls.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationMap = googleMap;

        if (mapLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationMap.setMyLocationEnabled(true);
            locationMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }

    /*
     * Method to get the current location of the device. This uses a FusedLocationProviderClient
     * in an attempt to get the current (last) location of the device. If it is successful, the
     * map will move to the current location and set a marker there. If it is unsuccessful, the
     * map will move to a default location and set a marker there.
     */
    private void getDeviceLocation() {
        mapFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mapLocationPermissionsGranted){

                final Task location = mapFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(),
                                            currentLocation.getLongitude()), DEFAULT_ZOOM,
                                        "My Location");

                        }
                        else if (task.isSuccessful()){
                            moveCamera(DEFAULT_LOCATION, DEFAULT_ZOOM, "Default Location");
                        }
                        else{
                            Toast.makeText(ActivityAddGeolocation.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        //Security exception if the permissions are not granted.
        catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    //move camera to specified location (latitude and longitude)
    private void moveCamera (LatLng latLng, float zoom, String title){

        locationMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        //set the marker to the set latitude and longitude, with a title.
        options = new MarkerOptions().position(latLng).title(title);
        options.draggable(true);
        locationMap.addMarker(options);
    }
}

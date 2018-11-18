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

public class ActivityAddGeolocation extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "ActivityAddGeolocation";
    private static final int locationPermissionRequestCode = 1234;
    private GoogleMap locationMap;
    private LatLng defaultLocation = new LatLng(53.5232, -113.5263);
    private  MarkerOptions options;

    private boolean mapLocationPermissionsGranted = false;
    private FusedLocationProviderClient mapFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_geolocation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent firstIntent = getIntent();
        final Patient patient = (Patient) firstIntent.getSerializableExtra("Patient");
        final Record record = (Record) firstIntent.getSerializableExtra("Record");

        checkLocationPermissions();
        Button saveButton = (Button) findViewById(R.id.saveGeolocationButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordGeoLocation geoLocation = new RecordGeoLocation(options.getPosition());
                Intent intent = new Intent(ActivityAddGeolocation.this, ActivityAddRecord.class);
                intent.putExtra("Patient", patient);
                intent.putExtra("Flag", "a");
                intent.putExtra("Record", record);
                intent.putExtra("geoLocation", geoLocation);
                startActivity(intent);
            }
        });
    }

    private void checkLocationPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mapLocationPermissionsGranted = true;
                initializeMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, locationPermissionRequestCode);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, locationPermissionRequestCode);
        }
    }


    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(ActivityAddGeolocation.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mapLocationPermissionsGranted = false;

        if (requestCode == locationPermissionRequestCode) {
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

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                        }
                        else if (task.isSuccessful()){
                            moveCamera(defaultLocation,
                                    DEFAULT_ZOOM,
                                    "Default Location");
                        }
                        else{
                            Toast.makeText(ActivityAddGeolocation.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }


    private void moveCamera (LatLng latLng, float zoom, String title){
        locationMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        options = new MarkerOptions().position(latLng).title(title);
        options.draggable(true);
        locationMap.addMarker(options);
    }
}

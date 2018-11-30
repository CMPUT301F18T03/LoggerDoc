package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

//This Activity lets the user see all the geolocations of all their records on a map.

public class ActivityProblemMap extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayList<Record> geoLocationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_map);

        //get the patient from the intent
        Intent intent = getIntent();
        int patientID = intent.getIntExtra("Patient",0);
        Patient patient = (Patient) UserListController.getUserList().get(patientID);

        geoLocationArrayList = new ArrayList<Record>();

        for (Record record : ProblemRecordListController.getRecordList().getArray()){
            if (record.getRecordGeoLocation() != null){
                geoLocationArrayList.add(record);
            }
        }

        //set the button to return to the Patient's homepage
        Button returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeProblemMap();
    }

    /**
     * @author = Alexandra Tyrrell
     *
     * Initialize the map.
     */
    private void initializeProblemMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.problemMap);
        mapFragment.getMapAsync(ActivityProblemMap.this);
    }

    /**
     * @author = Alexandra Tyrrell
     *
     * The callback interface implemented for when the Map is ready to used. In this activity, the
     * map will display all the geolocations for a patient. It will also enable the zoom
     * features for the GoogleMap and the long click listener.
     *
     * @param googleMap GoogleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap problemMap = googleMap;
        problemMap.getUiSettings().setZoomControlsEnabled(true);

        for (Record geoLocation : geoLocationArrayList){
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(geoLocation.getRecordGeoLocation().getLatitude(),
                            geoLocation.getRecordGeoLocation().getLongitude()))
                    .title(geoLocation.getTitle());
            problemMap.addMarker(marker);
        }
    }
}

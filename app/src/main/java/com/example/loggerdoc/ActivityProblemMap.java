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

public class ActivityProblemMap extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayList<Record> geoLocationArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_map);

        Intent intent = getIntent();
        int patientID = intent.getIntExtra("Patient",0);

        Patient patient = (Patient) UserListController.getUserList().get(patientID);

         geoLocationArrayList = new ArrayList<Record>();

        for (Record record : ProblemRecordListController.getRecordList().getArray()){
            if (record.getRecordGeoLocation() != null){
                geoLocationArrayList.add(record);
            }
        }

        Button returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeProblemMap();
    }

    private void initializeProblemMap() {
        //intialize the map object
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.problemMap);
        mapFragment.getMapAsync(ActivityProblemMap.this);
    }

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

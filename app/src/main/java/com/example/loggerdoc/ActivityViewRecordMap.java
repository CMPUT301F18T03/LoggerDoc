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

//This Activity lets the user see the geolocation of records for a selected problem on a map

public class ActivityViewRecordMap extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayList<Record> geoLocationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record_map);
    }

    @Override
    protected  void onResume(){
        super.onResume();

        Intent intent = getIntent();
        int problemID = intent.getIntExtra("Problem", 0);

        geoLocationArrayList = new ArrayList<Record>();

        for (Record record : ProblemRecordListController.getRecordList().getRecords(problemID)){
            if (record.getRecordGeoLocation() != null){
                geoLocationArrayList.add(record);
            }
        }

        //set the button to return to the Patient's homepage
        Button returnButton = (Button) findViewById(R.id.returnProblemButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeRecordMap();
    }

    /**
     * @author = Alexandra Tyrrell
     *
     * Initialize the map.
     */
    private void initializeRecordMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recordMap);
        mapFragment.getMapAsync(ActivityViewRecordMap.this);
    }

    /**
     * @author = Alexandra Tyrrell
     *
     * The callback interface implemented for when the Map is ready to used. In this activity, the
     * map will display all the geolocation records of a selected problem. It will also enable the
     * zoom features for the GoogleMap and the long click listener. This is the local view as
     * opposed to the global view.
     *
     * @param googleMap GoogleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap recordMap = googleMap;
        recordMap.getUiSettings().setZoomControlsEnabled(true);

        for (Record geoLocation : geoLocationArrayList){
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(geoLocation.getRecordGeoLocation().getLatitude(),
                            geoLocation.getRecordGeoLocation().getLongitude()))
                    .title(geoLocation.getTitle());
            recordMap.addMarker(marker);
        }
    }
}

package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityEditRecord extends AppCompatActivity implements OnMapReadyCallback {
    private int problemID;
    private int recordID;
    private GoogleMap editRecordMap;
    private Record record;
    private EditText editRecordTitle;
    private EditText editRecordComment;
    private  MarkerOptions options = null;

    private static final int DEFAULT_ZOOM = 15;
    private static final LatLng DEFAULT_LOCATION = new LatLng(53.5232, -113.5263);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = getIntent();
        problemID = intent.getIntExtra("Problem",0);
        recordID = intent.getIntExtra("Record",0);

        Problem problem = ProblemRecordListController.getProblemList().get(problemID);
        record = ProblemRecordListController.getRecordList().get(recordID);

        TextView problemTitle = (TextView) findViewById(R.id.editRecordProblemTitleView);
        problemTitle.setText(problem.getTitle());

        editRecordTitle = (EditText) findViewById(R.id.editRecordTitleView);
        editRecordTitle.setText(record.getTitle());

        editRecordComment = (EditText) findViewById(R.id.editRecordComment);
        editRecordComment.setText(record.getComment());

        initializeMap();
    }

    private void initializeMap() {
        //intialize the map object
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.editRecordMapView);
        mapFragment.getMapAsync(ActivityEditRecord.this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        editRecordMap = googleMap;
        editRecordMap.getUiSettings().setZoomControlsEnabled(true);
        editRecordMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                editRecordMap.clear();
                moveCamera(latLng, DEFAULT_ZOOM, record.getTitle());
            }
        });

        if (record.getRecordGeoLocation() != null){
            moveCamera(new LatLng(record.getRecordGeoLocation().getLatitude(),
                            record.getRecordGeoLocation().getLongitude()),
                    DEFAULT_ZOOM, record.getTitle());
        }
    }

    //move camera to specified location (latitude and longitude)
    private void moveCamera (LatLng latLng, float zoom, String title){

        editRecordMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        //set the marker to the set latitude and longitude, with a title.
        options = new MarkerOptions().position(latLng).title(title);
        editRecordMap.addMarker(options);
    }

    public void updateRecord(View v){
        record.setTitle(editRecordTitle.getText().toString());
        record.setComment(editRecordComment.getText().toString());

        if (options != null){
            RecordGeoLocation newGeoLocation = new RecordGeoLocation(options.getPosition());
            record.setRecordGeoLocation(newGeoLocation);
        }

        //TODO: Photos and Body Locations

        //TODO: Update the record in the recordList
        ProblemRecordListController.getRecordList().update(record,getApplicationContext());
        finish();
    }
}

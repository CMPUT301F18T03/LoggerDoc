package com.example.loggerdoc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityViewRecord extends AppCompatActivity implements OnMapReadyCallback {

    private Record record;
    private GoogleMap recordMap;
    private static final float DEFAULT_ZOOM = 15;
    private static final int EDIT_RECORD_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
    }

    @Override
    public void onResume(){
        super.onResume();

        Intent intent = getIntent();
        Problem problem = (Problem) intent.getSerializableExtra("Problem");
        int position = (int) intent.getSerializableExtra("Position");
        record  = problem.getRecordList().getRecordArrayList().get(position);

        TextView problemTitle = (TextView) findViewById(R.id.recordProblemTitleView);
        problemTitle.setText(problem.getTitle());

        TextView recordTitle = (TextView) findViewById(R.id.recordTitleView);
        recordTitle.setText(record.getTitle());

        TextView recordComment = (TextView) findViewById(R.id.recordCommentView);
        recordComment.setText(record.getComment());

        initializeMap();
        if (record.getRecordGeoLocation() != null){
            moveCamera(new LatLng(record.getRecordGeoLocation().getLatitude(),
                    record.getRecordGeoLocation().getLongitude()),
                    DEFAULT_ZOOM, "Record Location");
        }

    }

    private void initializeMap() {
        //intialize the map object
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.recordMapView);
        mapFragment.getMapAsync(ActivityViewRecord.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        recordMap = googleMap;
        recordMap.getUiSettings().setZoomControlsEnabled(true);
    }


    //move camera to specified location (latitude and longitude)
    private void moveCamera (LatLng latLng, float zoom, String title){

        recordMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        //set the marker to the set latitude and longitude, with a title.
        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        options.draggable(true);
        recordMap.addMarker(options);
    }

    private void goEditRecord(View view){
        Intent intent = new Intent(this, ActivityEditRecord.class);
        intent.putExtra("Record", record);
        startActivityForResult(intent, EDIT_RECORD_RESULT);
    }

}

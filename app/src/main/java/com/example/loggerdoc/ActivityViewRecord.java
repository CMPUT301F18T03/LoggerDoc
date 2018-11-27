package com.example.loggerdoc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityViewRecord extends AppCompatActivity implements OnMapReadyCallback {

    private int problemID;
    private int recordID;
    private Record record;
    private GoogleMap recordMap;
    private static final float DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
    }

    @Override
    public void onResume(){
        super.onResume();

        Intent intent = getIntent();
        problemID = intent.getIntExtra("Problem", 0);
        recordID = intent.getIntExtra("Record", 0);
        Problem problem = ProblemRecordListController.getProblemList().get(problemID);
        record  = ProblemRecordListController.getRecordList().get(recordID);

        TextView problemTitle = (TextView) findViewById(R.id.recordProblemTitleView);
        problemTitle.setText(problem.getTitle());

        TextView recordTitle = (TextView) findViewById(R.id.recordTitleView);
        recordTitle.setText(record.getTitle());

        TextView recordComment = (TextView) findViewById(R.id.recordCommentView);
        recordComment.setText(record.getComment());

        initializeMap();
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
        recordMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                recordMap.clear();
                moveCamera(latLng, DEFAULT_ZOOM, "");
            }
        });

        if (record.getRecordGeoLocation() != null){
            moveCamera(new LatLng(record.getRecordGeoLocation().getLatitude(),
                            record.getRecordGeoLocation().getLongitude()),
                    DEFAULT_ZOOM, "Record Location");
        }
    }


    //move camera to specified location (latitude and longitude)
    private void moveCamera (LatLng latLng, float zoom, String title){

        recordMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        //set the marker to the set latitude and longitude, with a title.
        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        recordMap.addMarker(options);
    }

    private void goEditRecord(View view){
        Intent intent = new Intent(this, ActivityEditRecord.class);
        intent.putExtra("Problem", problemID);
        intent.putExtra("Record", recordID);
        startActivity(intent);
    }

    private void goDeleteRecord (View view){
        //Show an alert dialog to ask for user's confirmation whether they would like to delete
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you would like to delete this record?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProblemRecordListController.getRecordList().remove(record);
                finish();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

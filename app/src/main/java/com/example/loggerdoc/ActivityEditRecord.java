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

/*
 * This class displays the record that the user has selected and it allows them to edit the title,
 * the comment, the geolocation, any photos, and/or body locations.
 */

public class ActivityEditRecord extends AppCompatActivity implements OnMapReadyCallback {
    private int problemID;
    private int recordID;
    private GoogleMap editRecordMap;
    private Record record;
    private EditText editRecordTitle;
    private EditText editRecordComment;
    private MarkerOptions options = null;

    private static final int DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //Get the correct problem and record from the intent, and initialize the text fields accordingly.
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

    /**
     * @author = Alexandra Tyrrell
     *
     * Initialize the map.
     */
    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.editRecordMapView);
        mapFragment.getMapAsync(ActivityEditRecord.this);

    }

    /**
     * @author = Alexandra Tyrrell
     *
     * The callback interface implemented for when the Map is ready to used. In this activity, the
     * map will display the geolocation of the record if it has one. It will also enable the zoom
     * features for the GoogleMap and the long click listener. The long click listener will add a
     * marker to the map showing the new geolocation that the user would like to save.
     *
     * @param googleMap GoogleMap
     */
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

    /**
     * @author = Alexandra Tyrrell
     *
     * The method will change the position of the camera to show the specified latitude and
     * longitude coordinate. It will also add a marker to the specified location with a title.
     */
    private void moveCamera (LatLng latLng, float zoom, String title){

        editRecordMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        //set the marker to the set latitude and longitude, with a title.
        options = new MarkerOptions().position(latLng).title(title);
        editRecordMap.addMarker(options);
    }

    /**
     * @author = Alexandra Tyrrell and Tristan Glover
     *
     * The method will update the record with changes from the title, comment, geolocation, photos
     * and/or body locations. It will update it both on the server and the cache.
     */
    public void updateRecord(View v){
        record.setTitle(editRecordTitle.getText().toString());
        record.setComment(editRecordComment.getText().toString());

        if (options != null){
            RecordGeoLocation newGeoLocation = new RecordGeoLocation(options.getPosition());
            record.setRecordGeoLocation(newGeoLocation);
        }

        //TODO: Photos and Body Locations

        ProblemRecordListController.getRecordList().update(record,getApplicationContext());
        finish();
    }
}

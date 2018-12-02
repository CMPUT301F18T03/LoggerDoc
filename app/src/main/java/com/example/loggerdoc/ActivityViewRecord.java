package com.example.loggerdoc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;

/*
 * This class displays the selected record. If the user is a patient, he/she can edit or delete the
 * record. If the user is a caregiver, he/she can only view the record's geolocation, photos or/and
 * body-location.
 */

public class ActivityViewRecord extends AppCompatActivity implements OnMapReadyCallback {

    private int problemID;
    private int recordID;
    private Record record;
    private GoogleMap recordMap;

    //public static RecordPhotoList photoList = new RecordPhotoList();
    //public  static BodyLocationPhotoList blPhotoList = new BodyLocationPhotoList();
    public static Bodylocation bodylocation = new Bodylocation();
    private static final float DEFAULT_ZOOM = 15;
    private static final int REMOVE_BL = 2000;

    public static ArrayList<BodyLocationPhoto> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
    }

    @Override
    public void onResume() {
        super.onResume();
        Button editRecordButton = (Button) findViewById(R.id.editRecordButton);
        Button deleteRecordButton = (Button) findViewById(R.id.deleteRecordButton);

        /*
         * Check whether the currently logged in user is a patient or a caregiver. If a caregiver,
         * make the edit and delete record buttons invisible.
         */
        User user = UserListController.getUserList().get(UserListController.getCurrentUserID());
        if (user.getRole().equals("Caregiver")) {
            editRecordButton.setVisibility(View.INVISIBLE);
            deleteRecordButton.setVisibility(View.INVISIBLE);
        } else {
            editRecordButton.setVisibility(View.VISIBLE);
            deleteRecordButton.setVisibility(View.VISIBLE);
        }

        //Get the correct problem and record from the intent, and initialize the text fields accordingly.
        Intent intent = getIntent();
        problemID = intent.getIntExtra("Problem", 0);
        recordID = intent.getIntExtra("Record", 0);
        Problem problem = ProblemRecordListController.getProblemList().get(problemID);
        record = ProblemRecordListController.getRecordList().get(recordID);

        TextView problemTitle = (TextView) findViewById(R.id.recordProblemTitleView);
        problemTitle.setText(problem.getTitle());

        TextView recordTitle = (TextView) findViewById(R.id.recordTitleView);
        recordTitle.setText(record.getTitle());

        TextView recordComment = (TextView) findViewById(R.id.recordCommentView);
        recordComment.setText(record.getComment());

        Button showimages = (Button) findViewById(R.id.showRecordImage);
        Button showBodyLocation = (Button) findViewById(R.id.showBodyLoc);

        list = ProblemRecordListController.getRecordPhotoList().getBodyLocationPhotos();
        bodylocation = record.getBodylocation();

        // Log.i("THIS_TAG", String.valueOf(photoList.getPhoto(0).getPhoto()));
        showimages.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(v.getContext(), ActivityPhotoGrid.class);
                startActivity(intent);

            }
        });
        showBodyLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityViewBodyLocation.class);

                startActivityForResult(intent, REMOVE_BL);
            }
        });

        initializeMap();
    }

    /*
     * @author = Alexandra Tyrrell
     *
     * Initialize the map.
     */
    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recordMapView);
        mapFragment.getMapAsync(ActivityViewRecord.this);
    }

    /*
     * @author = Alexandra Tyrrell
     *
     * The callback interface implemented for when the Map is ready to used. In this activity, the
     * map will display the geolocation of the record if it has one. It will also enable the zoom
     * features for the GoogleMap.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        recordMap = googleMap;
        recordMap.getUiSettings().setZoomControlsEnabled(true);

        if (record.getRecordGeoLocation() != null) {
            recordMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(record.getRecordGeoLocation().getLatitude(),
                            record.getRecordGeoLocation().getLongitude()), DEFAULT_ZOOM));

            //set the marker to the set latitude and longitude, with a title.
            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(record.getRecordGeoLocation().getLatitude(),
                            record.getRecordGeoLocation().getLongitude()))
                    .title("Record Location");
            recordMap.addMarker(options);
        }
    }

    /*
     * @author = Alexandra Tyrrell
     *
     * Change to the Edit Record Activity.
     */
    public void goEditRecord(View view) {
        Intent intent = new Intent(this, ActivityEditRecord.class);
        intent.putExtra("Problem", problemID);
        intent.putExtra("Record", recordID);
        startActivity(intent);
    }

    /*
     * @author = Alexandra Tyrrell
     *
     * Show an alert dialog to ask for user's confirmation whether they would like to delete the
     * selected record.
     */
    public void goDeleteRecord(View view) {
        //Show an alert dialog to ask for user's confirmation whether they would like to delete
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you would like to delete this record?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProblemRecordListController.getRecordList().remove(record, getApplicationContext());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REMOVE_BL && resultCode == RESULT_OK) {

            int index = data.getIntExtra("REMOVE", 0);

            if (index == 1) {
                record.getBodylocation().setFrontX(0);
                record.getBodylocation().setFrontY(0);
                if (list.size() > 0) {
                    record.removeBlPhoto(list.get(0));
                    ProblemRecordListController.getRecordPhotoList().removePhoto(list.get(0));
                }
                ProblemRecordListController.getRecordList().update(record, getApplicationContext());
            }
            if (index == 2) {
                record.getBodylocation().setBackX(0);
                record.getBodylocation().setBackY(0);
                if (list.size() == 2) {
                    record.removeBlPhoto(list.get(1));
                    ProblemRecordListController.getRecordPhotoList().removePhoto(list.get(1));

                }
                else if (list.size() == 1) {
                    record.removeBlPhoto(list.get(0));
                    ProblemRecordListController.getRecordPhotoList().removePhoto(list.get(0));

                }


                ProblemRecordListController.getRecordList().update(record, getApplicationContext());
            }


        }
    }
}



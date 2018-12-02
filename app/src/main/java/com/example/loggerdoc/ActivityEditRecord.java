package com.example.loggerdoc;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private static String PhotoPath;

    private static final int DEFAULT_ZOOM = 15;

    static final int REQUEST_IMAGE_CAPTURE_RECORD = 1000;
    static final int GALLERY_REQUEST_RECORD = 1001;
    static final int BODY_LOCATION_REQUEST = 1003;
    static final int BODY_LOCATION_GALLARY_REQUEST = 1004;
    static final int LABEL_REQUEST = 1005;
    private static final int CAMERA_PERMISSION_REQUEST = 100;
    private static final int STORAGE_PERMISSION_REQUEST = 200;
    private boolean cameraPermissionsGranted = false;
    private boolean pictureStoragePermissionsGranted = false;


    private RecordPhotoList recordPhotoList;
    private BodyLocationPhotoList blphotos;
    private Bodylocation bodylocation = new Bodylocation();
    private BodyLocationPhoto blPhoto;// = new BodyLocationPhoto();


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

        bodylocation = record.getBodylocation();
        blphotos = record.getBlPhotoList();
        recordPhotoList = record.getRecordPhotoList();

        Button recordGallery = findViewById(R.id.RecordGallary);
        final Button recordCamera = findViewById(R.id.RecordCamera);
        Button BodyLocationButton = findViewById(R.id.BodyLocation);
        Button BodyLocationGallery = findViewById(R.id.BodyLocationGallary);

        recordGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(recordPhotoList.size() < 10){
                    checkStoragePermission();
                }
                else{
                    Toast.makeText(ActivityEditRecord.this, "You already have 10 recordPhotos for this record", Toast.LENGTH_SHORT).show();

                }
            }
        });

        recordCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                checkCameraPermission();
            }
        });

        BodyLocationButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent  = new Intent(v.getContext(), ActivityBodyLocation.class);
                startActivityForResult(intent, BODY_LOCATION_REQUEST);
            }
        });

        BodyLocationGallery.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // get comment from user than when it returns call the gallery app for user bl they already have
                if (blphotos.size()<2){
                    Intent intent = new Intent(v.getContext(), ActivityBlLabel.class);
                    startActivityForResult(intent, LABEL_REQUEST);}
                else{
                    Toast.makeText(ActivityEditRecord.this, "You already have 2 bl photos for this record", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        record.setBodylocation(bodylocation);
        record.setBlPhotoList(blphotos);
        record.setRecordPhotoList(recordPhotoList);

        ProblemRecordListController.getRecordList().update(record,getApplicationContext());
        finish();
    }
    /**
     * @author = Alexandra Tyrrell
     *
     * Check if we have permission to access external storage (photos). If not, we need to
     * request the permission.
     */
    private void checkStoragePermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            pictureStoragePermissionsGranted = true;
            GalleryIntent(GALLERY_REQUEST_RECORD);
        }
        else{
            ActivityCompat.requestPermissions(this, permissions, STORAGE_PERMISSION_REQUEST);
        }
    }

    /**
     * @author = Alexandra Tyrrell
     *
     * Check if we have permission to access the camera. If not, we need to request the camera
     * permission.
     */
    private void checkCameraPermission(){
        String[] permissions = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraPermissionsGranted = true;
            dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE_RECORD);
        }
        else{
            ActivityCompat.requestPermissions(this, permissions, CAMERA_PERMISSION_REQUEST);
        }
    }

    /**
     * @author = Alexandra Tyrrell
     *
     * Callback interface for the result of requesting permissions. If we don't have the camera permission
     * than cameraPermissionsGranted is set to false. If we have the camera permission than
     * cameraPermissionsGranted is set to true. Same thing for pictureStoragePermissionsGranted.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        cameraPermissionsGranted = false;
        pictureStoragePermissionsGranted = false;

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        cameraPermissionsGranted= false;
                        Toast.makeText(ActivityEditRecord.this, "Do not have permission to access camera", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                cameraPermissionsGranted= true;
                dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE_RECORD);
            }
        }

        if (requestCode == STORAGE_PERMISSION_REQUEST){
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        pictureStoragePermissionsGranted= false;
                        Toast.makeText(ActivityEditRecord.this, "Do not have permission to access storage/photos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                pictureStoragePermissionsGranted= true;
                GalleryIntent(GALLERY_REQUEST_RECORD);
            }
        }

    }

    private void GalleryIntent(int request) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, request);
    }

    private void dispatchTakePictureIntent(int request) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch(IOException ex){


            }
            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(this,"com.example.loggerdoc.fileprovider",photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                //Log.i("THIS_IS_TAG", "exception");

                startActivityForResult(takePictureIntent, request);

            }

        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        PhotoPath = image.getAbsolutePath();
        Log.i("THIS_IS_TAG", String.valueOf(image));

        return image;
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE_RECORD && resultCode == RESULT_OK) {
            File path = new File(PhotoPath);
            //Uri uri = Uri.fromFile(f);
            RecordPhoto photo = new RecordPhoto();
            photo.setPhoto(path);
            recordPhotoList.addPhoto(photo);
            PhotoPath = null;

        }

        if (requestCode == LABEL_REQUEST && resultCode == RESULT_OK) {
            String label = data.getStringExtra("THELABEL");
            blPhoto = new BodyLocationPhoto();

            blPhoto.setLabel(label);
            // Log.i("THIS_TAG", blPhoto.getLabel());

            GalleryIntent(BODY_LOCATION_GALLARY_REQUEST);

        }

        if (requestCode == BODY_LOCATION_GALLARY_REQUEST && resultCode == RESULT_OK) {
            final Uri uri = data.getData();
            File path = new File(getRealPathFromURI(uri));
            blPhoto.setPhoto(path);
            Log.i("THIS_TAG", blPhoto.getLabel());

            blphotos.add(blPhoto);

        }

        if (requestCode == GALLERY_REQUEST_RECORD && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            File path = new File(getRealPathFromURI(imageUri));
            RecordPhoto photo = new RecordPhoto();
            photo.setPhoto(path);
            recordPhotoList.addPhoto(photo);
            Log.i("THIS_TAG", String.valueOf(path));
        }
        if (requestCode == BODY_LOCATION_REQUEST && resultCode == Activity.RESULT_OK) {
            ArrayList<Integer> location = data.getIntegerArrayListExtra("BODYLOCATION");
            bodylocation.setFrontX(location.get(0));
            bodylocation.setFrontY(location.get(1));
            bodylocation.setBackX(location.get(2));
            bodylocation.setBackY(location.get(3));

        }
    }
}

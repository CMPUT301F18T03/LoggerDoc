package com.example.loggerdoc;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * This class lets the user add a record to a problem. The record can have a geolocation, photo,
 * bodylocation and/or a comment. The record is then added to the record list.
 */

public class ActivityAddRecord extends AppCompatActivity {

    private static String PhotoPath;
    private EditText recordTitleText;
    private EditText recordCommentText;
    private RecordGeoLocation geoLocation;
    private Record record;
    private static int problemID;


    private static final int ERROR_DIALOG_REQUEST = 9001;
    static final int REQUEST_IMAGE_CAPTURE_RECORD = 1000;
    static final int GALLERY_REQUEST_RECORD = 1001;
    static final int ADD_GEOLOCATION_RESULT = 1002;
    static final int BODY_LOCATION_REQUEST = 1003;
    static final int BODY_LOCATION_GALLARY_REQUEST = 1004;
    static final int LABEL_REQUEST = 1005;
    private static final int CAMERA_PERMISSION_REQUEST = 100;
    private static final int STORAGE_PERMISSION_REQUEST = 200;
    private boolean cameraPermissionsGranted = false;
    private boolean pictureStoragePermissionsGranted = false;

    private ArrayList<RecordPhoto> photos = new ArrayList<RecordPhoto>();
    private ArrayList<BodyLocationPhoto> blphotos = new ArrayList<BodyLocationPhoto>();
    private Bodylocation bodylocation = new Bodylocation();
    private BodyLocationPhoto blPhoto;// = new BodyLocationPhoto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //get the objects that were passed from the previous activity
        Intent intent = getIntent();
        problemID = (int) intent.getSerializableExtra("Problem");

        recordTitleText = (EditText) findViewById(R.id.record_title_text);
        recordCommentText = (EditText) findViewById(R.id.record_comment_text);

        Button recordGallery = findViewById(R.id.gallery_button);
        Button recordCamera = findViewById(R.id.Camera_button);
        Button bodyLocationButton = findViewById(R.id.body_location_button);
        Button bodyLocationGallery = findViewById(R.id.BodyLocationGallary);

        recordGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                checkStoragePermission();
            }
        });

        recordCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                checkCameraPermission();
            }
        });

        bodyLocationButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent  = new Intent(v.getContext(), ActivityBodyLocation.class);
                startActivityForResult(intent, BODY_LOCATION_REQUEST);
            }
        });

        bodyLocationGallery.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // get comment from user than when it returns call the gallery app for user bl they already have
                if (blphotos.size()<2){
                    Intent intent = new Intent(v.getContext(), ActivityBlLabel.class);
                    startActivityForResult(intent, LABEL_REQUEST);}
                else{
                    Toast.makeText(ActivityAddRecord.this, "You already have 2 bl photos for this record", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (isServicesOkay()){
          initialize();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE_RECORD && resultCode == RESULT_OK) {
            File path = new File(PhotoPath);
            //Uri uri = Uri.fromFile(f);
            RecordPhoto photo = new RecordPhoto();
            photo.setPhoto(path);
            photos.add(photo);
            PhotoPath = null;

        }

        if (requestCode == LABEL_REQUEST && resultCode == RESULT_OK){
            String label = data.getStringExtra("THELABEL");
            blPhoto = new BodyLocationPhoto();

            blPhoto.setLabel(label);
           // Log.i("THIS_TAG", blPhoto.getLabel());

            GalleryIntent(BODY_LOCATION_GALLARY_REQUEST);

        }

        if(requestCode == BODY_LOCATION_GALLARY_REQUEST && resultCode == RESULT_OK){
            final Uri uri = data.getData();
            File path =  new File(getRealPathFromURI(uri));
            blPhoto.setPhoto(path);
            Log.i("THIS_TAG", blPhoto.getLabel());

            blphotos.add(blPhoto);

        }

        if (requestCode == GALLERY_REQUEST_RECORD && resultCode == RESULT_OK){
            final Uri imageUri = data.getData();
            File path = new File(getRealPathFromURI(imageUri));
            RecordPhoto photo = new RecordPhoto();
            photo.setPhoto(path);
            photos.add(photo);
            Log.i("THIS_TAG", String.valueOf(path));
        }

        if (requestCode == ADD_GEOLOCATION_RESULT && resultCode == RESULT_OK) {
            geoLocation = (RecordGeoLocation) data.getSerializableExtra("geoLocation");
            TextView recordGeoText = (TextView) findViewById(R.id.recordGeoText);
            recordGeoText.setVisibility(View.VISIBLE);
            recordGeoText.setText("Geolocation Saved:\n" + "Lat: "+
                    String.valueOf(geoLocation.getLatitude()) + "\n" + "Lon: "+
                    String.valueOf(geoLocation.getLongitude()));
        }

        if(requestCode == BODY_LOCATION_REQUEST && resultCode == Activity.RESULT_OK){
            ArrayList<Integer> location = data.getIntegerArrayListExtra("BODYLOCATION");
            bodylocation.setFrontX(location.get(0));
            bodylocation.setFrontY(location.get(1));
            bodylocation.setBackX(location.get(2));
            bodylocation.setBackY(location.get(3));
            Log.i("THISTAG", String.valueOf(location.get(0)));
            Log.i("THISTAG", String.valueOf(location.get(1)));
            Log.i("THISTAG", String.valueOf(location.get(2)));
            Log.i("THISTAG", String.valueOf(location.get(3)));


        }
    }

    /**
     *   @author = Alexandra Tyrrell
     *
     *   Intialize the map button.
     */
    private void initialize(){
        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAddRecord.this, ActivityAddGeolocation.class);
                intent.putExtra("Problem",problemID);
                startActivityForResult(intent, ADD_GEOLOCATION_RESULT);
            }
        });
    }

    /**
     * @author = Alexandra Tyrrell
     *
     * Create the record and add it to the problem list. This method will also add photos, body
     * locations and geolocations.
     *
     * @param v View
     */
    public void createRecord (View v){

        //Check if the Record Title is empty
        boolean emptyTitle = checkEmptyString(recordTitleText.getText().toString());

        if (emptyTitle){
            //if the title is empty, show an error message
            showAlertDialog("Error: Empty Title","Please fill in the title field");
        }

        else{
            //Create a new record
           record = new Record (recordTitleText.getText().toString(),problemID);
           record.setComment(recordCommentText.getText().toString());
           //add_internal a geolocation to the record
           if (geoLocation != null) {
               record.setRecordGeoLocation(geoLocation);
           }

           //add_internal record to the problem

            if (photos.size() != 0) {
                for (int i = 0; i<photos.size(); i++){
                    record.getRecordPhotoList().addPhoto(photos.get(i));
                }

           }
           if (blphotos.size() != 0){
               for(int i =0; i<blphotos.size(); i++){
                   record.getBlPhotoList().add(blphotos.get(i));
               }
           }
           record.setBodylocation(bodylocation);
           ProblemRecordListController.getRecordList().add(record,getApplicationContext());

            finish();
        }
    }

    /**
     * @author Alexandra Tyrrell
     *
     * This method checks whether GooglePlayServices is okay to be used. It checks if we can connect
     * to the map or not.
     *
     * @return true if everything is okay
     */
    public boolean isServicesOkay(){
        //check if GooglePlayServices is Available
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ActivityAddRecord.this);

        if (available == ConnectionResult.SUCCESS){
            //everything is okay and the user can make map requests
            return true;
        }

        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but it can be resolved
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ActivityAddRecord.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }

        else{
            //can't fix the error
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * @author Alexandra Tyrrell
     *
     * This method checks whether a string is empty or not. It returns true if the string is empty
     * and false otherwise.
     *
     * @param string String
     */
    public boolean checkEmptyString(String string){
        if (string.length() == 0){
            return true;
        }
        return false;
    }

    /**
     * @author Alexandra Tyrrell
     *
     * Uses the Dialog Problem class to show an error dialog with the specified string and message.
     *
     * @param title String
     * @param message String
     */
    private void showAlertDialog( String title, String message){
        Bundle messageArgs = new Bundle();
        messageArgs.putString(DialogProblem.TITLE_ID, title);
        messageArgs.putString(DialogProblem.MESSAGE_ID, message);

        DialogProblem dialog = new DialogProblem();
        dialog.setArguments(messageArgs);
        dialog.show(getSupportFragmentManager(), "error_dialog");
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
                Log.i("THIS_IS_TAG", "exception");

            }
            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(this,"com.example.loggerdoc.fileprovider",photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                //Log.i("THIS_IS_TAG", "exception");

                startActivityForResult(takePictureIntent, request);

            }
            //startActivityForResult(takePictureIntent, request);
        }
    }

    /**
     * creates a file to store image from the camera
     * @return path to file
     * @throws IOException
     */

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
                        Toast.makeText(ActivityAddRecord.this, "Do not have permission to access camera", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ActivityAddRecord.this, "Do not have permission to access storage/photos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                pictureStoragePermissionsGranted= true;
                GalleryIntent(GALLERY_REQUEST_RECORD);
            }
        }

    }
}

package com.example.loggerdoc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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

    private ArrayList<RecordPhoto> photos = new ArrayList<RecordPhoto>();
    private Bodylocation bodylocation = new Bodylocation();
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

        recordGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                GalleryIntent(GALLERY_REQUEST_RECORD);
            }
        });

        recordCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE_RECORD);
            }
        });

        bodyLocationButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent  = new Intent(v.getContext(), ActivityBodyLocation.class);
                startActivityForResult(intent, BODY_LOCATION_REQUEST);
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
            File f = new File(PhotoPath);
            Uri uri = Uri.fromFile(f);
            RecordPhoto photo = new RecordPhoto();
            photo.setPhoto(uri);
            photos.add(photo);
            PhotoPath = null;

        }

        if (requestCode == GALLERY_REQUEST_RECORD && resultCode == RESULT_OK){
            final Uri imageUri = data.getData();
            RecordPhoto photo = new RecordPhoto();
            photo.setPhoto(imageUri);
            photos.add(photo);
            Log.i("THIS_IS_TAG", "onActivityResult: "+ photos.size());
        }

        if (requestCode == ADD_GEOLOCATION_RESULT && resultCode == RESULT_OK) {
            geoLocation = (RecordGeoLocation) data.getSerializableExtra("geoLocation");
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

    private void initialize(){
        //initialize the map button
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

    //Called when the user presses the "Create" button on the ActivityAddRecord
    public void createRecord (View v){

        //Check if the Record Title is empty
        boolean emptyTitle = checkEmptyString(recordTitleText.getText().toString());

        if (emptyTitle){
            //if the title is empty, show an error message
            showAlertDialog("Error: Empty Title","Please fill in the title field");
        }

        else{
            //Create a new record
           record = new Record (recordTitleText.getText().toString(),2147483647);
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
           record.setBodylocation(bodylocation);
            ProblemRecordListController.getRecordList().add(record,getApplicationContext());
            //patient.getProblems().getArray().get(position).getRecordList().getArray().add_internal(record);
        }

        //Add record to problem list
        Intent intent = new Intent();
        intent.putExtra("Record", record);
        setResult(RESULT_OK, intent);
        finish();
    }

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

    //check if the given string is empty or not
    public boolean checkEmptyString(String string){
        if (string.length() == 0){
            return true;
        }
        return false;
    }

    //Show an error alert dialog using the class DialogProblem()
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

}

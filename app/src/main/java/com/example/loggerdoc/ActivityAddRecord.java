package com.example.loggerdoc;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class ActivityAddRecord extends AppCompatActivity {

    private EditText recordTitleText;
    private RecordGeoLocation geoLocation;
    private TextView latitudeText;
    private TextView longitudeText;
    private Record record;
    private static Patient patient;
    private static int position;
    private static final int ERROR_DIALOG_REQUEST = 9001;

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
        patient = (Patient) intent.getSerializableExtra("Patient");
        position = (int) intent.getSerializableExtra("Position");
        String flag = (String) intent.getSerializableExtra("Flag");

        recordTitleText = (EditText) findViewById(R.id.record_title_text);
        latitudeText = (TextView) findViewById(R.id.latitude_text);
        longitudeText = (TextView) findViewById(R.id.longitude_text);

        if (flag.equals("b")){
            //the previous activity was ActivityAddGeolocation
            geoLocation = (RecordGeoLocation) intent.getSerializableExtra("geoLocation");
            latitudeText.setText("Latitude: " + String.valueOf(geoLocation.getLatitude()));
            longitudeText.setText( "Longitude: " + String.valueOf(geoLocation.getLongitude()));
        }

        if (isServicesOkay()){
          initialize();
        }
    }

    private void initialize(){
        //initialize the map button
        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAddRecord.this, ActivityAddGeolocation.class);
                intent.putExtra("Patient", patient);
                intent.putExtra("Position", position);
                intent.putExtra("Record", record);
                startActivity(intent);
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
           record = new Record (recordTitleText.getText().toString());

           //add a geolocation to the record
           if (geoLocation != null) {
               record.setRecordGeoLocation(geoLocation);
           }

           //add record to the problem
           patient.getProblems().getProblemArrayList().get(position).getRecordList().getRecordArrayList().add(record);
        }

        //Change to ActivityViewProblem
        Intent intent = new Intent(ActivityAddRecord.this, ActivityViewProblem.class);
        intent.putExtra("Patient", patient);
        intent.putExtra("Position", position);
        startActivity(intent);
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

}

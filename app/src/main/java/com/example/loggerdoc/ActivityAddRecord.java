package com.example.loggerdoc;

import android.app.Dialog;
import android.content.Intent;
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


public class ActivityAddRecord extends AppCompatActivity {

    private EditText recordTitleText;
    private RecordGeoLocation geoLocation;
    private TextView geoLocationText;
    private Record record;
    private static Patient patient;
    private static final String TAG = "ActivityAddRecord";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("Patient");
        String flag = (String) intent.getSerializableExtra("Flag");

        recordTitleText = (EditText) findViewById(R.id.record_title_text);
        geoLocationText = (TextView) findViewById(R.id.Geolocation_text);

        if (flag.equals("b")){
            geoLocation = (RecordGeoLocation) intent.getSerializableExtra("geoLocation");
            geoLocationText.setText("Latitude: " + String.valueOf(geoLocation.getLatitude()) + " Longitude: " + String.valueOf(geoLocation.getLongitude()));
        }

        if (!flag.equals("a")){
            record = (Record) intent.getSerializableExtra("Record");
            recordTitleText.setText(record.getTitle());
        }

        if (isServicesOkay()){
          initialize();
        }
    }

    private void initialize(){
        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAddRecord.this, ActivityAddGeolocation.class);
                intent.putExtra("Patient", patient);
                intent.putExtra("Record", record);
                startActivity(intent);
            }
        });
    }

    public void createRecord (View v){

        //Check if the Record Title is empty
        boolean emptyTitle = checkEmptyString(recordTitleText.getText().toString());

        if (emptyTitle){
            showAlertDialog("Error: Empty Title","Please fill in the title field");
        }

        else{
           record = new Record (recordTitleText.getText().toString());
           if (geoLocation == null) {
               record.setRecordGeoLocation(geoLocation);
           }
        }
    }

    public boolean isServicesOkay(){
        Log.d(TAG, "isServicesOkay: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ActivityAddRecord.this);

        if (available == ConnectionResult.SUCCESS){
            //everything is okay and the user can make map requests
            Log.d(TAG, "google play services is working");
            return true;
        }

        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ActivityAddRecord.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }

        else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean checkEmptyString(String string){
        if (string.length() == 0){
            return true;
        }
        return false;
    }

    private void showAlertDialog( String title, String message){
        Bundle messageArgs = new Bundle();
        messageArgs.putString(DialogProblem.TITLE_ID, title);
        messageArgs.putString(DialogProblem.MESSAGE_ID, message);

        DialogProblem dialog = new DialogProblem();
        dialog.setArguments(messageArgs);
        dialog.show(getSupportFragmentManager(), "error_dialog");
    }

}

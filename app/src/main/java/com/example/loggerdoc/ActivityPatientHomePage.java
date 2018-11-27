package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityPatientHomePage extends AppCompatActivity {
    private Patient patient;
    private Integer patient_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__home__page);

        // Get the userID being passed from ActivityLogin.java
        Intent intent = getIntent();
        patient_ID = intent.getIntExtra("Patient",0);
        patient = (Patient) UserListController.getUserList().get(patient_ID);
        ProblemRecordListController.loadUser(patient_ID,getApplicationContext());

        // Set the textview to display the username
        TextView usernameTextView = findViewById(R.id.patient_username_text);
        usernameTextView.setText(patient.getUserID());


        Log.d("TAG", "userID = " + patient.getUserID());
        Log.d("TAG", "email = " + patient.getEmailAddress());
        Log.d("TAG", "phone = " + patient.getPhoneNumber());
        Log.d("TAG", "CareGiver List = " + patient.getCareGivers());

        // find the patient object using the given userId that was passed through
        // Patient patient = (Patient) UserListController.findUser(userID);

        Button browseProblems = (Button) findViewById(R.id.browse_problems_button);

    }


    //this method changes the current activity to the browse problems activity
    public void changeActivity (View v){
        Intent intent = new Intent(this, ActivityBrowseProblems.class);
        intent.putExtra("Patient", patient_ID);
        startActivity(intent);
    }

    //this method changes the current activity to the update contact info activity
    public void toUpdateContactInfo(View v){
        Intent intent = new Intent(this, ActivityUpdateContactInfo.class);
        intent.putExtra("Patient", patient_ID);
        startActivity(intent);

    }

    public void goProblemMap(View v){
        Intent intent = new Intent (this, ActivityProblemMap.class);
        intent.putExtra("Patient", patient_ID);
        startActivity(intent);
    }



}

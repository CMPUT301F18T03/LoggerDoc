package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

public class ActivityPatientHomePage extends AppCompatActivity {
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__home__page);

        // Get the userID being passed from ActivityLogin.java
        // Set the textview to display the username
        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("Patient");
        TextView usernameTextView = (TextView) findViewById(R.id.patient_username_text);
        usernameTextView.setText(patient.getUserID());

        UserList userList = UserListController.getUserList();


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
        intent.putExtra("Patient", patient);
        startActivity(intent);
    }

    //this method changes the current activity to the update contact info activity
    public void toUpdateContactInfo(View v){
        Intent intent = new Intent(this, ActivityUpdateContactInfo.class);
        intent.putExtra("Patient", patient);
        startActivity(intent);

    }





}

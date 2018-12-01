package com.example.loggerdoc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

public class ActivityPatientHomePage extends AppCompatActivity {
    private Patient patient;
    private Integer patient_ID;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    static Switch adsSwitch;

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
        usernameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toViewProfile(v);
            }
        });

        Button browseProblems = (Button) findViewById(R.id.browse_problems_button);

        adsSwitch = ActivityLogin.adsSwitch;
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("/6499/example/interstitial");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("/6499/example/interstitial");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
    }


    //this method changes the current activity to the browse problems activity
    public void changeActivity (View v){
        Intent intent = new Intent(this, ActivityBrowseProblems.class);
        intent.putExtra("Patient", patient_ID);
        startActivity(intent);
        if (mPublisherInterstitialAd.isLoaded() && adsSwitch.isChecked()) {
            mPublisherInterstitialAd.show();
        }
    }

    //this method changes the current activity to the view profile activity
    public void toViewProfile(View v){
        Intent intent = new Intent(this, ActivityUserProfile.class);
        intent.putExtra("Patient", patient_ID);
        startActivity(intent);
        if (mPublisherInterstitialAd.isLoaded() && adsSwitch.isChecked()) {
            mPublisherInterstitialAd.show();
        }
    }

    //Change to ActivityProblemMap.
    public void toProblemMap (View view){
        Intent intent = new Intent (this, ActivityProblemMap.class);
        intent.putExtra("Patient", patient_ID);
        startActivity(intent);
        if (mPublisherInterstitialAd.isLoaded() && adsSwitch.isChecked()) {
            mPublisherInterstitialAd.show();
        }
    }


}

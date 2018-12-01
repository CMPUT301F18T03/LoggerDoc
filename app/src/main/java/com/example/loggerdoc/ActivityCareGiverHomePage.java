package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

public class ActivityCareGiverHomePage extends AppCompatActivity {
    private CareGiver caregiver;
    private Integer caregiver_ID;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    static Switch adsSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_home_page);

        // Get the elasticID being passed from ActivityLogin.java
        Intent intent = getIntent();
        caregiver_ID = intent.getIntExtra("Caregiver",0);
        caregiver = (CareGiver) UserListController.getUserList().get(caregiver_ID);

        // Set the textview to display the username
        TextView usernameTextView = (TextView) findViewById(R.id.username_text);
        usernameTextView.setText(caregiver.getUserID());

        usernameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserProfile(v);
            }
        });
        UserList userList = UserListController.getUserList();

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

    //this method changes the current activity to the caregiver browse patients activity
    public void toCareGiverBrowsePatients(View view){
        Intent intent = new Intent(this, ActivityCareGiverBrowsePatients.class);
        intent.putExtra("Caregiver", caregiver_ID);
        startActivity(intent);
        if (mPublisherInterstitialAd.isLoaded() && adsSwitch.isChecked()) {
            mPublisherInterstitialAd.show();
        }
    }


    //this method changes the current activity to the update contact info activity
    public void toEditContactInfo(View view){
        Intent intent = new Intent(this, ActivityUpdateContactInfo.class);
        intent.putExtra("Caregiver", caregiver_ID);
        startActivity(intent);
        if (mPublisherInterstitialAd.isLoaded() && adsSwitch.isChecked()) {
            mPublisherInterstitialAd.show();
        }
    }

    //Change to ActivityUserProfile.
    public void toUserProfile (View view){
        Intent intent = new Intent (this, ActivityUserProfile.class);
        intent.putExtra("Caregiver", caregiver_ID);
        startActivity(intent);
        if (mPublisherInterstitialAd.isLoaded() && adsSwitch.isChecked()) {
            mPublisherInterstitialAd.show();
        }

    }
}

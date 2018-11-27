package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityUserProfile extends AppCompatActivity {

    User viewedUser;
    private EditText emailEditText;
    private EditText phoneNumberEditText;
    private ImageView contactInfoEmailWarning;
    private ImageView contactInfoPhoneWarning;
    private TextView userIdView;
    private String userType;
    private Integer user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //initialize all of the views
        emailEditText = (EditText) findViewById(R.id.edit_contact_info_email_view);
        phoneNumberEditText = (EditText) findViewById(R.id.edit_contact_info_number_view);
        contactInfoEmailWarning = (ImageView) findViewById(R.id.warningEditEmail);
        contactInfoPhoneWarning = (ImageView) findViewById(R.id.warningEditPhoneNumber);
        userIdView = (TextView) findViewById(R.id.ProblemTitle);

        //get the user being viewed from the intent
        Intent intent = getIntent();

        //if user a patient, use a patient object. Otherwise use a caregiver object
        user_ID = intent.getIntExtra("Patient",0);
        if(user_ID == 0){
            user_ID = intent.getIntExtra("Caregiver",0);
        }
        viewedUser = UserListController.getUserList().get(user_ID);
        userType = viewedUser.getRole();



        userIdView.setText(viewedUser.getUserID());
        emailEditText.setText(viewedUser.getEmailAddress());
        phoneNumberEditText.setText(viewedUser.getPhoneNumber());
    }








    //this method changes the current activity to the update contact info activity
    public void toUpdateContactInfo(View v){
        Intent intent = new Intent(this, ActivityUpdateContactInfo.class);
        intent.putExtra(userType, user_ID);
        startActivity(intent);

    }

}

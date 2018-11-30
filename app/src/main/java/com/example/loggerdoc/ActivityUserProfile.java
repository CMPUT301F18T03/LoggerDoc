package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.io.IOException;

public class ActivityUserProfile extends AppCompatActivity {

    User viewedUser;
    private TextView emailEditText;
    private TextView phoneNumberEditText;
    private TextView userIdView;
    private String userType;
    private Integer user_ID;
    private ImageView qrCodeImageView;
    private Button editContactInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //initialize all of the views
        emailEditText = (TextView) findViewById(R.id.edit_contact_info_email_view);
        phoneNumberEditText = (TextView) findViewById(R.id.edit_contact_info_number_view);
        userIdView = (TextView) findViewById(R.id.UserNameDisplay);
        qrCodeImageView = (ImageView) findViewById(R.id.QrCodeImageView);
        editContactInfoButton = (Button) findViewById(R.id.EditContactInfoButton);

        //get the user being viewed from the intent
        Intent intent = getIntent();

        //if user a patient, use a patient object. Otherwise use a caregiver object
        user_ID = intent.getIntExtra("Patient",0);
        if(user_ID == 0){
            user_ID = intent.getIntExtra("Caregiver",0);
        }
        if (UserListController.getCurrentUserID().equals(user_ID) == false){
            editContactInfoButton.setVisibility(View.INVISIBLE);
        }
        viewedUser = UserListController.getUserList().get(user_ID);
        userType = viewedUser.getRole();
        try {
            qrCodeImageView.setImageBitmap(QRCodeController.generateQRCodeImage(Integer.toString(user_ID)));
        } catch (WriterException | IOException e){
            //yikes
        }
    }

    @Override
    protected void onResume () {
        super.onResume();
        viewedUser = UserListController.getUserList().get(user_ID);
        userIdView.setText(viewedUser.getUserID());
        emailEditText.setText(viewedUser.getEmailAddress());
        phoneNumberEditText.setText(viewedUser.getPhoneNumber());


    }






    //this method changes the current activity to the user profile activity
    public void toUpdateContactInfo(View v){
        Intent intent = new Intent(this, ActivityUpdateContactInfo.class);
        intent.putExtra(userType, user_ID);
        startActivity(intent);

    }

    //this method ends the current activity
    public void endActivity(View v){
        finish();
    }

}

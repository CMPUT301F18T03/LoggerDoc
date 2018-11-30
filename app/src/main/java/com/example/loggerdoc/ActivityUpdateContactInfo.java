/* Created 2018-11-16 by Stephen Zuk
 *
 *  The Update contact info activity displays the users contact info and lets the user modify
 *  and save the info
 *
 */

package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityUpdateContactInfo extends AppCompatActivity {

    User loggedInUser;
    private EditText emailEditText;
    private EditText phoneNumberEditText;
    private ImageView contactInfoEmailWarning;
    private ImageView contactInfoPhoneWarning;
    private TextView userId;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact_info);

        //initialize all of the views
        emailEditText = (EditText) findViewById(R.id.edit_contact_info_email_view);
        phoneNumberEditText = (EditText) findViewById(R.id.edit_contact_info_number_view);
        contactInfoEmailWarning = (ImageView) findViewById(R.id.warningEditEmail);
        contactInfoPhoneWarning = (ImageView) findViewById(R.id.warningEditPhoneNumber);
        userId = (TextView) findViewById(R.id.UserNameDisplay);


        //get the logged in user from the intent
        Intent intent = getIntent();
        getLoggedInUser(intent);
        userId.setText(loggedInUser.getUserID());
        emailEditText.setText(loggedInUser.getEmailAddress());
        phoneNumberEditText.setText(loggedInUser.getPhoneNumber());

    }

    //this method gets the entered email and phone number from the EditTexts, calls checkChanges
    //to make sure valid changes were made and updates the users email and phone number if
    //valid values were entered
    public void saveChanges(View view){
        String newEmail = emailEditText.getText().toString();
        String newPhoneNumber = phoneNumberEditText.getText().toString();

        //if the new changes are valid, set them to the user and close the activity
        if(checkChanges(newEmail, newPhoneNumber)) {
            loggedInUser.setEmailAddress(newEmail);
            loggedInUser.setPhoneNumber(newPhoneNumber);
            UserListController.getUserList().add(loggedInUser, getApplicationContext());
            finish();
        }

    }

    //this method closes the update contact info method without updating any of the users
    //information
    public void cancelChanges(View view){
        finish();
    }


    //this method takes the entered email and phone number and checks if they are the proper
    //length, otherwise it displays an error message
    public boolean checkChanges(String newEmail, String newPhoneNumber){

        //Check if the problemTitle and if the problemDescription is empty
        boolean emptyEmail = newEmail.length()== 0;
        boolean emptyPhoneNumber = newPhoneNumber.length()==0;

        //If either of the problemTitle or the problemDescription is empty
        if (emptyEmail || emptyPhoneNumber){

            //Set the flag for the user to indicate the Title Field is Empty
            if (emptyEmail){
                contactInfoEmailWarning.setVisibility(View.VISIBLE);
            }

            //Set the flag for the user to indicate the Description Field is Empty
            if (emptyPhoneNumber){
                contactInfoPhoneWarning.setVisibility(View.VISIBLE);
            }
            showAlertDialog("Error: Empty Fields","Please fill in the fields indicated by the red stars");
            return false;
        }

        else {

            //check if the email address is too long
            if (newEmail.length() > 30){
                showAlertDialog("Error: Too Long Email Address","The email address can be a maximum of 30 characters. Please shorten it");
                contactInfoEmailWarning.setVisibility(View.VISIBLE);
                return false;
            }

            //check if the description is too long
            if (newPhoneNumber.length() > 13){
                showAlertDialog("Error: Too Long Phone Number","The entered phone number has too many numbers. Please enter a valid number");
                contactInfoPhoneWarning.setVisibility(View.VISIBLE);
                return false;
            }


            //all checks passed
            //Turn off the flags
            contactInfoEmailWarning.setVisibility(View.INVISIBLE);
            contactInfoPhoneWarning.setVisibility(View.INVISIBLE);
            return true;


        }
    }

    //Show an error Alert Dialog.
    private void showAlertDialog( String title, String message){
        Bundle messageArgs = new Bundle();
        messageArgs.putString(DialogProblem.TITLE_ID, title);
        messageArgs.putString(DialogProblem.MESSAGE_ID, message);

        DialogProblem dialog = new DialogProblem();
        dialog.setArguments(messageArgs);
        dialog.show(getSupportFragmentManager(), "error_dialog");
    }


    public void getLoggedInUser(Intent intent){
        Integer patient_ID = intent.getIntExtra("Patient",0);
        if(patient_ID == 0){
            patient_ID = intent.getIntExtra("Caregiver",0);
        }
        loggedInUser = UserListController.getUserList().get(patient_ID);
        userType = loggedInUser.getRole();
    }






}

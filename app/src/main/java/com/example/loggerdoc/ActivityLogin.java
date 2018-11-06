package com.example.loggerdoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    UserList userList = new UserList();
    EditText userID = (EditText) findViewById(R.id.Username_Field);

    // If user hits the login button
    public void login(View view) {
        String userLogin = userID.getText().toString();
    }

    // When the create account button is pressed from the login screen this method gets run
    // this method will display the create account alert dialog
    public void createAccount(View view) {
        createAccountInfo();
    }

    // need method to check if the username is taken when the user is creating an account
    public void verifyUsername(String id) {
    }


    // This method will get called when the user clicks on create account from the ActivityLogin page.
    // If the user presses the create account button when they do not have internet connection they will be prompted
    // with a toast/error message saying they need internet to create an account
    public void createAccountInfo() {

        LayoutInflater layoutInflater = LayoutInflater.from(ActivityLogin.this);
        View dialogView = layoutInflater.inflate(R.layout.account_creation_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLogin.this);
        builder.setView(dialogView);

        builder.setTitle("Account Creation");
        final EditText userID = (EditText) dialogView.findViewById(R.id.userID);
        final EditText userEmail = (EditText) dialogView.findViewById(R.id.userEmailAddress);
        final EditText userPhoneNumber = (EditText) dialogView.findViewById(R.id.userPhoneNumber);


        // Triggered when the user clicks on the Patient button
        // TODO: check if the username has already been taken, need elasticsearch
        builder.setPositiveButton("Patient", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String username = userID.getText().toString();
                String emailAddress = userEmail.getText().toString();
                String phoneNumber = userPhoneNumber.getText().toString();

                Patient patient = new Patient(username, emailAddress, phoneNumber, new CareGiverList());
                userList.addUser(patient);
            }
        });

        // Triggered when the user clicks on the CareGiver button
        // TODO: check whether thee user id is taken, need elasticsearch
        builder.setPositiveButton("Caregiver", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String username = userID.getText().toString();
                String emailAddress = userEmail.getText().toString();
                String phoneNumber = userPhoneNumber.getText().toString();

                CareGiver careGiver = new CareGiver(username, emailAddress, phoneNumber, new PatientList());
                userList.addUser(careGiver);
            }
        });
        builder.show();
    }
}
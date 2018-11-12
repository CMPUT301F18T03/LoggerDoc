package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ActivityPatientHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__home__page);

        // Get the userID being passed from ActivityLogin.java
        // Set the textview to display the username
        // wil use the username to get the patient object from userlist.
        Intent intent = getIntent();
        String userID = (String) intent.getSerializableExtra("Patient");
        TextView usernameTextView = (TextView) findViewById(R.id.Username_text);
        usernameTextView.setText(userID);

        UserList userList = UserListController.getUserList();

        // find the patient object using the given userId that was passed through
        Patient patient = (Patient) UserListController.findUser(userID);

        Log.d("Tag", "Username = " + patient.getUserID());
        Log.d("Tag", "Email = " + patient.getEmailAddress());
        Log.d("Tag", "Phone = " + patient.getPhoneNumber());


    }



}

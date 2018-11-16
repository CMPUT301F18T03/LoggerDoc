package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityCareGiverHomePage extends AppCompatActivity {
    CareGiver loggedInCareGiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_home_page);

        // Get the userID being passed from ActivityLogin.java
        // Set the textview to display the username
        // wil use the username to get the patient object from userlist.
        Intent intent = getIntent();
        loggedInCareGiver = (CareGiver) intent.getSerializableExtra("Caregiver");
        TextView usernameTextView = (TextView) findViewById(R.id.username_text);
        usernameTextView.setText(loggedInCareGiver.getUserID());

        UserList userList = UserListController.getUserList();
    }


    public void toCareGiverBrowsePatients(View view){
        Intent intent = new Intent(this, ActivityCareGiverBrowsePatients.class);
        intent.putExtra("Caregiver", loggedInCareGiver);
        startActivity(intent);
    }
}

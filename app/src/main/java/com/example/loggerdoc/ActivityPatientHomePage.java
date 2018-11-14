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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__home__page);

        // Get the userID being passed from ActivityLogin.java
        // Set the textview to display the username
        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("Patient");
        TextView usernameTextView = (TextView) findViewById(R.id.Username_text);
        usernameTextView.setText(patient.getUserID());

        UserList userList = UserListController.getUserList();

        // find the patient object using the given userId that was passed through
        // Patient patient = (Patient) UserListController.findUser(userID);

        Button browseProblems = (Button) findViewById(R.id.browse_problems_button);

    }

    public void changeActivity (View v){
        Intent intent = new Intent(this, ActivityBrowseProblems.class);
        intent.putExtra("Patient", patient);
        startActivity(intent);
    }



}

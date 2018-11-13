package com.example.loggerdoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityCareGiverAddPatient extends AppCompatActivity {

    private CareGiver loggedInCareGiver;
    private Patient patientToAdd;

    EditText userID = (EditText) findViewById(R.id.AddPatientEditText);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver_add_patient);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //TODO Check If Entry Matches user
    }

    //activated when a caregiver presses the add button
    public void addPatient(View view){
        String entered = userID.getText().toString();
        ArrayList<User> list = (ArrayList)UserListController.getUserList().getUsers();
        for(User user :list){
            if (user.getUserID().equals(entered)){
                patientToAdd =(Patient) user;
                loggedInCareGiver.getPatientList().addPatient(patientToAdd);
                userID.setText("");
            }
            else{
                Toast.makeText(this, "That username does not exist. Please try again.", Toast.LENGTH_SHORT).show();
                userID.setText("");
                /*TODO Alex made a good alert error message creater for improper entries, consider using
                that once merged to master to separate concerns and not rewrite code

                 */

            }


        }
    }

}

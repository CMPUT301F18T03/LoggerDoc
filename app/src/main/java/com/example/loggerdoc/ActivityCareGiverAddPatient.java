/* Created 2018-11-13 by Stephen Zuk
*
*  The Caregiver add_internal patient activity provides a screen for caregivers to add_internal a patient
*  to their list of patients, and can be accessed from the caregiver patient list activity
*
*/

package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityCareGiverAddPatient extends AppCompatActivity {

    private CareGiver loggedInCareGiver;
    private Patient patientToAdd;
    EditText userID;
    Integer caregiver_ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver_add_patient);
        userID = (EditText) findViewById(R.id.AddPatientEditText);

        Intent intent = getIntent();
        caregiver_ID = intent.getIntExtra("Caregiver", 0);
        loggedInCareGiver = (CareGiver) UserListController.getUserList().get(caregiver_ID);
        intent.removeExtra("Caregiver");
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    /*activated when a caregiver presses the add_internal button. This method confirms that the
    string entered into the edittext is a user and adds the user to the caregivers patient list
    if it is. If the entered value is not a user, an error message pops up and the edittext is cleared*/
    public void addPatient(View view){
        String entered = userID.getText().toString();
        ArrayList<User> list = UserListController.getUserList().getArray();
        for(User user :list){
            if (user.getUserID().equals(entered)){
                patientToAdd =(Patient) user;
                loggedInCareGiver.addPatient(patientToAdd);
                UserListController.getUserList().add(loggedInCareGiver,getApplicationContext());
                userID.setText("");
                finish();
            }
            else{
                Toast.makeText(this, "That username does not exist. Please try again.", Toast.LENGTH_SHORT).show();
                userID.setText("");

            }


        }
    }




}

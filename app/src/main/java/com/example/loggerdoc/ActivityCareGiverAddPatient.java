/* Created 2018-11-13 by Stephen Zuk
*
*  The Caregiver add patient activity provides a screen for caregivers to add a patient
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver_add_patient);
        userID = (EditText) findViewById(R.id.AddPatientEditText);

        Intent intent = getIntent();
        loggedInCareGiver = (CareGiver) intent.getSerializableExtra("Caregiver");
    }


    @Override
    protected void onStart() {
        super.onStart();
        //TODO Check If Entry Matches user
    }

    /*activated when a caregiver presses the add button. This method confirms that the
    string entered into the edittext is a user and adds the user to the caregivers patient list
    if it is. If the entered value is not a user, an error message pops up and the edittext is cleared*/
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

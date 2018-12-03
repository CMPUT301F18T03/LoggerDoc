package com.example.loggerdoc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/* Created 2018-11-13 by Stephen Zuk
 *  The Caregiver add patient activity provides a screen for caregivers to add a patient
 *  to their list of patients, and can be accessed from the caregiver patient list activity
 */

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

        //Get the patient from the intent
        Intent intent = getIntent();
        caregiver_ID = intent.getIntExtra("Caregiver", 0);
        loggedInCareGiver = (CareGiver) UserListController.getUserList().get(caregiver_ID);
        intent.removeExtra("Caregiver");
    }

    /*
     *This method is called upon returning from the QR scanning activity. It gets the
     *result of scanning and gets the patient object associated to the scanned qr code if it exists,
     *and adds the patient object to the caregivers list.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent prevIntent) {
        super.onActivityResult(requestCode, resultCode, prevIntent);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = prevIntent.getStringExtra("SCAN_RESULT");
                patientToAdd = (Patient) UserListController.getUserList().get(Integer.parseInt(contents));
                loggedInCareGiver.addPatient(patientToAdd);
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }
    /*
     * activated when a caregiver presses the add button. This method confirms that the
     * string entered into the edittext is a user and adds the user to the caregivers patient list
     * if it is. If the entered value is not a user, an error message pops up and the edittext is cleared
     */
    public void addPatient(View view){
        String entered = userID.getText().toString();
        ArrayList<User> list = UserListController.getUserList().getArray();
        for(User user :list){
            if (user.getUserID().equals(entered)){
                //add the patient to the caregivers patient list, update userlist with updated caregiver
                patientToAdd =(Patient) user;
                loggedInCareGiver.addPatient(patientToAdd);
                UserListController.getUserList().add(loggedInCareGiver,getApplicationContext());
                finish();
                return;
            }
            else {
                //user not found, display text and do nothing
                Toast.makeText(this, "That username does not exist. Please try again.", Toast.LENGTH_SHORT).show();
                userID.setText("");
            }
        }
    }

    /*
     * this method opens the QR scanning activity and is called when the user presses the
     * "ADD WITH QR CODE" button
     */
    public void scanQR(View view){
        try {

            Intent intent = new Intent(this, ActivityScanQR.class);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0);

        } catch (Exception e) {
            Toast.makeText(this, "Error opening QR Scanner.", Toast.LENGTH_SHORT).show();
            Log.e("QR Exception", "Exception: "+Log.getStackTraceString(e));

        }
    }

}

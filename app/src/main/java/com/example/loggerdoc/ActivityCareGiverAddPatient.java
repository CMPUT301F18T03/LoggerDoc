/* Created 2018-11-13 by Stephen Zuk
*
*  The Caregiver add_internal patient activity provides a screen for caregivers to add_internal a patient
*  to their list of patients, and can be accessed from the caregiver patient list activity
*
*/

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
        //user not found, display text and do nothing
        Toast.makeText(this, "That username does not exist. Please try again.", Toast.LENGTH_SHORT).show();
        userID.setText("");




        }
    }


    public void scanQR(View view){
        try {

            Intent intent = new Intent(this, ActivityScanQR.class);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0);

        } catch (Exception e) {
            Toast.makeText(this, "Error opening QR Scanner.", Toast.LENGTH_SHORT).show();
            Log.e("QR Exception", "Exception: "+Log.getStackTraceString(e));
            //Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            //Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            //startActivity(marketIntent);

        }
    }

}

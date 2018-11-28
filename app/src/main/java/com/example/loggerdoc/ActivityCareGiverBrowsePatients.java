/* Created 2018-11-13 by Stephen Zuk
 *
 *  The Caregiver Browse patients activity displays the list of patients that a caregiver is serving.
 *  It uses the PatientListAdapter to display a custom field for each patient in the list.
 *
 */

package com.example.loggerdoc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityCareGiverBrowsePatients extends AppCompatActivity {
    private ListView patientList;
    private ArrayAdapter<Patient> adapter;
    private CareGiver caregiver;
    private Integer caregiver_ID;
    private TextView userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver_browse_patients);
        patientList = (ListView) findViewById(R.id.PatientList);

        //get the caregiver from the intent
        Intent intent = getIntent();
        caregiver_ID = intent.getIntExtra("Caregiver", 0);
        caregiver = (CareGiver) UserListController.getUserList().get(caregiver_ID);
        intent.removeExtra("Caregiver");

        //set textview to the logged in caregivers user ID
        userId = (TextView) findViewById(R.id.patientListUsernameText);
        userId.setText(caregiver.getUserID());
    }


    @Override
    protected void onStart() {
        super.onStart();
        //The patient list adapter provides a custom view for each patient in the list, displaying
        //their user ID, email and phone number
        //initialize adapter and set it to the patient list
        adapter = new AdapterListPatient(this,
                R.layout.patient_listview_layout, UserListController.getSpecificUserList(caregiver.getPatientList()));
        patientList.setAdapter(adapter);

        //Set the onClickListener for the listView. This will call toProblemListActivity().
        patientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient selectedPatient = (Patient) parent.getItemAtPosition(position);
                toBrowseProblemsActivity(selectedPatient);



            }
        });


        // Set the Add Patient button. When this button is pressed it will call toAddPatient().
        FloatingActionButton addPatientButton = (FloatingActionButton) findViewById(R.id.addPatientButton);
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAddPatient(view);
            }
        });

    }


    //this method changes the current activity to the addPatient activity
    public void toAddPatient(View view) {
        Intent intent = new Intent(this, ActivityCareGiverAddPatient.class);
        intent.putExtra("Caregiver", caregiver.getElasticID());
        startActivity(intent);
    }


    //this method takes a patient and switches the current activity to the patients browse problems activity
    //@alex if you see this and I forget to ask, should it also pass the logged in caregiver so
    //he/she can add_internal comments?
    public void toBrowseProblemsActivity(Patient patient){
        Intent intent = new Intent(this, ActivityBrowseProblems.class);
        intent.putExtra("Patient", patient.getElasticID());
        startActivity(intent);
    }









}

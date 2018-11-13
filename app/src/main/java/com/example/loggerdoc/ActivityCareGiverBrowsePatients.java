/* Created 2018-11-13 by Stephen Zuk
 *
 *  The Caregiver Browse patients activity displays the list of patients that a caregiver is serving.
 *  It uses the PatientListAdapter to display a custom field for each patient in the list.
 *
 */

package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityCareGiverBrowsePatients extends AppCompatActivity {
    private ListView patientList;
    private ArrayAdapter<Patient> adapter;
    private PatientList testPatients = new PatientList();// TODO get this list from the caregiver
    private CareGiver loggedInCaregiver; // TODO get this caregiver from the previous activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver_browse_patients);

        patientList = (ListView) findViewById(R.id.PatientList);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //The patient list adapter provides a custom view for each patient in the list, displaying
        //their user ID, email and phone number
        adapter = new PatientListAdapter(this,
                R.layout.patient_listview_layout, loggedInCaregiver.getPatientList().getPatients());


        patientList.setAdapter(adapter);
        patientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient selectedPatient = (Patient) parent.getItemAtPosition(position);


            }
        });

    }

    public void onHomeButton(View view) {
        Intent intent = new Intent(this, ActivityCareGiverHomePage.class);
        startActivity(intent);
    }

    public void toAddPatient(View view) {
        Intent intent = new Intent(this, ActivityCareGiverAddPatient.class);
        startActivity(intent);
    }







}

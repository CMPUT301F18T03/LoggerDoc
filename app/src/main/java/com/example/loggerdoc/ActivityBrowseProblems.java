package com.example.loggerdoc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityBrowseProblems extends AppCompatActivity {

    private AdapterListProblems adapter;
    private Patient patient;
    private Integer patient_ID;
    private ListView problemsList;

    //To be called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_problems);

        //get the patient from the intent
        Intent intent = getIntent();
        patient_ID = intent.getIntExtra("Patient",0);
        patient = (Patient) UserListController.getUserList().get(patient_ID);

        /*
         * Set the Add Problem button. When this button is pressed it will call
         * changeToAddProblemActivity().
         */
        FloatingActionButton addProblemButton = (FloatingActionButton) findViewById(R.id.addProblemButton);
        addProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToAddProblemActivity(v);
            }
        });

        /*
         * Set the Search Problem button. When this button is pressed it will call
         * changeToSearchActivity().
         */
        FloatingActionButton searchButton = (FloatingActionButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToSearchActivity(v);
            }
        });

        /*
         * Display the patient's userID. If this userID is clicked, it will call
         * changeToUserProfile().
         */
        TextView username = (TextView) findViewById(R.id.usernameText);
        username.setText(patient.getUserID());
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToUserProfile(v);
            }
        });
    }

    //To be called when the activity is resumed
    @Override
    protected void onResume(){
        super.onResume();

        //TODO: fix if we get listeners working
        adapter = new AdapterListProblems(this, ProblemRecordListController.getProblemList().getArray());
        problemsList = (ListView) findViewById(R.id.ProblemList);
        problemsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //Set the onClickListener for the listView. This will call changeToViewProblemActivity().
        problemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeToViewProblemActivity(view, patient, position);
            }
        });
    }

    //Change to ActivityViewProblem.
    public void changeToViewProblemActivity(View view, Patient patient, int position){
        Intent intent = new Intent(this, ActivityViewProblem.class);
        intent.putExtra("Patient", patient_ID);
        intent.putExtra("Position",
                ProblemRecordListController.getProblemList().getArray().get(position).getElasticID());
        startActivity(intent);
    }

    //Change to ActivityAddProblem.
    public void changeToAddProblemActivity (View view){
        Intent intent = new Intent(this, ActivityAddProblem.class);
        startActivity(intent);
    }

    //Change to ActivitySearch.
    public void changeToSearchActivity(View view){
        Intent intent = new Intent (this, ActivitySearch.class);
        startActivity(intent);
    }

    //Change to ActivityUserProfile.
    public void changeToUserProfile (View view){
        Intent intent = new Intent (this, ActivityUserProfile.class);
        startActivity(intent);
    }

}
